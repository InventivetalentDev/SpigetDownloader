package org.inventivetalent.spiget.downloader;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.spiget.client.SpigetDownload;
import org.spiget.client.SpigetResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		OptionParser parser = new OptionParser();
		OptionSpec<String> urlSpec = parser.accepts("url").withRequiredArg().defaultsTo("");
		OptionSpec<String> fileSpec = parser.accepts("file").withRequiredArg().defaultsTo("");
		OptionSpec<Boolean> silentSpec = parser.accepts("silent").withOptionalArg().ofType(boolean.class).defaultsTo(false);

		OptionSet options = parser.parse(args);

		String url = urlSpec.value(options);
		String file = fileSpec.value(options);
		boolean silent = silentSpec.value(options);

		SpigetDownloader spigetDownloader = new SpigetDownloader();
		if (!silent) { System.out.println("Testing connection to spigotmc.org..."); }
		SpigetResponse testResponse = spigetDownloader.get("https://spigotmc.org");
		if (testResponse.getCode() != 200) {
			if (!silent) {
				System.err.println("Got response code " + testResponse.getCode() + " on index page");
				System.err.println(testResponse.getDocument().toString());
			}
			spigetDownloader.saveCookies();
			System.exit(-1);
		} else {
			if (!silent) {
				System.out.println("Connection successful.");
				System.out.println();
			}
		}

		if (url != null && !url.isEmpty()) {
			if (file != null && !file.isEmpty()) {
				if (!silent) { System.out.println("Downloading '" + url + "' to '" + file + "'..."); }
				SpigetDownload download = spigetDownloader.download(url);
				if (!silent) { System.out.println(download.getUrl() + ": " + download.getCode()); }
				if (download.getCode() == 200) {
					ReadableByteChannel channel = Channels.newChannel(download.getInputStream());
					FileOutputStream output = new FileOutputStream(file);
					output.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
					if (!silent) { System.out.println("Done."); }
					spigetDownloader.saveCookies();
					System.exit(0);
				} else {
					if (!silent) { System.err.println("Got response code " + download.getCode()); }
					spigetDownloader.saveCookies();
					System.exit(-1);
				}
			} else {
				if (!silent) { System.err.println("Please specify a file"); }
				System.exit(-1);
			}
		} else {
			if (!silent) { System.err.println("Please specify an URL"); }
			System.exit(-1);
		}
	}

}

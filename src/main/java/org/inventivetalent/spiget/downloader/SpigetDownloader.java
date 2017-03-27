package org.inventivetalent.spiget.downloader;

import org.spiget.client.SpigetClient;
import org.spiget.client.SpigetDownload;
import org.spiget.client.SpigetResponse;

import java.io.*;

public class SpigetDownloader {

	public SpigetDownloader() throws IOException {
		this(new DownloadConfig());
	}

	public SpigetDownloader(DownloadConfig config) throws IOException {
		SpigetClient.config = config.toJson();
		SpigetClient.userAgent = config.userAgent;

		File cookieFile = new File(config.cookieFile);
		if (!cookieFile.exists()) {
			if (cookieFile.getParentFile() != null) {
				cookieFile.getParentFile().mkdirs();
			}
			cookieFile.createNewFile();
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(cookieFile))) {
				writer.write("{}\n");
			}
		}
		SpigetClient.loadCookiesFromFile();
	}

	public void saveCookies() throws IOException {
		SpigetClient.saveCookiesToFile();
	}

	public SpigetResponse get(String url) throws IOException, InterruptedException {
		return SpigetClient.get(url);
	}

	public SpigetDownload download(String url) throws IOException, InterruptedException {
		return SpigetClient.download(url);
	}

	public InputStream openInputStream(String url) throws IOException, InterruptedException {
		return SpigetClient.download(url).getInputStream();
	}

}

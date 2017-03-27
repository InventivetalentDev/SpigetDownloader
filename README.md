# Spiget Downloader
This is **standalone** program / **API** to downlod resource files from SpigotMC.org. 


## Standalone usage (command line)
This downloads version 4.2.0 of ProtocolLib and saves it as `ProtocolLib.jar`
```bash
java -jar spiget-downloader.jar --url https://www.spigotmc.org/resources/protocollib.1997/download?version=131115 --file ProtocolLib.jar
```  


## API usage
```java
SpigetDownload download = new SpigetDownloader().download("https://www.spigotmc.org/resources/protocollib.1997/download?version=13111");
ReadableByteChannel channel = Channels.newChannel(download.getInputStream());
try (FileOutputStream out = new FileOutputStream(file)) {
    out.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
}
```

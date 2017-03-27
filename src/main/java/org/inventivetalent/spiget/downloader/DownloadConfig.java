package org.inventivetalent.spiget.downloader;

import com.google.gson.JsonObject;

public class DownloadConfig {

	public boolean debugConnections;
	public int     requestDelay;
	public String  cookieFile;
	public String  userAgent;

	public DownloadConfig(boolean debugConnections, int requestDelay, String cookieFile, String userAgent) {
		this.debugConnections = debugConnections;
		this.requestDelay = requestDelay;
		this.cookieFile = cookieFile;
		this.userAgent = userAgent;
	}

	public DownloadConfig() {
		this.debugConnections = true;
		this.requestDelay = 3000;
		this.cookieFile = "cookies.json";
		this.userAgent = "Spiget-Download-Client";
	}

	JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("debug.connections", debugConnections);
		jsonObject.addProperty("request.delay", requestDelay);
		jsonObject.addProperty("request.cookieFile", cookieFile);
		return jsonObject;
	}

}

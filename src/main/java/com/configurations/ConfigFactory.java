package com.configurations;

import com.utils.Utils;

public class ConfigFactory {

	private ConfigFactory() {

	}

	public synchronized static IConfiguration getConfig(String category) {

		String url = null;
		IConfiguration config = null;
		String urlKey = Utils.getProperty("env");
		String coreUrl = null;

		switch (urlKey) {
		case "dev":
			coreUrl = Utils.getProperty("appURL");
			break;
		case "test":
			coreUrl = "testEnvUrl";
			break;
		case "preProd":
			coreUrl = "PreProdUrl";
			break;

		}

		switch (category) {
		case "core":
			url = coreUrl;
			config = new Core(url);
		default:
			break;
		}
		return config;

	}

}

package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;

public class GetOptions {
	
	private Properties prop;
	
	public GetOptions(Properties prop) {
		this.prop=prop;
	}

	public ChromeOptions getChromeOptions() {
		ChromeOptions co = new ChromeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			co.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			co.addArguments("--incognito");
		}
		return co;
	}
}

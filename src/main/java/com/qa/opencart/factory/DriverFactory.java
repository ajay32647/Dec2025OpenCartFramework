package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;


import com.qa.opencart.exceptions.BrowserException;

public class DriverFactory {
	WebDriver driver;
	Properties prop;
	
	GetOptions getOptions;
	
	public static String highlight;
	
	private static final Logger log = LogManager.getLogger(DriverFactory.class);
	
	public static ThreadLocal<WebDriver> thLocal = new ThreadLocal<WebDriver>();
	
	public WebDriver initDriver(Properties prop) {
		
		log.info(prop);
		String browserName = prop.getProperty("browser");
		log.info("Browser name is "+browserName);
		
		//System.out.println("Browser name is "+browserName);
		
		highlight=prop.getProperty("highlight");
		
		getOptions= new GetOptions(prop);
		
		switch(browserName.trim().toLowerCase()) {
		case "chrome":
			
			log.info("Launching the Chrome Browser");
			thLocal.set(new ChromeDriver(getOptions.getChromeOptions()));
			break;
		
		case "safari":
			log.info("Launching the Safari Browser");
			thLocal.set(new SafariDriver());
			break;
			
		case "firefox":
			log.info("Launching the Firefox Browser");
			thLocal.set(new FirefoxDriver());
			break;
			
		case "edge":
			log.info("Launching the Edge Browser");
			thLocal.set(new EdgeDriver());
			break;
			
		default:
			log.error("Please pass the valid browser name.."+ browserName);
			throw new BrowserException("====INVALID BROWSER====");

		}
		
		getDriver().get(prop.getProperty("url"));
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		return getDriver();
	}
	
	public static WebDriver getDriver() {
		return thLocal.get();
	}
	
	public Properties initProp() {
		
		//maven clean install -Denv="qa"
		
		FileInputStream ip=null;
		String envName = System.getProperty("env");	
		prop = new Properties();
		
		try {
			if(envName==null) {
				log.warn("envName is null....hence running tests in QA environment...");
				 ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			}
			else {
				switch(envName) {
				case "dev":
					log.info("Running tests in dev environment...");
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				 
				case "qa":
					log.info("Running tests in qa environment...");
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				
				case "uat":
					log.info("Running tests in uat environment...");
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
		
				case "stage":
					log.info("Running tests in stage environment...");
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				
				default:
				break;
			
			}
		}
			prop.load(ip);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
		
	}
	
	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		return srcFile;
	}
	


}


package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import static com.qa.opencart.constants.AppConstants.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qa.opencart.utils.ElementUtil;


public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	Map<String, String> productDataMap;
	
	private final By headerLoc = By.tagName("h1");
	private final By imageCountLoc = By.cssSelector("ul.thumbnails img");
	private final By productMetaDataloc = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li"); 
	private final By productPriceDataloc = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li"); 
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
		
	}
	
	public String getProductHeader() {
		String headerText = eleUtil.waitForElementVisible(headerLoc, DEFAULT_TIME_OUT).getText();
		System.out.println("Header text is "+headerText);
		return headerText;

	}
	
	public int getProductImageCount() {
	int prodImageCount =eleUtil.waitForVisibilityOfElemenetsLocated(imageCountLoc,DEFAULT_TIME_OUT).size();
	System.out.println("Total number of images present on the page is "+prodImageCount);
	return prodImageCount;
	}
	
	public Map<String, String> getProductCompleteData() {
		productDataMap = new HashMap<String, String>();
		getProductMetaData();
		getProductPriceData();
		System.out.println("Full Product Details :"+productDataMap);
		
		return productDataMap;
	}
	
	
	private Map<String, String> getProductMetaData() {
		List<WebElement> ele = 
				eleUtil.waitForPresenceOfElemenetsLocated(productMetaDataloc, DEFAULT_TIME_OUT);
		for(WebElement e: ele) {
			String data = e.getText();
			String values[] = data.split(":");
			productDataMap.put(values[0].trim(), values[1].trim());
			
		}
		return productDataMap;
	}
	
	
	//$2,000.00
	//Ex Tax: $2,000.00
	private Map<String, String> getProductPriceData() {
		List<WebElement> ele = 
				eleUtil.waitForPresenceOfElemenetsLocated(productPriceDataloc, DEFAULT_TIME_OUT);
		
		String price = ele.get(0).getText().trim();
		productDataMap.put("product price", price);
		String exTax[] = ele.get(1).getText().split(":");
		productDataMap.put(exTax[0].trim(), exTax[1].trim());
		return productDataMap;
		
	}
	
	
}


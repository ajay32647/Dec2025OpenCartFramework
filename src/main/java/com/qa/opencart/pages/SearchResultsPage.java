package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private static By searchResultLoc = By.cssSelector("div.product-thumb");
	
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil= new ElementUtil(driver);
	}
	
	public int searchResultCount() {
		int searchResultCount = 
				eleUtil.waitForVisibilityOfElemenetsLocated(searchResultLoc, AppConstants.DEFAULT_TIME_OUT).size();
		System.out.println("Total number of products available is/are "+ searchResultCount);
		return searchResultCount;
	}
	
	public ProductInfoPage selectProductFromSearch(String productname) {
		By productLoc = By.xpath("//div[@class= 'caption']//a[text()='"+productname+"']");
		eleUtil.clickWhenReady(productLoc, AppConstants.DEFAULT_TIME_OUT);
		return new ProductInfoPage(driver);
	}
	
	
}

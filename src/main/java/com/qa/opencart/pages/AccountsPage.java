package com.qa.opencart.pages;

import static com.qa.opencart.constants.AppConstants.DEFAULT_TIME_OUT;
import static com.qa.opencart.constants.AppConstants.HOME_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.HOME_PAGE_TITLE;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;


public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private final By header = By.cssSelector("div#content > h2");
	private final By searchField = By.name("search");
	private final By searchIcon = By.cssSelector("div#search span");
	
	private static final Logger log = LogManager.getLogger(AccountsPage.class);

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getAccPageTitle() {
		String accPageTitle = eleUtil.waitForTitleToBe(HOME_PAGE_TITLE, DEFAULT_TIME_OUT);
		log.info("Accounts page title is "+accPageTitle);
		return accPageTitle;

	}
	
	public String getAccPageURL() {
		return eleUtil.waitForURLContains(HOME_PAGE_FRACTION_URL, DEFAULT_TIME_OUT);
}
	
	public List<String> getAccPageHeaders() {
		List<WebElement> headerList = eleUtil.waitForVisibilityOfElemenetsLocated(header, DEFAULT_TIME_OUT);
		List<String> headersValue = new ArrayList<String>();
		for(WebElement e: headerList) {
			String text = e.getText();
			headersValue.add(text);
		}
		log.info("Headers in the Account Page are "+headersValue);
		return headersValue;
	}
	
	public SearchResultsPage accPageProdSearch(String searchKey) {
		eleUtil.doSendKeys(searchField, searchKey);
		eleUtil.clickWhenReady(searchIcon, DEFAULT_TIME_OUT);
		log.info("Searching for Element :"+searchKey);
		
		return new SearchResultsPage(driver);
	}
	
	
	
	
	
	
	
	
	
	
	
}

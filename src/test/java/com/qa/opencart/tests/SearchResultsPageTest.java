package com.qa.opencart.tests;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.pages.SearchResultsPage;

public class SearchResultsPageTest extends BaseTest{

	@BeforeClass
	public void searchResultsPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void searchResultCountTest() {
		searchResults = accPage.accPageProdSearch("macbook");
		int actResultCount = searchResults.searchResultCount();
		Assert.assertEquals(actResultCount, 3);
		
	}
	
	
}

package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void ProductInfoPageTestSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] productHeaderData() {
		return new Object[][] {
		{"Macbook", "MacBook Pro"},
		{"Macbook","MacBook Air"},
		{"samsung","Samsung SyncMaster 941BW"},
		{"samsung","Samsung Galaxy Tab 10.1"},
		{"iMac","iMac"}
	};
	}
	
	@Test(dataProvider = "productHeaderData")
	public void getProductHeaderTest(String searchKey, String productName) {
		searchResults = accPage.accPageProdSearch(searchKey);
		productInfo = searchResults.selectProductFromSearch(productName);
		String actHeader = productInfo.getProductHeader();
		Assert.assertEquals(actHeader, productName);
	}
	
	@DataProvider
	public Object[][] productImageCountData() {
		return new Object[][] {
			{"Macbook", "MacBook Pro", 4},
			{"Macbook","MacBook Air", 4},
			{"samsung","Samsung SyncMaster 941BW", 1},
			{"samsung","Samsung Galaxy Tab 10.1", 7},
			{"iMac","iMac", 3}
		};
	}
	
	@Test(dataProvider = "productImageCountData")
	public void getProductImageCountTest(String searchKey, String productName, int imgCount) {
		searchResults = accPage.accPageProdSearch(searchKey);
		productInfo = searchResults.selectProductFromSearch(productName);
		int productImageCount = productInfo.getProductImageCount();
		Assert.assertEquals(productImageCount, imgCount);
	}
	
	
	
	@Test
	public void getProductCompleteDataTest() {
		searchResults = accPage.accPageProdSearch("Macbook");
		productInfo = searchResults.selectProductFromSearch("MacBook Pro");
		Map<String, String> productData = productInfo.getProductCompleteData();
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(productData.get("Brand"), "Apple");
		softAssert.assertEquals(productData.get("Reward Points"), "800");
		softAssert.assertEquals(productData.get("product price"), "$2,000.00");
		softAssert.assertEquals(productData.get("Ex Tax"), "$2,000.00");
		
		softAssert.assertAll();
	}
}

package com.qa.opencart.tests;

import static com.qa.opencart.constants.AppConstants.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class AccountsPageTest extends BaseTest{
	
	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accPageTitleTest() {
		Assert.assertEquals(accPage.getAccPageTitle(), HOME_PAGE_TITLE);
	}
	
	@Test
	public void AccPageURLTest() {
		Assert.assertTrue(accPage.getAccPageURL().contains(HOME_PAGE_FRACTION_URL));
	}
	
	@Test
	public void AccPageHeadersTest() {
		Assert.assertEquals(accPage.getAccPageHeaders(), HOME_PAGE_EXP_HEADERS);
		
	}
	
	
}

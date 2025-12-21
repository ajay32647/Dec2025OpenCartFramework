package com.qa.opencart.tests;

import static com.qa.opencart.constants.AppConstants.REGISTRATION_CONF_PAGE_HEADER;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class RegistrationPageTest extends BaseTest{

	@BeforeClass
	public void registrationPageSetUp() {
		regPage = loginPage.doRegistration(); 
	}
	
	@DataProvider
	public Object[][] registrationData() {
		return new Object[][] {
			{"Ajay","Sri","2323232323","saswe3455","no"},
			{"Satish","kumar","343234345","aswuw72533","yes"},
			{"Suresh","jumahr","343425433","232@12344","yes"},
		};
	}
	
	
	@Test(dataProvider = "registrationData")
	public void registrationPageTest(String firstName, String lastName, String phoneNum, String password, String subsYesNo) {
		String confirmMszHeader = regPage.doRegister(firstName, lastName, phoneNum, password, subsYesNo);
		Assert.assertEquals(confirmMszHeader, REGISTRATION_CONF_PAGE_HEADER);
	}
}

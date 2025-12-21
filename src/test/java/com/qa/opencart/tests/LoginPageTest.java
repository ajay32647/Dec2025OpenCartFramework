package com.qa.opencart.tests;

import static com.qa.opencart.constants.AppConstants.HOME_PAGE_TITLE;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_TITLE;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.pages.AccountsPage;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: design for Open Cart Application")
@Story("US 101: implement login page for open cart Application")
public class LoginPageTest extends BaseTest{

	@Test
	@Severity(SeverityLevel.MINOR)
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, LOGIN_PAGE_TITLE);
		
	}
	@Test
	public void loginPageURLTest() {
		String actTitle = loginPage.getLoginPageURL();
		Assert.assertTrue(actTitle.contains(LOGIN_PAGE_FRACTION_URL));	
	}
	
	@Test
	public void forgotPwdLinkTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExists());
	}
	
	@Test(priority = Short.MAX_VALUE)
	public void doLoginTest() {
		AccountsPage accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccPageTitle(), HOME_PAGE_TITLE);
	}
}

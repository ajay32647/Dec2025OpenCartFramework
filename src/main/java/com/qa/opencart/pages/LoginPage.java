package com.qa.opencart.pages;

import static com.qa.opencart.constants.AppConstants.DEFAULT_TIME_OUT;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_TITLE;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.StringUtils;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	// 1. private login page by locators
	private final By email = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginbtn = By.xpath("//input[@value='Login']");
	private final By forgotPwdLink = By.linkText("Forgotten Password");
	
	private final By registerLoc = By.linkText("Register");
	
	private static final Logger log = LogManager.getLogger(LoginPage.class);

	//2. public login page constructors
	public LoginPage(WebDriver driver) {
		this.driver= driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//3. public login page actions/methods
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleToBe(LOGIN_PAGE_TITLE, DEFAULT_TIME_OUT);
		log.info("Login Page Title is "+title);
		return title;
	}
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(LOGIN_PAGE_FRACTION_URL, DEFAULT_TIME_OUT);
		log.info("Login Page URL is "+url);
		return url;
	}
	
	public boolean isForgotPwdLinkExists() {
		return(eleUtil.isElementDisplayed(forgotPwdLink)) ;
		
	}
	@Step("Login with valid username: {0} and password: {1}")
	public AccountsPage doLogin(String userName, String pwd) {
		log.info("User Credentials: "+userName+": "+pwd);
		eleUtil.waitForElementVisible(email, DEFAULT_TIME_OUT).sendKeys(userName);;
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginbtn);
		
		return new AccountsPage(driver);
	}
	
	public RegistrationPage doRegistration() {
		eleUtil.clickWhenReady(registerLoc, DEFAULT_TIME_OUT);
		return new RegistrationPage(driver);

	}
	
	
	
	
}


package com.qa.opencart.pages;

import static com.qa.opencart.constants.AppConstants.DEFAULT_TIME_OUT;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.StringUtils;

public class RegistrationPage {
	
	WebDriver driver;
	ElementUtil eleUtil;
	
	private final By firstNameLoc = By.id("input-firstname");
	private final By lastNameLoc = By.id("input-lastname");
	private final By emailIDLoc = By.id("input-email");
	private final By phoneNumLoc = By.id("input-telephone");
	private final By passwordLoc = By.id("input-password");
	private final By conPwdLoc = By.id("input-confirm");
	private final By subsYesLoc = By.xpath("(//input[@type='radio' and @value='1'])[2]");
	private final By subsNoLoc = By.xpath("//input[@type='radio' and @value='0']");
	private final By checkboxBtnLoc = By.xpath("//input[@type='checkbox']");
	private final By contButtonLoc = By.xpath("//input[@value='Continue']");
	
	By confMszLoc = By.tagName("h1");
	By logoutLoc = By.linkText("Logout");
	By registerLoc = By.linkText("Register");

	public RegistrationPage(WebDriver driver){
		this.driver=driver;
		eleUtil = new ElementUtil(driver);
		}

	
	public String doRegister
	(String firstName, String lastName, String phoneNum, String password, String subsYesNo ) {
		eleUtil.waitForElementVisible(firstNameLoc, DEFAULT_TIME_OUT).sendKeys(firstName);
		eleUtil.doSendKeys(lastNameLoc, lastName);
		eleUtil.doSendKeys(emailIDLoc, StringUtils.getRandomEmailId());
		eleUtil.doSendKeys(phoneNumLoc, phoneNum);
		eleUtil.doSendKeys(passwordLoc, password);
		eleUtil.doSendKeys(conPwdLoc,password );
		
		if(subsYesNo.toLowerCase().equals("yes")) {
			eleUtil.doClick(subsYesLoc);
		}
		else {
			eleUtil.doClick(subsNoLoc);
		}
		eleUtil.doClick(checkboxBtnLoc);
		eleUtil.doClick(contButtonLoc);
		String confMsz = eleUtil.waitForElementVisible(confMszLoc, DEFAULT_TIME_OUT).getText();
		eleUtil.clickWhenReady(logoutLoc, DEFAULT_TIME_OUT);
		eleUtil.doClick(registerLoc);
		return confMsz;

}
}

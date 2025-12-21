package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {
	
	WebDriver driver;
	JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	
	private void nullCheck(CharSequence... value) {
		
		if(value==null) {
			System.out.println(value+" is not a valid value");
			throw new RuntimeException("====Invalid Value====");
		}
	}
	public String getTitle() {
		String title = driver.getTitle();
		System.out.println("Title is "+title);
		return title;
	}
	
	public String getCurrentURL(){
		String cuurentURL = driver.getCurrentUrl();
		System.out.println("Title is "+cuurentURL);
		return cuurentURL;
	}
	
	@Step("Finding the Element with locator: {0}")
	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		highlightElement(element);
		return element;
	}
	
	private void highlightElement(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
	}
	
	public void doClick(By localtor) {
		getElement(localtor).click();
	}
	
	@Step("Entering value into: {1} into element: {0}")
	public void doSendKeys(By localtor, String value) {
		nullCheck(value);
		getElement(localtor).clear();
		getElement(localtor).sendKeys(value);;
	}
	
	public void doSendKeys(By localtor, CharSequence... value) {
		nullCheck(value);
		getElement(localtor).clear();
		getElement(localtor).sendKeys(value);;
	}
	
	public String doGetText(By localtor) {
		return getElement(localtor).getText();
	}
	
	public boolean isElementDisplayed(By locator) {
		try {
		return getElement(locator).isDisplayed();
		}
		catch(Exception e) {
			System.out.println("No such element is present "+locator);
			return false;
		}
	}
	
	public String getDomAttributeValue(String attName, By locator) {
		return getElement(locator).getDomAttribute(attName);
	}

	public String getDomPropertyValue(String attName, By locator) {
		return getElement(locator).getDomProperty(attName);
	}
	
	
	//************=========find Elements methods=======**********//
	
	public List<String> getElementsListText(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleText = new ArrayList<String>();//pc=0
		for(WebElement e : eleList) {
			String text = e.getText();
			if(text.length()!=0) {
			System.out.println(text);
			eleText.add(text);
			}
		}
		return eleText;
	}
	
	
	
	public int getElementsListSize(By locator) {
		List<WebElement> eleList = getElements(locator);
		System.out.println("No of elements present in the list is "+eleList.size());
		return eleList.size();
	}
	
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	//*** Count of element displayed on the basis of driver.findElements()***//
	
	
	public boolean elementIsDisplayed(By locator, int expCount) {
		List<WebElement> eleList = getElements(locator);
		System.out.println("Element is displaying "+eleList.size()+" times...");
		if(eleList.size()==expCount){
			System.out.println("Actual count is corect :" + eleList.size());
			return true;
		}
		else {
			System.out.println("Actual count is not corect :" +eleList.size());
			return false;
		}
	}
	
	
	//*********............Select class methods...........**************************
	
	public boolean doSelectById(By locator, int index) {
		Select select = new Select(getElement(locator));
		
		try {
			select.selectByIndex(index);
			System.out.println("Selecting "+index+" by index");
			return true;
			}
			catch(NoSuchElementException e) {
				System.out.println(index +" is not available in the dropdown..");
				return false;
			}
	}
	
	public boolean doSelectByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		try {
		select.selectByVisibleText(text);
		System.out.println("Selecting "+text+" by visible text");
		return true;
		}
		catch(NoSuchElementException e) {
			System.out.println(text +" is not available in the dropdown..");
			return false;
		}
	}
	
	public  boolean doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		try {
			select.selectByValue(value);
			System.out.println("Selecting "+value+" by value");
			return true;
			}
			catch(NoSuchElementException e) {
				System.out.println(value +" is not available in the dropdown..");
				return false;
			}
	}
	
	public void getOptionsusingSelectClass(By locator, String choice) {
		Select select = new Select(getElement(locator));
		
		List<WebElement> ele = select.getOptions();
		
		System.out.println(ele.size());
		boolean flag = false;
		for(WebElement e: ele) {
			String data = e.getText();
			System.out.println(data);
			if(data.equalsIgnoreCase(choice)) {
				e.click();
				flag=true;
				break;
			}
		
		}
		if(flag) {
			System.out.println(choice +" is selected");
		}
		else {
			System.out.println(choice +" is not available..");

		}
	}
	
	//***** choices without Select Class**********//
	public  void selectChoiceWithoutSelectClass(By locator, By choicesLoc, String... choicesOptions ) {
		getElement(locator).click();
		
		List<WebElement> choicesList = getElements(choicesLoc);
	
		System.out.println(choicesList.size());
		
		if(choicesOptions[0].equalsIgnoreCase("all")) {
			for(WebElement e: choicesList) {
				e.click();
			}
		}
		else {
		for(WebElement e: choicesList) {
			String data = e.getText();
			
			for(String s: choicesOptions) {
				if(s.equals(data)) {
					e.click();
					break;
				}
			}
			
		}
	}
	}
	public void doSearch(By searchField, String searchKey, By suggestions, String value) throws InterruptedException {
		doSendKeys(searchField, searchKey);
		Thread.sleep(3000);
		List<WebElement> suggList = getElements(suggestions);
		System.out.println(suggList.size());
		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(value)) {
				e.click();
				break;
			}
		}
	}

	// *****************Actions utils********************//

	public void handleParentSubMenu(By parentLocator, By childLocator) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentLocator)).perform();
		Thread.sleep(2000);
		doClick(childLocator);
	}

	public void doDragAndDrop(By sourcelocator, By targetLocator) {
		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(sourcelocator), getElement(targetLocator)).perform();
	}

	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}

	/**
	 * This method is used to enter the value in the text field with a pause.
	 * 
	 * @param locator
	 * @param value
	 * @param pauseTime
	 */
	public void doActionsSendKeysWithPause(By locator, String value, long pauseTime) {
		Actions act = new Actions(driver);
		char ch[] = value.toCharArray();
		for (char c : ch) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTime).perform();
		}
	}

	/**
	 * This method is used to enter the value in the text field with a pause of 500
	 * ms (by default).
	 * 
	 * @param locator
	 * @param value
	 */
	public void doActionsSendKeysWithPause(By locator, String value) {
		Actions act = new Actions(driver);
		char ch[] = value.toCharArray();
		for (char c : ch) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(500).perform();
		}
	}

	public void level4MenuSubMenuHandlingUsingClick(By level1, String level2, String level3, String level4)
			throws InterruptedException {

		doClick(level1);
		Thread.sleep(1000);

		Actions act = new Actions(driver);
		act.moveToElement(getElement(By.linkText(level2))).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(By.linkText(level3))).perform();
		Thread.sleep(1000);
		doClick(By.linkText(level4));
	}

	public void level4MenuSubMenuHandlingUsingClick(By level1, By level2, By level3, By level4)
			throws InterruptedException {

		doClick(level1);
		Thread.sleep(1000);

		Actions act = new Actions(driver);
		act.moveToElement(getElement(level2)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(level3)).perform();
		Thread.sleep(1000);
		doClick(level4);

	}

	public void level4MenuSubMenuHandlingUsingMouseHover(By level1, By level2, By level3, By level4)
			throws InterruptedException {

		Actions act = new Actions(driver);

		act.moveToElement(getElement(level1)).perform();
		Thread.sleep(1000);

		act.moveToElement(getElement(level2)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(level3)).perform();
		Thread.sleep(1000);
		doClick(level4);

	}

	// *******************Wait Utils***************//

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	
	@Step("Waiting for the element: {0} with wait time: {1}")
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element =  wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		highlightElement(element);
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	@Step("Waiting for the element: {0} with wait time: {1}")
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;

	}
	@Step("Waiting for the element: {0} with wait time: {1}")
	public WebElement waitForElementVisible(By locator, int timeOut, int intervalTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime)).ignoring(NoSuchElementException.class)
				.withMessage("===element is not found===");

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;

	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	@Step("Waiting for the element: {0} with wait time: {1}")
	public List<WebElement> waitForPresenceOfElemenetsLocated(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForVisibilityOfElemenetsLocated(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		} catch (Exception e) {
			return List.of();//return empty arraylist
		}
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public void clickWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	public String waitForTitleContains(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title not found");
		}
		return driver.getTitle();
	}

	public String waitForTitleToBe(String titleVal, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			wait.until(ExpectedConditions.titleIs(titleVal));
				return driver.getTitle();
			
		} catch (TimeoutException e) {
			System.out.println("title not found");
			return null;
		}
	}

	public String waitForURLContains(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL not found");
		}
		return driver.getCurrentUrl();
	}

	public String waitForURLToBe(String urlValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL not found");
		}
		return driver.getCurrentUrl();
	}

	public Alert waitForJSAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public Alert waitForJSAlert(int timeOut, int intervalTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime)).ignoring(NoAlertPresentException.class)
				.withMessage("===alert is not found===");
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeOut) {
		Alert alert = waitForJSAlert(timeOut);
		String text = alert.getText();
		alert.accept();
		return text;
	}

	public void acceptAlert(int timeOut) {
		waitForJSAlert(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {
		waitForJSAlert(timeOut).dismiss();
	}

	public void alertSendKeys(int timeOut, String value) {
		Alert alert = waitForJSAlert(timeOut);
		alert.sendKeys(value);
		alert.accept();
	}

	// wait for iframes/frame:
	/**
	 * An expectation for checking whether the given frame is available to switch
	 * to. If the frame is available it switches the given driver to the specified
	 * frame.
	 * 
	 * @param frameLocator
	 * @param timeOut
	 */
	public void waitForFrameByLocator(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void waitForFrameByLocator(By frameLocator, int timeOut, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime)).ignoring(NoSuchFrameException.class)
				.withMessage("===frame is not found===");

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));

	}

	public void waitForFrameByIndex(int frameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));

	}

	public void waitForFrameByIndex(String frameIDOrName, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));

	}

	public void waitForFrameByIndex(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));

	}
	
}

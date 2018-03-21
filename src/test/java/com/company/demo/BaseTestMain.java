package com.company.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseTestMain {

	public String testBranch_Data = "src/main/resources/config/testdata_branch.xml";
	public String testStaff_Data = "src/main/resources/config/testdata_staff.xml";

	public void sendKeys(WebDriver webdriver, WebElement element, String value) {
		WebDriverWait wait = new WebDriverWait(webdriver, 10);
		element = wait.until(ExpectedConditions.visibilityOf(element));
		if (null != element && element.isDisplayed()) {
			element.clear();
			element.sendKeys(value, Keys.TAB);
			wait.until(ExpectedConditions.textToBePresentInElementValue(element, value));
		}
	}
	

	public void doClick(WebDriver webdriver, By selector) {
		WebDriverWait wait = new WebDriverWait(webdriver, 10);
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(selector));
		element = wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
	

}

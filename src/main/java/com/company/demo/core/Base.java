package com.company.demo.core;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.company.demo.driver.CustomDriver;

public class Base {

	public String testBranch_Data = "src/main/resources/config/testdata_branch.xml";
	public String testStaff_Data = "src/main/resources/config/testdata_staff.xml";
	public WebDriver webdriver;

	/*public static WebDriver initializeWebDriverChrome() {
		WebDriver webdriver = CustomDriver.InitializeWebdriver(BrowserType.CHROME);
		webdriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return webdriver;
	}

	public WebDriver getWebDriver() {
		return this.webdriver;
	}*/

	public Base(WebDriver webdriver) {
		this.webdriver = webdriver;
	}

	/*public Base() {
		init();
		System.out.println("base class");
	}

	private void init() {
		if (webdriver == null) {
			webdriver = initializeWebDriverChrome();
			webdriver.get(Utility.app_url);
		}
	}*/

	public void closeWebdDriver() {
		if (null != webdriver) {
			webdriver.quit();
		}
	}

	public void sendKeys(WebElement element, String value) {
		WebDriverWait wait = new WebDriverWait(webdriver, 10);
		element = wait.until(ExpectedConditions.visibilityOf(element));
		if (null != element && element.isDisplayed()) {
			element.clear();
			element.sendKeys(value, Keys.TAB);
			wait.until(ExpectedConditions.textToBePresentInElementValue(element, value));
		}
	}

	public void doClick(By selector) {
		WebDriverWait wait = new WebDriverWait(webdriver, 10);
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(selector));
		element = wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	public boolean isElementNotVisible(By by) {
		WebDriverWait wait = new WebDriverWait(webdriver, 10);
		return wait.until(ExpectedConditions.invisibilityOf(webdriver.findElement(by)));
	}

	public void isElementVisible(By by) {
		WebDriverWait wait = new WebDriverWait(webdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(webdriver.findElement(by)));
	}

	public void isEquals(int object1, int object2) {
		System.out.println("Elements are compared and value is : " + (object1 == object2));
	}

	public void isEquals(String object1, String object2) {
		System.out.println("String elements are compared and value is : " + (object1.equals(object2)));
	}

	public void doClick(WebElement element) {
		WebDriverWait wait = new WebDriverWait(webdriver, 10);
		element = wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	public void doClickByXpath(String name, WebDriver driver) {
		WebElement element = driver.findElement(By.xpath(name));
		doClick(element);
	}

	public void takeScreenshot() throws IOException {
		File src = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File("C:/selenium/Screenshot/error.png"));
	}

	public static WebDriver initializeBrowser() {
		WebDriver driver = CustomDriver.InitializeWebdriver(BrowserType.CHROME);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(Utility.app_url);
		return driver;
	}
}

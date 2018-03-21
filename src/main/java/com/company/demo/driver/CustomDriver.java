package com.company.demo.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

public class CustomDriver implements BrowserType {

	// Takes Browsercode and initialize the particular browser
	public static WebDriver InitializeWebdriver(String BrowserCode ) {
		WebDriver driver = null;
		switch (BrowserCode) {
		case IE:
			System.setProperty("webdriver.ie.driver", "src/main/resources/drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			break;

		case FIREFOX:
			System.setProperty("webdriver.firefox.marionette", "src/main/resources/drivers/geckodriver.exe");
			driver = new FirefoxDriver();
			break;

		case CHROME:
		default:
			System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		driver.manage().window().maximize();
		return driver;
	}

}

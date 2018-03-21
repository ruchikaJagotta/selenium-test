package com.company.demo.login;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.company.demo.core.Base;
import com.company.demo.core.Utility;

public class Login extends Base {

	public Login(WebDriver webdriver) {
		super(webdriver);
	}

	public void logout() {
		int menuItems = webdriver.findElements(By.xpath("//*[@id=\"navbar-collapse\"]/ul/li")).size();
		if (menuItems == 3) {
			doClick(By.xpath("//span[@translate='global.menu.account.main']"));
			doClick(By.xpath("//span[@translate='global.menu.account.logout']"));
		} else if (menuItems == 2) {
			doClick(By.xpath("//span[@translate='global.menu.home']"));
		}

	}

	public void loginTest() {
		try {
			// To Login to page
			doClick(webdriver.findElement(By.linkText(Utility.getControls("Login_link"))));
			sendKeys(webdriver.findElement(By.id(Utility.getControls("Login_Username"))),
					Utility.getTestData("TestData_Login", "Username", testBranch_Data));
			sendKeys(webdriver.findElement(By.id(Utility.getControls("Login_Password"))),
					Utility.getTestData("TestData_Login", "Password", testBranch_Data));
			doClick(webdriver.findElement(By.xpath(Utility.getControls("Login_Button"))));
			WebElement home = webdriver.findElement(By.xpath("//span[@translate='global.menu.home']"));
			assertTrue(home.isDisplayed(), "Login is not sucessfull");
		} catch (IOException e) {
			Logger.getLogger(Login.class).error(e);
		}
	}

	public void loginTest_Unauthenticate() {

		try {
			// To Login to page
			doClick(webdriver.findElement(By.linkText(Utility.getControls("Login_link"))));
			webdriver.findElement(By.id(Utility.getControls("Login_Username")))
					.sendKeys(Utility.getTestData("TestData_Login_Unauthenticate", "Username", testBranch_Data));
			webdriver.findElement(By.id(Utility.getControls("Login_Password")))
					.sendKeys(Utility.getTestData("TestData_Login_Unauthenticate", "Password", testBranch_Data));
			doClick(webdriver.findElement(By.xpath(Utility.getControls("Login_Button"))));

			String authentication_error = webdriver
					.findElement(By.xpath(Utility.getControls("Login_Authentication_error"))).getText();

			String Expected_Error = Utility.getMessages("login_error");
			assertEquals(authentication_error, Expected_Error, "Authentication Error doesn't match");
		} catch (IOException e) {
			Logger.getLogger(Login.class).error(e);
		}
	}

	public void loginTest_Succesful() {
		try {
			doClick(webdriver.findElement(By.linkText(Utility.getControls("Login_link"))));
			webdriver.findElement(By.id(Utility.getControls("Login_Username")))
					.sendKeys(Utility.getTestData("TestData_Login", "Username", testBranch_Data));
			webdriver.findElement(By.id(Utility.getControls("Login_Password")))
					.sendKeys(Utility.getTestData("TestData_Login", "Password", testBranch_Data));
			doClick(webdriver.findElement(By.xpath(Utility.getControls("Login_Button"))));

			String authentication_message = webdriver.findElement(By.xpath(Utility.getControls("Login_Authentication")))
					.getText();
			String Expected_message = Utility.getMessages("Login_successful");
			assertEquals(authentication_message, Expected_message, "Login is not successful");
		} catch (IOException e) {
			Logger.getLogger(Login.class).error(e);
		}
	}

}
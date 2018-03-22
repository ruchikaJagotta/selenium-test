package com.company.demo.login;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.company.demo.core.Base;
import com.company.demo.core.Utility;

public class Login extends Base {

	public Login(WebDriver webdriver) {
		super(webdriver);
	}


	public void logout() {
		try {
			int menuItems = webdriver.findElements(By.xpath(Utility.getControls("menuItems_login"))).size();
			if (menuItems == 3) {
				doClick(By.xpath(Utility.getControls("dropdown_account_login")));
				doClick(By.xpath(Utility.getControls("dropdown_logout_login")));
			} else if (menuItems == 2) {
				doClick(By.xpath(Utility.getControls("dropdown_home_login")));
			}
		} catch (IOException e) {
			Logger.getLogger(Login.class).error(e);
		}

	}

	public void loginTest() {
		try {
			// To Login to page
			doClick(webdriver.findElement(By.linkText(Utility.getControls("link_Login"))));
			sendKeys(webdriver.findElement(By.id(Utility.getControls("input_username_login"))),
					Utility.getTestData("TestData_Login", "Username", testBranch_Data));
			sendKeys(webdriver.findElement(By.id(Utility.getControls("input_password_login"))),
					Utility.getTestData("TestData_Login", "Password", testBranch_Data));
			doClick(webdriver.findElement(By.xpath(Utility.getControls("button_authenticate_login"))));
			WebElement home = webdriver.findElement(By.xpath(Utility.getControls("dropdown_home_login")));
			Assert.assertTrue(home.isDisplayed(), "Login is not sucessfull");
		} catch (IOException e) {
			Logger.getLogger(Login.class).error(e);
		}
	}

	public void loginTest_Unauthenticate() {

		try {
			// To Login to page
			doClick(webdriver.findElement(By.linkText(Utility.getControls("link_Login"))));
			webdriver.findElement(By.id(Utility.getControls("input_username_login")))
					.sendKeys(Utility.getTestData("TestData_Login_Unauthenticate", "Username", testBranch_Data));
			webdriver.findElement(By.id(Utility.getControls("input_password_login")))
					.sendKeys(Utility.getTestData("TestData_Login_Unauthenticate", "Password", testBranch_Data));
			doClick(webdriver.findElement(By.xpath(Utility.getControls("button_authenticate_login"))));

			String authentication_error = webdriver
					.findElement(By.xpath(Utility.getControls("text_authenticationerror_login"))).getText();

			String Expected_Error = Utility.getMessages("login_error");
			Assert.assertEquals(authentication_error, Expected_Error, "Authentication Error doesn't match");
		} catch (IOException e) {
			Logger.getLogger(Login.class).error(e);
		}
	}

	public void loginTest_Succesful() {
		try {
			doClick(webdriver.findElement(By.linkText(Utility.getControls("link_Login"))));
			webdriver.findElement(By.id(Utility.getControls("input_username_login")))
					.sendKeys(Utility.getTestData("TestData_Login", "Username", testBranch_Data));
			webdriver.findElement(By.id(Utility.getControls("input_password_login")))
					.sendKeys(Utility.getTestData("TestData_Login", "Password", testBranch_Data));
			doClick(webdriver.findElement(By.xpath(Utility.getControls("button_authenticate_login"))));

			String authentication_message = webdriver.findElement(By.xpath(Utility.getControls("text_authentication_login")))
					.getText();
			String Expected_message = Utility.getMessages("Login_successful");
			Assert.assertEquals(authentication_message, Expected_message, "Login is not successful");
		} catch (IOException e) {
			Logger.getLogger(Login.class).error(e);
		}
	}

}
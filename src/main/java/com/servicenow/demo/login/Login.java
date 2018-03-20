package com.servicenow.demo.login;

import org.openqa.selenium.By;

import com.servicenow.demo.core.Base;
import com.servicenow.demo.core.Utility;

public class Login extends Base {

	public Login() {
		super();
	}

	public static void main(String[] args) {
		Login login = new Login();
		try {
			login.loginTest();
			login.logout();
			login.loginTest_Succesful();
			login.logout();
			login.loginTest_Unauthenticate();
		} finally {
			if (null != login.getWebDriver()) {
				login.getWebDriver().quit();
			}
		}
		// to quit the driver //

	}

	private void logout() {
		doClick(By.xpath("//span[@translate='global.menu.account.main']"));
		doClick(By.xpath("//span[@translate='global.menu.account.logout']"));
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

		} catch (Exception e) {
			System.out.println("Exceptioon" + e);
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
			System.out.println(authentication_error);

			String Expected_Error = Utility.getMessages("login_error");
			isEquals(authentication_error, Expected_Error);
		} catch (Exception e) {
			System.out.println("exception " + e);
		}
	}

	public void loginTest_Succesful() {

		try {
			// To Login to page
			doClick(webdriver.findElement(By.linkText(Utility.getControls("Login_link"))));
			webdriver.findElement(By.id(Utility.getControls("Login_Username")))
					.sendKeys(Utility.getTestData("TestData_Login", "Username", testBranch_Data));
			webdriver.findElement(By.id(Utility.getControls("Login_Password")))
					.sendKeys(Utility.getTestData("TestData_Login", "Password", testBranch_Data));
			doClick(webdriver.findElement(By.xpath(Utility.getControls("Login_Button"))));

			String authentication_message = webdriver.findElement(By.xpath(Utility.getControls("Login_Authentication")))
					.getText();
			System.out.println(authentication_message);

			String Expected_message = Utility.getMessages("Login_successful");
			isEquals(authentication_message, Expected_message);
		} catch (Exception e) {
			System.out.println("exception " + e);
		}
	}

}
package com.servicenow.demo;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeGroups;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;

import com.servicenow.demo.branch.Branch;
import com.servicenow.demo.core.Utility;
import com.servicenow.demo.driver.CustomDriver;
import com.servicenow.demo.login.Login;
import com.servicenow.demo.reports.GenerateMail;

public class TestMain extends BaseTestMain {

	private WebDriver driver = null;

	@BeforeClass
	public void setup() {
		if (driver == null) {
			driver = CustomDriver.InitializeWebdriver(BrowserType.CHROME);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}

	@Test(priority = 1)
	public void login() {
		Login login = new Login();
		driver.get(Utility.app_url);
		login.loginTest();
		System.out.println("called from method 1");
	}

	@Test(priority = 2)
	public void login_succesfull() throws IOException {
		Branch branch = new Branch();
		driver.get(Utility.app_url);
		branch.loginTest();
		branch.clickOnBrnach();
		branch.addBranches();
		System.out.println("called from method 2");
	}

	@AfterClass
	public void generateMail() {
		if (null != driver) {
			driver.quit();
		}
		GenerateMail.sendPDFReportByGMail("ruchika.test.from@gmail.com", "*******", "ruchika.test.to@gmail.com",
				"TestNG report from Eclipse Run", "Enclosed please find attachment");
	}

}

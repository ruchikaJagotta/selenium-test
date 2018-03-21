package com.servicenow.demo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.servicenow.demo.branch.Branch;
import com.servicenow.demo.core.Utility;
import com.servicenow.demo.driver.CustomDriver;
import com.servicenow.demo.login.Login;
import com.servicenow.demo.reports.GenerateMail;
import com.servicenow.demo.staff.Staff;

public class BranchTest extends BaseTestMain {

	private WebDriver driver = null;
	private Login login = null;
	private Branch branch = null;
	private Staff staff = null;

	@BeforeClass
	public void setup() {
		if (driver == null) {
			driver = CustomDriver.InitializeWebdriver(BrowserType.CHROME);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(Utility.app_url);
			login = new Login(driver);
			branch = new Branch(driver);
			staff = new Staff(driver);
		}
	}

	@Test(priority = 1)
	public void login() {
	//	driver.get(Utility.app_url);
		System.out.println("Login test will start");
		login.loginTest();
		System.out.println("called from method 1");
	}

	@Test
	public void Login_Succesfull() {
		driver.get(Utility.app_url);
		System.out.println("Login successfull start");
		login.loginTest_Succesful();
		System.out.println("Called from login ");
	}

	@Test(priority = 2)
	public void add_Branches() throws IOException {
		branch.loginTest();
		branch.clickOnBrnach();
		branch.addBranches();
		System.out.println("called from method 2");
	}

	@AfterMethod
	public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			System.out.println(testResult.getStatus());
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("errorScreenshots\\" + testResult.getName() + "-"
					+ Arrays.toString(testResult.getParameters()) + ".jpg"));
		}
	}

	@AfterSuite
	public void generateMail() {
		if (null != driver) {
			driver.quit();
		}
		GenerateMail.sendPDFReportByGMail("TestNG report from Eclipse Run", "Enclosed please find attachment");
	}

}

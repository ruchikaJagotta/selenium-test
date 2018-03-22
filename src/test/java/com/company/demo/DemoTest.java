package com.company.demo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.company.demo.branch.Branch;
import com.company.demo.core.Base;
import com.company.demo.login.Login;
import com.company.demo.reports.SendMail;
import com.company.demo.staff.Staff;

public class DemoTest {

	private WebDriver driver = null;
	private Login login = null;
	private Branch branch = null;
	private Staff staff = null;

	@BeforeClass
	public void setup() {
		if (driver == null) {
			driver = Base.initializeBrowser();
			login = new Login(driver);
			branch = new Branch(driver);
			staff = new Staff(driver);
		}
	}

	@Test(priority=1)
	public void Login() {
		login.loginTest();
		login.logout();
	}

	@Test(priority=2)
	public void Login_Succesfull() {
		login.loginTest_Succesful();
		login.logout();
	}

	@Test(priority=0)
	public void Login_Error() {
		login.loginTest_Unauthenticate();
		login.logout();
	}
	
	@Test(priority = 3)
	public void AddBranch() {
		branch.loginTest();
		branch.clickOnBrnach();
		branch.addBranches();
	}

	@Test(priority = 4)
	public void ViewBranch() {
		branch.loginTest();
		branch.clickOnBrnach();
		branch.viewBranch();
	}

	@Test(priority = 5)
	public void EditBranch() {
		branch.loginTest();
		branch.clickOnBrnach();
		branch.editBranch();
	}
	
	@Test(priority = 6)
	public void deleteBranch() {
		branch.loginTest();
		branch.clickOnBrnach();
		branch.deleteBranch();
	}
	
	@Test(priority=7)
	public void validateMinLengthBranch() {
		branch.loginTest();
		branch.clickOnBrnach();
		branch.addBranch_minLength();
	}

	@Test(priority=7)
	public void validateMaxLengthBranch() {
		branch.loginTest();
		branch.clickOnBrnach();
		branch.addBranch_maxLength();
	}

	@Test(priority=7)
	public void validatePatternBranch() {
		branch.loginTest();
		branch.clickOnBrnach();
		branch.addBranch_Pattern();
	}
	
	@Test(priority=8)
	public void AddStaff() {
		staff.loginTest();
		staff.clickOnStaff();
		staff.addStaff();
	}

	@Test(priority = 9)
	public void ViewStaff() {
		staff.loginTest();
		staff.clickOnStaff();
		staff.viewStaff();
	}

	@Test(priority = 10)
	public void EditStaff() {
		staff.loginTest();
		staff.clickOnStaff();
		staff.editStaff();
	}

	@Test(priority =11)
	public void deleteStaff() {
		staff.loginTest();
		staff.clickOnStaff();
		staff.deleteStaff();
	}
	
	@Test(priority=12)
	public void validateMinLengthStaff() {
		staff.loginTest();
		staff.clickOnStaff();
		staff.addStaff_Mandatory();
	}

	@Test(priority=12)
	public void validateMaxLengthStaff() {
		staff.loginTest();
		staff.clickOnStaff();
		staff.addStaff_maxLength();
	}

	@Test(priority=12)
	public void validatePatternStaff() {
		staff.loginTest();
		staff.clickOnStaff();
		staff.addStaff_Pattern();
	}

	@AfterMethod
	public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			System.out.println(testResult.getStatus());
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("../../Screenshot/" + testResult.getName() + "-"
					+ Arrays.toString(testResult.getParameters()) + ".jpg"));
		}
		login.logout();
	}

	@AfterSuite
	public void tearDown() {
		if (null != driver) {
			driver.quit();
		}
		SendMail.sendPDFReportByGMail("TestNG report from Eclipse Run", "Enclosed please find attachment");
	}

}

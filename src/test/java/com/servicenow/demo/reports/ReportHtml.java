package com.servicenow.demo.reports;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.servicenow.demo.login.Login;

public class ReportHtml {
	ExtentReports report;
	ExtentTest logger;

	@Test
	public void verifysum() throws IOException {

		// Create object for Report with filepath
		report = new ExtentReports("./Reports/TestReport.html");

		Login login = new Login();
				
		// Start the test
		logger = report.startTest("LoginTest");
		login.loginTest();
		Assert.assertTrue(true);
		// Log the status in report
		logger.log(LogStatus.INFO, "login Started ");

		// Pass the test in report
		logger.log(LogStatus.PASS, "Login Verified");

		// End the test
		report.endTest(logger);

		// Flush the data to report
		report.flush();
	}

}

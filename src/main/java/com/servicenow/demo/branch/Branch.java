package com.servicenow.demo.branch;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.log4testng.Logger;

import com.google.gson.Gson;
import com.servicenow.demo.core.Base;
import com.servicenow.demo.core.DeleteObject;
import com.servicenow.demo.core.Utility;
import com.servicenow.demo.login.Login;

public class Branch extends Login {

	// private WebDriver webdriver;
	public Branch(WebDriver webdriver) {
		super(webdriver);
	}

	public static void main(String[] args) {
		Branch branch = new Branch(Base.initializeBrowser());
		branch.loginTest();
		branch.clickOnBrnach();
		branch.deleteBranch();
	}

	public void clickOnBrnach() {
		try {
			webdriver.findElement(By.xpath(Utility.getControls("Entities_Link"))).click();
			webdriver.findElement(By.xpath(Utility.getControls("Branch_Link"))).click();
		} catch (IOException e) {
			Logger.getLogger(Branch.class).error(e);
		}
	}

	public void addBranches() {
		try {
			List<String> list = Utility.getTestDataAdd("TestData_AddBranch", "name", testBranch_Data);
			List<String> lList = Utility.getTestDataAdd("TestData_AddBranch", "code", testBranch_Data);
			for (int temp = 0; temp < list.size(); temp++) {
				int elementsPresentInList = webdriver.findElements(By.xpath(Utility.getControls("branch_list"))).size();

				doClick(webdriver.findElement(By.xpath(Utility.getControls("branch_create_new"))));
				sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_name"))), list.get(temp));
				sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_code"))), lList.get(temp));
				doClick(By.cssSelector(Utility.getControls("branch_save")));
				isElementNotVisible(By.id("saveBranchModal"));
				int elementsInList = webdriver.findElements(By.xpath(Utility.getControls("branch_list"))).size();
				Assert.assertEquals(elementsInList, elementsPresentInList + 1);
			}
		}

		catch (IOException e) {
			Logger.getLogger(Branch.class).error(e);
		}
	}

	public void addBranch_minLength() {
		try {
			String minName = Utility.getTestData("TestData_minBranch", "name", testBranch_Data);
			String minCode = Utility.getTestData("TestData_minBranch", "code", testBranch_Data);

			String Expected_mes1 = Utility.getMessages("branch_create_name_min");
			String Expected_mes2 = Utility.getMessages("branch_create_code_min");

			doClick(webdriver.findElement(By.xpath(Utility.getControls("branch_create_new"))));
			isElementVisible(By.id("saveBranchModal"));
			sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_name"))), minName);
			sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_code"))), minCode);

			String message_min_length1 = webdriver.findElement(By.xpath(Utility.getControls("branch_minlength_name")))
					.getText();
			String message_min_length2 = webdriver.findElement(By.xpath(Utility.getControls("branch_minlength_code")))
					.getText();

			doClick(webdriver.findElement(By.xpath(Utility.getControls("button_cancel"))));
			isElementNotVisible(By.id("saveBranchModal"));
			Assert.assertEquals(message_min_length1, Expected_mes1, "Error messages doesn't match");
			Assert.assertEquals(message_min_length2, Expected_mes2, "Error messages doesn't match");
		} catch (IOException e) {
			Logger.getLogger(Branch.class).error(e);
		}
	}

	public void addBranch_Pattern() {
		try {
			String patternName = Utility.getTestData("TestData_PatternBranch", "name", testBranch_Data);
			String patternCode = Utility.getTestData("TestData_PatternBranch", "code", testBranch_Data);

			String Expected_mes1 = Utility.getMessages("branch_create_name_no");
			String Expected_mes2 = Utility.getMessages("branch_create_code_letter");

			doClick(webdriver.findElement(By.xpath(Utility.getControls("branch_create_new"))));
			isElementVisible(By.id("saveBranchModal"));
			sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_name"))), patternName);
			sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_code"))), patternCode);

			String message_Pattern1 = webdriver.findElement(By.xpath(Utility.getControls("pattern_name"))).getText();
			String message_Pattern2 = webdriver.findElement(By.xpath(Utility.getControls("branch_pattern_code")))
					.getText();

			doClick(webdriver.findElement(By.xpath(Utility.getControls("button_cancel"))));
			isElementNotVisible(By.id("saveBranchModal"));

			Assert.assertEquals(message_Pattern1, Expected_mes1, "Error messages doesn't match");
			Assert.assertEquals(message_Pattern2, Expected_mes2, "Error messages doesn't match");
		} catch (IOException e) {
			Logger.getLogger(Branch.class).error(e);
		}
	}

	public void addBranch_maxLength() {
		try {
			String maxName = Utility.getTestData("TestData_maxBranch", "name", testBranch_Data);
			String maxCode = Utility.getTestData("TestData_maxBranch", "code", testBranch_Data);

			String Expected_mes1 = Utility.getMessages("branch_create_name_max");

			String Expected_mes2 = Utility.getMessages("branch_create_code_max");

			doClick(webdriver.findElement(By.xpath(Utility.getControls("branch_create_new"))));
			isElementVisible(By.id("saveBranchModal"));
			sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_name"))), maxName);
			sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_code"))), maxCode);

			String message_max_length1 = webdriver.findElement(By.xpath(Utility.getControls("maxLength_name")))
					.getText();

			String message_max_length2 = webdriver.findElement(By.xpath(Utility.getControls("branch_maxlength_code")))
					.getText();

			doClick(webdriver.findElement(By.xpath(Utility.getControls("button_cancel"))));
			isElementNotVisible(By.id("saveBranchModal"));
			Assert.assertEquals(message_max_length1, Expected_mes1, "Error messages doesn't match");
			Assert.assertEquals(message_max_length2, Expected_mes2, "Error messages doesn't match");

		} catch (IOException e) {
			Logger.getLogger(Branch.class).error(e);
		}

	}

	public void viewBranch() {
		try {
			WebElement id_element = webdriver.findElement(By.xpath(Utility.getControls("text_view_first_id")));
			String expectedValue = "Branch " + id_element.getText();

			// check by id
			doClick(id_element);
			String actualValue = webdriver.findElement(By.xpath(Utility.getControls("view_title"))).getText();
			Assert.assertEquals(actualValue, expectedValue, "View id doesn't match");
			doClick(webdriver.findElement(By.cssSelector(Utility.getControls("view_back"))));

			// verify by button
			doClick(webdriver.findElement(By.xpath(Utility.getControls("view_button"))));
			actualValue = webdriver.findElement(By.xpath(Utility.getControls("view_title"))).getText();
			Assert.assertEquals(actualValue, expectedValue, "View id doesn't match");
			doClick(webdriver.findElement(By.cssSelector(Utility.getControls("view_back"))));
		} catch (IOException e) {
			Logger.getLogger(Branch.class).error(e);
		}
	}

	public void editBranch() {
		try {
			doClick(webdriver.findElement(By.xpath(Utility.getControls("edit_button"))));

			// To edit name n code
			List<String> name = Utility.getTestDataAdd("TestData_EditBranch", "name", testBranch_Data);
			List<String> code = Utility.getTestDataAdd("TestData_EditBranch", "code", testBranch_Data);
			WebElement el_name = webdriver.findElement(By.xpath(Utility.getControls("branch_create_name")));
			WebElement el_code = webdriver.findElement(By.xpath(Utility.getControls("branch_create_code")));
			sendKeys(el_name, name.get(0));
			sendKeys(el_code, code.get(0));
			doClick(By.cssSelector(Utility.getControls("branch_save")));
			isElementNotVisible(By.id("saveBranchModal"));
		} catch (IOException e) {
			Logger.getLogger(Branch.class).error(e);
		}
	}

	public void deleteBranch() {
		try {
			// To check the id being deleted
			int elementsPresentInList = webdriver.findElements(By.xpath(Utility.getControls("branch_list"))).size();
			WebElement id_element = webdriver.findElement(By.xpath(Utility.getControls("text_view_first_id")));
			String id = id_element.getText();

			// Click on delete button
			doClick(webdriver.findElement(By.xpath(Utility.getControls("delete_button"))));

			isElementVisible(By.id("deleteBranchConfirmation"));
			// Check the staff id on confirm pop up
			Gson gson = new Gson();
			String delete_text = webdriver.findElement(By.xpath(Utility.getControls("branch_delete_text")))
					.getAttribute("translate-values");
			DeleteObject object = gson.fromJson(delete_text, DeleteObject.class);
			if (id.equals(object.getId())) {
				doClick(By.cssSelector(Utility.getControls("branch_delete_confirm")));
			}
			isElementNotVisible(By.id("deleteBranchConfirmation"));

			int elementsPresentList = webdriver.findElements(By.xpath(Utility.getControls("branch_list"))).size();
			Assert.assertEquals(elementsPresentInList - 1, elementsPresentList);
		} catch (IOException e) {
			Logger.getLogger(Branch.class).error(e);
		}
	}
}

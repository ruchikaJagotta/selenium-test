package com.servicenow.demo.branch;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.servicenow.demo.core.Utility;
import com.servicenow.demo.login.Login;

public class Branch extends Login {

	public Branch() {
		super();
	}

	public static void main(String[] args) {
		Branch branch = new Branch();
		try {
			branch.loginTest();
			branch.clickOnBrnach();
			branch.addBranches();
			branch.viewBranch();
			branch.editBranch();
			branch.deleteBranch();
			branch.addBranch_minLength();
			branch.addBranch_maxLength();
			branch.addBranch_Pattern();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void clickOnBrnach() {
		try {
			webdriver.findElement(By.xpath(Utility.getControls("Entities_Link"))).click();
			webdriver.findElement(By.xpath(Utility.getControls("Branch_Link"))).click();
		} catch (Exception e) {
			System.out.println("Exceptioon" + e);
		}
	}

	public void addBranches() {
		System.out.println("inside addBranches method");
		try {
			List<String> list = Utility.getTestDataAdd("TestData_AddBranch", "name", testBranch_Data);
			List<String> lList = Utility.getTestDataAdd("TestData_AddBranch", "code", testBranch_Data);

			System.out.println(list.size());
			for (int temp = 0; temp < list.size(); temp++) {
				int elementsPresentInList = webdriver.findElements(By.xpath(Utility.getControls("branch_list"))).size();

				doClick(webdriver.findElement(By.xpath(Utility.getControls("branch_create_new"))));
				sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_name"))), list.get(temp));
				sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_code"))), lList.get(temp));
				doClick(By.cssSelector(Utility.getControls("branch_save")));
				isElementNotVisible(By.id("saveBranchModal"));
				int elementsInList = webdriver.findElements(By.xpath(Utility.getControls("branch_list"))).size();
				System.out.println("final list no=" + elementsInList);
				isEquals(elementsInList, elementsPresentInList + 1);
			}
		}

		catch (Exception e) {
			System.out.println("Exceptioon" + e);
		}
	}

	public void addBranch_minLength() throws IOException {
		System.out.println("inside addBranch_minLength method");
		String minName = Utility.getTestData("TestData_minBranch", "name", testBranch_Data);
		String minCode = Utility.getTestData("TestData_minBranch", "code", testBranch_Data);

		String Expected_mes1 = Utility.getMessages("branch_create_name_min");
		System.out.println(Expected_mes1);
		String Expected_mes2 = Utility.getMessages("branch_create_code_min");
		System.out.println(Expected_mes2);

		doClick(webdriver.findElement(By.xpath(Utility.getControls("branch_create_new"))));
		isElementVisible(By.id("saveBranchModal"));
		sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_name"))), minName);
		sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_code"))), minCode);

		String message_min_length1 = webdriver.findElement(By.xpath(Utility.getControls("branch_minlength_name")))
				.getText();
		System.out.println(message_min_length1);
		String message_min_length2 = webdriver.findElement(By.xpath(Utility.getControls("branch_minlength_code")))
				.getText();
		System.out.println(message_min_length2);

		doClick(webdriver.findElement(By.xpath(Utility.getControls("button_cancel"))));
		isElementNotVisible(By.id("saveBranchModal"));
		if (message_min_length1.equals(Expected_mes1) && message_min_length2.equals(Expected_mes2)) {
			System.out.println("Error message matched");
		} else
			System.out.println("Failed");
	}

	public void addBranch_Pattern() throws IOException {
		System.out.println("inside addBranch_Pattern method");
		String patternName = Utility.getTestData("TestData_PatternBranch", "name", testBranch_Data);
		String patternCode = Utility.getTestData("TestData_PatternBranch", "code", testBranch_Data);

		String Expected_mes1 = Utility.getMessages("branch_create_name_no");
		System.out.println("expected message = " + Expected_mes1);
		String Expected_mes2 = Utility.getMessages("branch_create_code_letter");
		System.out.println(Expected_mes2);

		doClick(webdriver.findElement(By.xpath(Utility.getControls("branch_create_new"))));
		isElementVisible(By.id("saveBranchModal"));
		sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_name"))), patternName);
		sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_code"))), patternCode);

		String message_Pattern1 = webdriver.findElement(By.xpath(Utility.getControls("branch_pattern_name"))).getText();
		System.out.println(message_Pattern1);
		String message_Pattern2 = webdriver.findElement(By.xpath(Utility.getControls("branch_pattern_code"))).getText();
		System.out.println(message_Pattern2);

		doClick(webdriver.findElement(By.xpath(Utility.getControls("button_cancel"))));
		isElementNotVisible(By.id("saveBranchModal"));
		if (message_Pattern1.equals(Expected_mes1) && message_Pattern2.equals(Expected_mes2))
			System.out.println("Error message matched");
		else
			System.out.println("Failed");

	}

	public void addBranch_maxLength() throws IOException {
		System.out.println("inside addBranch_maxLength method");
		String maxName = Utility.getTestData("TestData_maxBranch", "name", testBranch_Data);
		String maxCode = Utility.getTestData("TestData_maxBranch", "code", testBranch_Data);

		String Expected_mes1 = Utility.getMessages("branch_create_name_max");
		System.out.println(Expected_mes1);

		String Expected_mes2 = Utility.getMessages("branch_create_code_max");
		System.out.println(Expected_mes2);

		doClick(webdriver.findElement(By.xpath(Utility.getControls("branch_create_new"))));
		isElementVisible(By.id("saveBranchModal"));
		sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_name"))), maxName);
		sendKeys(webdriver.findElement(By.xpath(Utility.getControls("branch_create_code"))), maxCode);

		String message_max_length1 = webdriver.findElement(By.xpath(Utility.getControls("branch_maxlength_name")))
				.getText();
		System.out.println(message_max_length1);

		String message_max_length2 = webdriver.findElement(By.xpath(Utility.getControls("branch_maxlength_code")))
				.getText();

		doClick(webdriver.findElement(By.xpath(Utility.getControls("button_cancel"))));
		isElementNotVisible(By.id("saveBranchModal"));
		if (message_max_length1.equals(Expected_mes1) && message_max_length2.equals(Expected_mes2)) {
			System.out.println("Error message matched");
		} else
			System.out.println("Failed");

	}

	public void viewBranch() throws IOException {
		System.out.println("inside viewBranch method");
		WebElement id_element = webdriver.findElement(By.xpath(Utility.getControls("branch_view_first_id")));
		String expectedValue = "Branch " + id_element.getText();

		// check by id
		doClick(id_element);
		String actualValue = webdriver.findElement(By.xpath(Utility.getControls("branch_view_title"))).getText();

		isEquals(actualValue, expectedValue);

		doClick(webdriver.findElement(By.cssSelector(Utility.getControls("branch_view_back"))));

		// verify by button
		doClick(webdriver.findElement(By.xpath(Utility.getControls("branch_view_button"))));
		actualValue = webdriver.findElement(By.xpath(Utility.getControls("branch_view_title"))).getText();
		isEquals(actualValue, expectedValue);
		doClick(webdriver.findElement(By.cssSelector(Utility.getControls("branch_view_back"))));

	}

	public void editBranch() throws IOException {
		System.out.println("inside editBranch method");
		doClick(webdriver.findElement(By.xpath(Utility.getControls("branch_edit_button"))));

		// To edit name n code
		List<String> name = Utility.getTestDataAdd("TestData_EditBranch", "name", testBranch_Data);
		List<String> code = Utility.getTestDataAdd("TestData_EditBranch", "code", testBranch_Data);
		WebElement el_name = webdriver.findElement(By.xpath(Utility.getControls("branch_create_name")));
		WebElement el_code = webdriver.findElement(By.xpath(Utility.getControls("branch_create_code")));
		sendKeys(el_name, name.get(0));
		sendKeys(el_code, code.get(0));
		doClick(By.cssSelector(Utility.getControls("branch_save")));
		isElementNotVisible(By.id("saveBranchModal"));
	}

	public void deleteBranch() throws IOException {
		System.out.println("inside deleteBranch method");
		// To check the id being deleted
		int elementsPresentInList = webdriver.findElements(By.xpath(Utility.getControls("branch_list"))).size();

		WebElement id_element = webdriver.findElement(By.xpath(Utility.getControls("branch_view_first_id")));
		String id = id_element.getText();
		System.out.println("Id on main page is " + id);

		// Click on delete button
		doClick(webdriver.findElement(By.xpath(Utility.getControls("branch_delete_button"))));
		System.out.println("branch delete button clicked");

		isElementVisible(By.id("deleteBranchConfirmation"));
		// Check the staff id on confirm pop up
		String delete_message = webdriver.findElement(By.xpath(Utility.getControls("branch_delete_text"))).getText();
		System.out.println(delete_message);
		String split_string = delete_message.split("Branch")[1];
		System.out.println(split_string);
		String deleteId = split_string.trim().split("\\?")[0].trim();
		if (id.equals(deleteId)) {
			doClick(webdriver.findElement(By.xpath(Utility.getControls("branch_delete_confirm"))));
		}
		isElementNotVisible(By.id("deleteBranchConfirmation"));

		int elementsPresentList = webdriver.findElements(By.xpath(Utility.getControls("branch_list"))).size();
		isEquals(elementsPresentInList - 1, elementsPresentList);

	}

}

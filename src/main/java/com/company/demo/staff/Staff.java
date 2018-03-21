package com.company.demo.staff;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.company.demo.core.Base;
import com.company.demo.core.DeleteObject;
import com.company.demo.core.Utility;
import com.company.demo.login.Login;
import com.google.gson.Gson;

public class Staff extends Login {

	public Staff(WebDriver webdriver) {
		super(webdriver);
	}

	public static void main(String[] args) {
		Staff staff = new Staff(Base.initializeBrowser());
		staff.loginTest();
		staff.clickOnStaff();
		staff.addStaff_Mandatory();
	}
	
	public void clickOnStaff() {
		try {
			webdriver.findElement(By.xpath(Utility.getControls("Entities_Link"))).click();
			webdriver.findElement(By.xpath(Utility.getControls("Staff_Link"))).click();
		} catch (Exception e) {
			Logger.getLogger(Staff.class).error(e);
		}

	}

	// To Add Staff name and branch in staff Page
	public void addStaff() {

		try {

			List<String> list = Utility.getTestDataAdd("TestData_AddStaff", "name", testStaff_Data);

			for (int temp = 0; temp < list.size(); temp++) {
				int elementsPresentInList = webdriver.findElements(By.xpath(Utility.getControls("staff_list"))).size();
				doClick(webdriver.findElement(By.xpath(Utility.getControls("staff_create_new"))));
				sendKeys(webdriver.findElement(By.xpath(Utility.getControls("staff_create_name"))), list.get(temp));
				Select dropdown = new Select(
						webdriver.findElement(By.xpath(Utility.getControls("staff_choose_branch"))));
				dropdown.selectByIndex(temp + 1);

				doClick(By.cssSelector(Utility.getControls("staff_save")));
				isElementNotVisible(By.id("saveStaffModal"));
				int elementsInList = webdriver.findElements(By.xpath(Utility.getControls("staff_list"))).size();
				assertEquals(elementsInList, elementsPresentInList + 1);
			}
		} catch (IOException e) {
			Logger.getLogger(Staff.class).error(e);
		}
	}

	/*
	 * To view the staff and check for the id of the staff on main page and after
	 * clicking on View Button
	 */
	public void viewStaff() {
		try {
			WebElement id_element = webdriver.findElement(By.xpath(Utility.getControls("text_view_first_id")));
			String expectedValue = "Staff " + id_element.getText();

			// check by id
			doClick(id_element);
			String actualValue = webdriver.findElement(By.xpath(Utility.getControls("view_title"))).getText();

			assertEquals(actualValue, expectedValue);

			doClick(webdriver.findElement(By.cssSelector(Utility.getControls("view_back"))));

			// verify by button
			doClick(webdriver.findElement(By.xpath(Utility.getControls("view_button"))));
			actualValue = webdriver.findElement(By.xpath(Utility.getControls("view_title"))).getText();
			assertEquals(actualValue, expectedValue);

			doClick(webdriver.findElement(By.cssSelector(Utility.getControls("view_back"))));
		} catch (IOException e) {
			Logger.getLogger(Staff.class).error(e);
		}
	}

	/*
	 * To Edit the first id on the page by changing the name and branch of the staff
	 */

	public void editStaff() {
		try {
			doClick(webdriver.findElement(By.xpath(Utility.getControls("edit_button"))));

			// To edit name
			String edit_name = Utility.getTestData("TestData_EditStaff", "name", testStaff_Data);
			WebElement el_name = webdriver.findElement(By.xpath(Utility.getControls("staff_create_name")));
			sendKeys(el_name, edit_name);

			// To edit Branch
			Select dropdown = new Select(webdriver.findElement(By.xpath(Utility.getControls("staff_choose_branch"))));
			dropdown.selectByIndex(1);

			doClick(By.cssSelector(Utility.getControls("staff_save")));
			isElementNotVisible(By.id("saveStaffModal"));
		} catch (IOException e) {
			Logger.getLogger(Staff.class).error(e);
		}
	}

	/*
	 * To Delete the first staff on page and confirm if the staff has been deleted
	 */
	public void deleteStaff() {
		try {
			// To check the id being deleted
			int elementsPresentInList = webdriver.findElements(By.xpath(Utility.getControls("staff_list"))).size();
			String id = webdriver.findElement(By.xpath(Utility.getControls("text_view_first_id"))).getText();

			// Click on delete button
			doClick(webdriver.findElement(By.xpath(Utility.getControls("delete_button"))));
			isElementVisible(By.id("deleteStaffConfirmation"));

			// Check the staff id on confirm pop up
			Gson gson = new Gson();
			String delete_text = webdriver.findElement(By.xpath(Utility.getControls("staff_delete_text")))
					.getAttribute("translate-values");
			DeleteObject object = gson.fromJson(delete_text, DeleteObject.class);

			if (id.equals(object.getId())) {
				// Delete the staff selected
				doClick(webdriver.findElement(By.xpath(Utility.getControls("staff_delete_confirm"))));
			}
			isElementNotVisible(By.id("deleteStaffConfirmation"));
			int elementsPresentList = webdriver.findElements(By.xpath(Utility.getControls("staff_list"))).size();
			assertEquals(elementsPresentInList - 1, elementsPresentList);
		} catch (IOException e) {
			Logger.getLogger(Staff.class).error(e);
		}
	}

	/*
	 * To Check the Error message corresponding to the staff name is mandatory
	 */
	public void addStaff_Mandatory() {
		try {
			String minName = Utility.getTestData("TestData_minStaff", "name", testStaff_Data);
			String Expected_mes = Utility.getMessages("staff_create_name_mandatory");
			doClick(webdriver.findElement(By.xpath(Utility.getControls("staff_create_new"))));
			sendKeys(webdriver.findElement(By.xpath(Utility.getControls("staff_create_name"))), minName);
			String message_min_length = webdriver.findElement(By.xpath(Utility.getControls("staff_mandatory_name")))
					.getText();
			doClick(webdriver.findElement(By.xpath(Utility.getControls("button_cancel"))));
			assertEquals(message_min_length, Expected_mes);
		} catch (IOException e) {
			Logger.getLogger(Staff.class).error(e);
		}
	}

	/*
	 * To check the Error message corresponding to staff max length characters limit
	 */
	public void addStaff_maxLength() {
		try {
			String maxName = Utility.getTestData("TestData_maxStaff", "name", testStaff_Data);

			String Expected_mes = Utility.getMessages("staff_create_name_max");
			isElementNotVisible(By.id("saveStaffModal"));
			doClick(webdriver.findElement(By.xpath(Utility.getControls("staff_create_new"))));
			isElementVisible(By.id("saveStaffModal"));
			sendKeys(webdriver.findElement(By.xpath(Utility.getControls("staff_create_name"))), maxName);

			String message_max_length = webdriver.findElement(By.xpath(Utility.getControls("maxLength_name")))
					.getText();

			doClick(webdriver.findElement(By.xpath(Utility.getControls("button_cancel"))));
			isElementNotVisible(By.id("saveStaffModal"));
			assertEquals(Expected_mes, message_max_length);
		} catch (IOException e) {
			Logger.getLogger(Staff.class).error(e);
		}

	}

	/*
	 * To check the Error message corresponding to the pattern change
	 */
	public void addStaff_Pattern() {
		try {
			String pattern = Utility.getTestData("TestData_PatternStaff", "name", testStaff_Data);
			String Expected_mes = Utility.getMessages("staff_create_name_pattern");
			doClick(webdriver.findElement(By.xpath(Utility.getControls("staff_create_new"))));
			isElementVisible(By.id("saveStaffModal"));
			sendKeys(webdriver.findElement(By.xpath(Utility.getControls("staff_create_name"))), pattern);
			String message_pattern = webdriver.findElement(By.xpath(Utility.getControls("pattern_name"))).getText();
			doClick(webdriver.findElement(By.xpath(Utility.getControls("button_cancel"))));
			isElementNotVisible(By.id("saveStaffModal"));
			assertEquals(message_pattern, Expected_mes);
		} catch (IOException e) {
			Logger.getLogger(Staff.class).error(e);
		}
	}

}
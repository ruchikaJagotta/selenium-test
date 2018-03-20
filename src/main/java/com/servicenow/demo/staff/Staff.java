package com.servicenow.demo.staff;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.google.gson.Gson;
import com.servicenow.demo.core.DeleteObject;
import com.servicenow.demo.core.Utility;
import com.servicenow.demo.login.Login;

public class Staff extends Login {

	public Staff() {
		super();
	}

	public static void main(String[] args) {
		Staff staff = new Staff();
		try {
			staff.loginTest();
			staff.clickOnStaff();
			staff.addStaff();
			staff.viewStaff();
			staff.editStaff();
			staff.deleteStaff();
			staff.addStaff_Mandatory();
			staff.addStaff_maxLength();
			staff.addStaff_Pattern();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void clickOnStaff() {
		try {
			webdriver.findElement(By.xpath(Utility.getControls("Entities_Link"))).click();
			webdriver.findElement(By.xpath(Utility.getControls("Staff_Link"))).click();
		} catch (Exception e) {
			Logger.getLogger(Staff.class).error(e);
		}

	}

	public void addStaff() throws IOException {

		List<String> list = Utility.getTestDataAdd("TestData_AddStaff", "name", testStaff_Data);

		for (int temp = 0; temp < list.size(); temp++) {
			int elementsPresentInList = webdriver.findElements(By.xpath(Utility.getControls("staff_list"))).size();
			doClick(webdriver.findElement(By.xpath(Utility.getControls("staff_create_new"))));
			sendKeys(webdriver.findElement(By.xpath(Utility.getControls("staff_create_name"))), list.get(temp));
			Select dropdown = new Select(webdriver.findElement(By.xpath(Utility.getControls("staff_choose_branch"))));
			dropdown.selectByIndex(temp + 1);

			// webdriver.findElement(By.name("editForm")).submit();
			doClick(By.cssSelector(Utility.getControls("staff_save")));
			isElementNotVisible(By.id("saveStaffModal"));
			int elementsInList = webdriver.findElements(By.xpath(Utility.getControls("staff_list"))).size();
			isEquals(elementsInList, elementsPresentInList + 1);
		}

	}

	public void viewStaff() throws IOException {

		WebElement id_element = webdriver.findElement(By.xpath(Utility.getControls("staff_view_first_id")));
		String expectedValue = "Staff " + id_element.getText();

		// check by id
		doClick(id_element);
		String actualValue = webdriver.findElement(By.xpath(Utility.getControls("staff_view_title"))).getText();

		isEquals(actualValue, expectedValue);

		doClick(webdriver.findElement(By.cssSelector(Utility.getControls("staff_view_back"))));

		// verify by button
		doClick(webdriver.findElement(By.xpath(Utility.getControls("staff_view_button"))));
		actualValue = webdriver.findElement(By.xpath(Utility.getControls("staff_view_title"))).getText();
		isEquals(actualValue, expectedValue);

		doClick(webdriver.findElement(By.cssSelector(Utility.getControls("staff_view_back"))));

	}

	public void editStaff() throws IOException {
		doClick(webdriver.findElement(By.xpath(Utility.getControls("staff_edit_button"))));

		// To edit name
		String edit_name = Utility.getTestData("TestData_EditStaff", "name", testStaff_Data);
		WebElement el_name = webdriver.findElement(By.xpath(Utility.getControls("staff_create_name")));
		sendKeys(el_name, edit_name);

		// To edit Branch
		Select dropdown = new Select(webdriver.findElement(By.xpath(Utility.getControls("staff_choose_branch"))));
		dropdown.selectByIndex(1);

		doClick(By.cssSelector(Utility.getControls("staff_save")));
		isElementNotVisible(By.id("saveStaffModal"));

	}

	public void deleteStaff() throws IOException {

		// To check the id being deleted
		int elementsPresentInList = webdriver.findElements(By.xpath(Utility.getControls("staff_list"))).size();
		String id = webdriver.findElement(By.xpath(Utility.getControls("staff_view_first_id"))).getText();

		// Click on delete button
		doClick(webdriver.findElement(By.xpath(Utility.getControls("staff_delete_button"))));
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
		isEquals(elementsPresentInList - 1, elementsPresentList);

	}

	public void addStaff_Mandatory() throws IOException {
		String minName = Utility.getTestData("TestData_minStaff", "name", testStaff_Data);

		String Expected_mes = Utility.getMessages("staff_create_name_mandatory");

		doClick(webdriver.findElement(By.xpath(Utility.getControls("staff_create_new"))));
		sendKeys(webdriver.findElement(By.xpath(Utility.getControls("staff_create_name"))), minName);

		String message_min_length = webdriver.findElement(By.xpath(Utility.getControls("staff_mandatory_name")))
				.getText();

		doClick(webdriver.findElement(By.xpath(Utility.getControls("button_cancel"))));
		isEquals(message_min_length, Expected_mes);
	}

	public void addStaff_maxLength() throws IOException {

		String maxName = Utility.getTestData("TestData_maxStaff", "name", testStaff_Data);

		String Expected_mes = Utility.getMessages("staff_create_name_max");
		isElementNotVisible(By.id("saveStaffModal"));
		doClick(webdriver.findElement(By.xpath(Utility.getControls("staff_create_new"))));
		isElementVisible(By.id("saveStaffModal"));
		sendKeys(webdriver.findElement(By.xpath(Utility.getControls("staff_create_name"))), maxName);

		String message_max_length = webdriver.findElement(By.xpath(Utility.getControls("staff_maxLength_name")))
				.getText();

		doClick(webdriver.findElement(By.xpath(Utility.getControls("button_cancel"))));
		isElementNotVisible(By.id("saveStaffModal"));
		isEquals(Expected_mes, message_max_length);

	}

	public void addStaff_Pattern() throws IOException {

		String pattern = Utility.getTestData("TestData_PatternStaff", "name", testStaff_Data);
		String Expected_mes = Utility.getMessages("staff_create_name_pattern");
		doClick(webdriver.findElement(By.xpath(Utility.getControls("staff_create_new"))));
		isElementVisible(By.id("saveStaffModal"));
		sendKeys(webdriver.findElement(By.xpath(Utility.getControls("staff_create_name"))), pattern);
		String message_pattern = webdriver.findElement(By.xpath(Utility.getControls("staff_pattern_name"))).getText();
		doClick(webdriver.findElement(By.xpath(Utility.getControls("button_cancel"))));
		isElementNotVisible(By.id("saveStaffModal"));
		isEquals(message_pattern, Expected_mes);
	}

}
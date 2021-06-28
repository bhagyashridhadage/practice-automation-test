package reusableLibrary;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
//
//import net.serenitybdd.core.pages.PageObject;
//import net.serenitybdd.core.pages.WebElementFacade;
//import net.thucydides.core.util.EnvironmentVariables;
//import net.thucydides.core.util.SystemEnvironmentVariables;
//import java.io.File;
//
//import java.io.FileInputStream;
//
//import java.io.IOException;
//
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//
//import org.apache.poi.ss.usermodel.Row;
//
//import org.apache.poi.ss.usermodel.Sheet;
//
//import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.datatable.DataTable;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import reusableLibrary.CustomLog.LogInfo;



public class reusableMethods extends baseClass {

	EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
	public int GLOBAL_TIMEOUT = 10;

	public void openPageURL() {

		
		ConfigData = getSheetDataInMap(TESTDATA, CONFIGDATA_SHEET);
		executionEnvironment = ConfigData.get("Execution_Environment");
		String baseURL = ConfigData.get(executionEnvironment);
		environment = ConfigData.get("Environment");
		getDriver().get(baseURL);
		getDriver().manage().window().maximize();
	}

	public String getEnv() {
		ConfigData = getSheetDataInMap(TESTDATA, CONFIGDATA_SHEET);
		environment = ConfigData.get("Environment");
		String Env = ConfigData.get(environment);
		return Env;
	}

	protected void sleepTime(int num) {
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Button
	// Button Click using Serenity
	// Button Click using Selenium
	// Button Click using JS

	// TextBox
	// Enter Text in TextBox Click using Serenity
	// Enter Text in TextBox Click using Selenium
	// Enter Text in TextBox Click using JS

	// DropDown
	// Select DropDown Value using Serenity
	// Select DropDown Value Click using Selenium
	// Select DropDown Value Click using JS

	// RadioButton
	// Select RadioButton Value using Serenity
	// Select RadioButton Value Click using Selenium
	// Select RadioButton Value Click using JS

	// CheckBox
	// Select CheckBox Value using Serenity
	// Select CheckBox Value Click using Selenium
	// Select CheckBox Value Click using JS

	// getText
	// getText Value using Serenity
	// getText Value using Selenium
	// getText Value using JS

	// switchFrame
	// switchFrame using Serenity
	// switchFrame using Selenium
	// switchFrame using JS

	// HorizontalScroll
	// HorizontalScroll using Serenity
	// HorizontalScroll using Selenium
	// HorizontalScroll using JS

	// VerticalScroll
	// VerticalScroll using Serenity
	// VerticalScroll using Selenium
	// VerticalScroll using JS

	// isSelected
	// isSelected using Serenity
	// isSelected using Selenium
	// isSelected using JS

	// isExist
	// isExist using Serenity
	// isExist using Selenium
	// isExist using JS

	// WaitTillElementEnable
	// WaitTillElementEnable using Serenity
	// WaitTillElementEnable using Selenium
	// WaitTillElementEnable using JS

	// WaitTillElementVisible
	// WaitTillElementVisible using Serenity
	// WaitTillElementVisible using Selenium
	// WaitTillElementVisible using JS

	// Wait
	// getAtrributeValue
	// getAtrributeValue using Serenity
	// getAtrributeValue using Selenium
	// getAtrributeValue using JS

	// checkText
	// checkText using Serenity
	// checkText using Selenium
	// checkText using JS

	protected void enterText(WebElementFacade webElement, String text) {
		try {
			webElement.withTimeoutOf(GLOBAL_TIMEOUT, TimeUnit.SECONDS).waitUntilVisible();
			webElement.sendKeys(text);
			LogInfo.WithoutScreenShot("Typed " + text + " into " + webElement);
			System.out.println("\n\n >> Typed " + text + " into " + webElement);
		} catch (Exception e) {
			LogInfo.WithoutScreenShot("Can't type " + text + " into " + webElement + e.getMessage(), "Fail");
		}
	}

	public void clickOnElement(WebElementFacade webElement) {
		try {
			webElement.withTimeoutOf(GLOBAL_TIMEOUT, TimeUnit.SECONDS).waitUntilVisible();
			clickOn(webElement);
			LogInfo.WithoutScreenShot("Element " + webElement + " Clicked");
			System.out.println("\n\n >> Element " + webElement + "Clicked");
		} catch (Exception e) {
			System.out.println("\n\n >> Element " + webElement + " not clickable");
			LogInfo.WithoutScreenShot("Element " + webElement + " is not Clickable" + e.getMessage(), "Fail");
		}
	}

	protected void clickOnJS(WebElementFacade webElement) {

		WebDriver driver = getDriver();
		webElement.withTimeoutOf(GLOBAL_TIMEOUT, TimeUnit.SECONDS).waitUntilVisible();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", webElement);
		LogInfo.WithoutScreenShot("Clicked on Element : " + webElement, "Pass");
		System.out.println("\n\n >> JS Click on Element : " + webElement);
	}

	protected void clickOnWebElementJS(WebElement webElement) {
		WebDriver driver = getDriver();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", webElement);
		LogInfo.WithoutScreenShot("Clicked on Element : " + webElement, "Pass");
		System.out.println("\n\n >> JS Clicked on Element : " + webElement);
	}


	public Map<String, String> getDataTableValuesIntoHashMap(Map<String, String> mapTestData, DataTable DataTable) {
		List<List<String>> rowsOp = DataTable.asLists();
		int size = rowsOp.size();
		for (int j = 1; j < size; j++)
			for (int i = 0; i < rowsOp.get(0).size(); i++) {
				mapTestData.put(rowsOp.get(0).get(i) + j, rowsOp.get(j).get(i));
				if (j == 1)
					mapTestData.put(rowsOp.get(0).get(i), rowsOp.get(j).get(i));
			}
		mapTestData.put("noOfRecords", Integer.toString(size - 1));
		return mapTestData;
	}

}

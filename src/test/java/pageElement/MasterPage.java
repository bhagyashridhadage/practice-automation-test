package pageElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;

import net.serenitybdd.core.pages.WebElementFacade;
//import library.baseClass;
import reusableLibrary.reusableMethods;

public class MasterPage extends reusableMethods {

	public String menuItem;
	@FindBy(xpath = "//a[@data-toggle='dropdown']")
	WebElementFacade dropdown;

	public void selectMenu() {
		clickOn(dropdown);
	}

	public void navigateTo(String navigator) {
		String nav[];
		nav = navigator.split(">");

		clickOnMenu();

		for (int i = 0; i < nav.length; i++) {
			switch (i) {
			case 0:
				clickOnModule(nav[0]);
				break;
			case 1:
				clickOnSubmodule(nav[1]);
				break;
			case 2:
				clickOnChild(nav[2]);
				break;
			}
		}
	}

	private void clickOnMenu() {
		selectMenu();
	}

	private void clickOnModule(String matchtext) {
		WebElement webElement = null;
		webElement = getDriver().findElement(By.xpath("//*[contains(text(),'" + matchtext + "')]"));
		// *[contains(text(),'Master Data')]
		webElement.click();
		System.out.println(webElement + "Element is available");
	}

	private void clickOnSubmodule(String matchtext) {
		WebElement webElement = null;
		webElement = getDriver().findElement(By.xpath("//*[contains(text(),'" + matchtext + "')]"));
		webElement.click();
		System.out.println(webElement + "Element is available");

	}

	private void clickOnChild(String matchtext) {
		WebElement webElement = null;
		webElement = getDriver().findElement(By.xpath("//div/a/span[text()='" + matchtext + "']"));
		// div/a/span[text()='Orders']
		webElement.click();
		System.out.println(webElement + "Element is available");
	}

	
	public void sendTextToField(String fieldlabel, String value) {
		WebElement webElement = null;
		webElement = getDriver().findElement(By.xpath("//label[text()='" + fieldlabel + "']/ancestor::tr//input"));
		//label[text()='Sales Price']/ancestor::tr//input
		webElement.clear();
		webElement.sendKeys(value);
		System.out.println(webElement + "Element is available");
	}
	
	public void validateTextToField(String fieldlabel, String value) {
		WebElement webElement = null;
		webElement = getDriver().findElement(By.xpath("//label[text()='" + fieldlabel + "']/ancestor::tr//a"));
		webElement.getText().contentEquals(value);
		System.out.println(webElement + "Element is available");
	}
	
	
	@FindBy(xpath = "//button[contains(text(),'Action')]")
	WebElementFacade action;

	public void clickOnAction() {
		clickOnElement(action);
		sleepTime(2000);
	}
	
	//a[contains(text(),'Archive')]
	public void selectAction() {
		WebElement webElement = null;
		String action = paraData.get("productOperation");
		webElement = getDriver().findElement(By.xpath("//a[contains(text(),'" + action + "')]"));
		clickOnWebElementJS(webElement);	
	}
	
	
	
}

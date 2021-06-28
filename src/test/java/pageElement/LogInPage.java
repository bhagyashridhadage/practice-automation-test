package pageElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.GetElementText;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.serenitybdd.core.pages.WebElementFacade;
//import library.baseClass;

public class LogInPage extends MasterPage {

	// ********* SignIn flow Starts Here ***********//
	@FindBy(xpath = "//input[@name='username']")
	WebElementFacade userNameInput;

	public void enterUserName(String username) {
		enterText(userNameInput, username);
	}

	@FindBy(xpath = "//input[@name='password']")
	WebElementFacade passwordInput;

	public void enterPassword(String password) {
		enterText(passwordInput, password);
	}

	@FindBy(xpath = "//input[@type='submit']")
	WebElementFacade signIn;

	public void clickSignIn() {
		clickOnElement(signIn);
	}
	// ********* SignIn flow Ends Here ***********//

	// ********* Error Message Check flow Starts Here ***********//
	@FindBy(xpath = "")
	WebElementFacade userNameOrPasswordError;
	public void userNameErr() {
		// getTextofElement
	}

	@FindBy(xpath = "")
	WebElementFacade userNameError;
	public void userNameError() {
		// getTextofElement
	}

	@FindBy(xpath = "")
	WebElementFacade passwordError;
	public void passwordError() {
		// getTextofElement
	}

	// *[@class='alert alert-danger']
	@FindBy(xpath = "//span[@id='Password-error']")
	WebElementFacade passwordErr;
	public void passwordErr() {
		passwordErr.isDisplayed();
	}

	// ********* Error Message Check flow ends Here ***********//

	// ********* LogOut flow ends Here ***********//

	
	@FindBy(xpath = "//a[@href='/parabank/logout.htm']")
	
	WebElementFacade signOut;
	public void logout() {
			clickOnElement(signOut);
		
	}
	
	// ********* LogOut flow ends Here ***********//

	// ********* Reusable Methods Starts Here ***********//
	protected void clickSignInWithoutIsClickableChkAndValidate() {

	}

	protected void chkResetPasswordLink() {

	}

	protected void chkResetPasswordPage() {

	}

	protected void chkSignInDisable() {

	}

	protected void validateErrorMessage(String uiErrMessage, String scenario) {

	}

	protected void validatePageTitle(String uiPageTitle, String pageName) {

	}

	protected String getWrongAttemptErr() {

		return null;
	}

	protected String getPasswordErr() {

		return null;
	}

	protected String getUserNameErr() {

		return null;
	}

	protected void validateLoginSuccess() {
		paraData.put("isLoginSuccess", "Y");
	}

	// ********* Reusable Methods ends Here ***********//

}
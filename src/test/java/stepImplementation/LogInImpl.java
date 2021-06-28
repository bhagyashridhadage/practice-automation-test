package stepImplementation;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.datatable.DataTable;
import net.thucydides.core.annotations.Step;
import pageElement.LogInPage;

public class LogInImpl extends LogInPage {

	@Step
	public void openUrl() {
		openPageURL();
	}

	@Step
	public Map<String, String> logInWithUser(Map<String, String> paraData, String testCaseID, DataTable dataTable) {
		boolean shouldSignInClick = true;
		paraData = getDataTableValuesIntoHashMap(paraData, dataTable);
		paraData.put("testCaseID", testCaseID);
		TestData = getTestData(TESTDATA, getModuleSheetName(paraData.get("moduleName")), testCaseID);
		paraData.put("userName", TestData.get("userName"));
		paraData.put("password", TestData.get("password"));
		openUrl();
		String scenario = paraData.get("scenario").toLowerCase();

		switch (scenario) {
		case "valid_credentials":
		case "incorrect_username":
		case "incorrect_password":
			enterUserName(paraData.get("userName"));
			enterPassword(paraData.get("password"));
			break;
		
		case "blank_username_and_password":
			break;
		case "blank_username":
			enterPassword(paraData.get("password"));
			break;
		case "blank_password":
			enterUserName(paraData.get("userName"));
			break;
		
		default:
			enterUserName(paraData.get("userName"));
			enterPassword(paraData.get("password"));
		}
		if (shouldSignInClick)
			clickSignIn();
		return paraData;
	}

	@Step
	protected void validateScenario(Map<String, String> paraData, String result, String outcome) {
		String scenario = paraData.get("scenario").toLowerCase();
		HashMap<String, String> errorData = getTestData(TESTDATA, "errorMessage", scenario);
		HashMap<String, String> titleData = new HashMap<String, String>();
		String uiErrMessage = "";
		String pageTitle = "";
		String pageName = "signIn";
		boolean isErr = true;
		pageTitle = getTitle();
		switch (scenario) {
		case "valid_credentials":
			logout();
			validateTitle("accountPage");
			validateLoginSuccess();
			isErr = false;
			paraData.put("isLoginSuccess","Y");
			break;
		case "blank_username":
		case "incorrect_username":
			uiErrMessage = getUserNameErr();
			break;
		case "blank_password":
		case "incorrect_password":
			uiErrMessage = getPasswordErr();
			break;
		case "blank_username_and_password":
			uiErrMessage = getUserNameErr();
			scenario = "blank_password";
			validateErrorMessage(uiErrMessage, scenario);
			scenario = "blank_username";
			uiErrMessage = getPasswordErr();
			break;
		case "double_login":
			chkSignInDisable();
			break;
		case "reset_password":
			chkResetPasswordPage();
			validateTitle("resetPassword");
			break;
		case "wrong_attempts":
			uiErrMessage = getWrongAttemptErr();
			break;
		default:

		}
		// To validate specific error based on scenario
		validateErrorMessage(uiErrMessage, scenario);
	}

	private void validateTitle(String string) {
		// TODO Auto-generated method stub
		
	}

	private void atteptWrongCredentialsLogin(Map<String, String> paraData) {
		for (int i = 0; i < Integer.parseInt(paraData.get("noOfAttempts")); i++) {
			enterUserName(paraData.get("userName"));
			enterPassword(paraData.get("password"));
			clickSignIn();
		}
	}

}

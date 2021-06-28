package stepdefinitions;

import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import stepImplementation.LogInImpl;

public class LogInSteps extends LogInImpl {

	@Given("{string} is valid Modulr user")
	public void is_valid_Modulr_user(String customerType) {
		paraData.put("customerType", customerType);
	}

	@When("user logins with credential from {string}")
	public void user_logins_with_credential_from(String testCaseID,DataTable  dataTable) {
		paraData.put("moduleName", "Login");
		paraData =logInWithUser(paraData,testCaseID,dataTable);	
	}


	@Then("login is {string} with {string}")
	public void login_is_with(String result, String outcome) {
		validateScenario(paraData,result,outcome);
		logout();
	}
	
	//ParaBank
	@Given("I am valid PARA Bank user")
	public void i_am_valid_PARA_Bank_user() {
		paraData.put("moduleName", "Login");

	}

	@When("I logins with credential from {string}")
	public void i_logins_with_credential_from(String testCaseID,DataTable  dataTable) {
		paraData.put("moduleName", "Login");
		paraData =logInWithUser(paraData,testCaseID,dataTable);	
	 
	}

	@Then("login is successful")
	public void login_is_successful() {
	
	}

	
}
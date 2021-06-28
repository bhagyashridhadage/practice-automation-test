package runner;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(plugin = { "pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		"json:target/cucumber-report.json" },
		features = "src/test/resources/features/Login.feature",
		tags = "@dryRun", 
		glue = "", 
		monochrome = true)

public class LoginTestRunner {

	@AfterClass
	public static void tearDown() {
		String[] args = null;
		System.out.println("tearing down");
	}

}
package reusableLibrary;

import org.openqa.selenium.WebDriver;

import net.thucydides.core.webdriver.DriverSource;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import net.thucydides.core.webdriver.WebDriverFacade;

public class driverName implements DriverSource {

	baseClass baseClass = new baseClass();

	public WebDriver driver = null;

	@Override
	public WebDriver newDriver() {

		try {
			driver = baseClass.loadWebDriver();
			return driver;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean takesScreenshots() {
		// TODO Auto-generated method stub
		return false;
	}

}
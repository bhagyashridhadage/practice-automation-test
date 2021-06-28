
package reusableLibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import cucumber.api.Scenario;
import net.thucydides.core.pages.PageObject;


public class baseClass extends PageObject {
	public final String TESTDATA = "./src/test/resources/testdata/paraData.xlsx";
	public final String MESSAGEDATA = "./src/test/resources/testdata/erroMessage.xlsx";

	public final String CONFIGDATA_SHEET = "ConfigData_Sheet";
	public final String LOGIN_SHEET = "Login_Sheet";
	public final int IMPLICITLY_WAIT = 30;

	public WebDriver driver = null;
	public static Scenario scenario = null;
	protected static long randomNumber = 0;
	protected static String executionEnvironment = null;
	protected static String environment = null;
	protected static String isEnvironmentSelected = "N";
	protected static String testCaseID = null;
	protected static Logger Log = null;
	protected static Map<String,String> paraData = new HashMap<String,String>();
	protected static String accountsContactEmail;

	private FileInputStream fis = null;
	private ArrayList<String> webDriverDetails = null;
	private XSSFSheet excelWorkSheet = null;
	private XSSFWorkbook excelWorkBook = null;
	protected HashMap<String, String> allSheetData = null;
	protected static HashMap<String, String> ConfigData = null;
	protected static HashMap<String, String> TestData = null;

	protected static String accountName = null;

	protected WebDriver loadWebDriver() {
		try {
			Log = Logger.getLogger(baseClass.class);
			DOMConfigurator.configure("./src/test/resources/other/log4j.xml");
		} catch (FactoryConfigurationError e) {
			Log.error("*********** FactoryConfigurationError Logger Failed");
			Log.error("Exception is: ", e);
		} catch (Exception e) {
			Log.error("*********** Logger Failed");
			Log.error("Exception is: ", e);
		}

		Log.info("-------------------------------------------------------------");
		Log.info("					INITIALIZATION");
		Log.info("-------------------------------------------------------------");

		try {
			ConfigData = getSheetDataInMap(TESTDATA, CONFIGDATA_SHEET);
			Log.info("Config Data Read Successfully");
		} catch (Exception e) {
			Log.error("*********** Reading Config Data Failed ");
			Log.error("Exception is: ", e);
		}

		executionEnvironment = ConfigData.get("Execution_Environment");
		webDriverDetails = getWebDriverDetails();

		String webDriverName = null;
		String webDriverProperty = null;
		String webDriverPath = null;

		try {
			if (webDriverDetails != null) {
				Iterator<String> itr = webDriverDetails.iterator();
				while (itr.hasNext()) {
					webDriverName = itr.next();
					webDriverProperty = itr.next();
					webDriverPath = itr.next();
				}
			}
		} catch (Exception e) {
			Log.error("*********** Could not read WebDriver Details");
			Log.error("Exception is: ", e);
		}

		System.setProperty(webDriverProperty, webDriverPath);

		try {
			switch (webDriverName) {
			case "chrome":
				if (driver == null) {
					driver = new ChromeDriver();
					break;
				}
			case "firefox":
				if (driver == null)
					driver = new FirefoxDriver();
				break;

			case "IE":
				if (driver == null)// internet_explorer
					driver = new InternetExplorerDriver();
				break;
			}
//return driver;
		} catch (Exception e) {
			Log.error("*********** Loading Driver failed");
			Log.error("Exception is: ", e);
		}
		return driver;
	}

	protected XSSFWorkbook getExcelWorkBook(String workBookName) {
		File sourceFile = new File(workBookName);

		try {
			fis = new FileInputStream(sourceFile);
		} catch (FileNotFoundException e) {
			Log.error("*********** Could not find file");
			Log.error("Exception is: ", e);
		}

		try {
			excelWorkBook = new XSSFWorkbook(fis);
		} catch (IOException e) {
			Log.error("*********** Error while accessing file");
			Log.error("Exception is: ", e);
		}

		return excelWorkBook;
	}

	private ArrayList<String> getWebDriverDetails() {
		String currentBrowserName = getBrowserName();
		ArrayList<String> driverDetails = new ArrayList<String>();
		String sheetName = CONFIGDATA_SHEET;

		int totalRowCount = getRowCount(TESTDATA, sheetName);
		DataFormatter dataFormatter = new DataFormatter();
		String cellValue = null;

		excelWorkSheet = excelWorkBook.getSheet(sheetName);

		try {
			for (int i = 0; i < totalRowCount; i++) {
				cellValue = dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(0));
				if (cellValue != null && cellValue.length() > 0) {
					if (cellValue.equalsIgnoreCase(currentBrowserName)) {
						driverDetails.add(currentBrowserName);
						driverDetails.add(dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(1)).trim());
						driverDetails.add(dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(2)).trim());
						break;
					}
				}
			}
		} catch (Exception e) {
			Log.error("*********** Excel Cell Data Missing");
			Log.error("Exception is: ", e);
		}

		return driverDetails;
	}

	protected HashMap<String, String> getTestData(String excelFileName, String excelSheetName, String testCaseID) {
		int totalColumnCount = getColumnCount(excelFileName, excelSheetName);
		int testCaseIDRow = getTestCaseIDRow(excelFileName, excelSheetName, testCaseID);

		String cellValue = null;
		String key = null;
		String value = null;
		DataFormatter dataFormatter = new DataFormatter();

		excelWorkSheet = excelWorkBook.getSheet(excelSheetName);
		HashMap<String, String> TestData = new HashMap<String, String>();

		try {
			for (int i = 0; i < totalColumnCount; i++) {
				try {
					cellValue = dataFormatter.formatCellValue(excelWorkSheet.getRow(testCaseIDRow).getCell(1));
				} catch (Exception e) {
					Log.error("Test Case ID is Missing");
					Log.error("Exception is: ", e);
				}

				try {
					if (cellValue != null && cellValue.length() > 0) {
						key = dataFormatter.formatCellValue(excelWorkSheet.getRow(0).getCell(i)).trim();
						value = dataFormatter.formatCellValue(excelWorkSheet.getRow(testCaseIDRow).getCell(i)).trim();
						TestData.put(key, value);
					}
				} catch (Exception e) {
					System.out.println(" Data missing from DX reports");
					Log.error("Exception is: ", e);
				}
			}
		} catch (Exception e) {
			Log.error("Excel cell data is Missing");
			Log.error("Exception is: ", e);
		}

		return TestData;
	}

	protected HashMap<String, String> getSheetDataInMap(String excelFileName, String excelSheetName) {
		int totalRowCount = getRowCount(excelFileName, excelSheetName);
		String cellValue = null;
		String key = null;
		String value = null;
		DataFormatter dataFormatter = new DataFormatter();

		excelWorkSheet = excelWorkBook.getSheet(excelSheetName);
		HashMap<String, String> mapSheetData = new HashMap<String, String>();

		try {
			for (int i = 0; i < totalRowCount; i++) {
				cellValue = dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(0));

				if (cellValue != null && cellValue.length() > 0) {
					key = dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(0));
					value = dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(1));
					mapSheetData.put(key, value);
				}
			}
		} catch (Exception e) {
			Log.error("*********** Excel Cell Data Missing");
			Log.error("Exception is: ", e);
		}

		return mapSheetData;
	}

	protected String getBrowserName() {
		String sheetName = CONFIGDATA_SHEET;
		String currentBrowserName = null;
		String cellValue = null;
		DataFormatter dataFormatter = new DataFormatter();

		int totalRowCount = getRowCount(TESTDATA, sheetName);

		excelWorkSheet = excelWorkBook.getSheet(sheetName);

		try {
			for (int i = 0; i < totalRowCount; i++) {
				cellValue = dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(0)).trim();
				if (cellValue != null && cellValue.length() > 0) {
					if (cellValue.equalsIgnoreCase("BrowserForCurrentTest")) {
						currentBrowserName = dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(1)).trim();
						break;
					}
				}
			}
		} catch (Exception e) {
			Log.error("*********** Excel Cell Data Missing");
			Log.error("Exception is: ", e);
		}

		return currentBrowserName;
	}
	
	protected String getConfigKeyValue(String key) {
		String sheetName = CONFIGDATA_SHEET;
		String excelkey = null;
		String cellValue = null;
		DataFormatter dataFormatter = new DataFormatter();

		int totalRowCount = getRowCount(TESTDATA, sheetName);

		excelWorkSheet = excelWorkBook.getSheet(sheetName);

		try {
			for (int i = 0; i < totalRowCount; i++) {
				cellValue = dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(0)).trim();
				if (cellValue != null && cellValue.length() > 0) {
					if (cellValue.equalsIgnoreCase(key)) {
						excelkey = dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(1)).trim();
						break;
					}
				}
			}
		} catch (Exception e) {
			Log.error("*********** Excel Cell Data Missing");
			Log.error("Exception is: ", e);
		}
		System.out.println("from Mail ID is:" + key);
		return excelkey;
	}

	private int getRowCount(String workBookName, String sheetName) {
		int rowCount = 0;

		try {
			if (excelWorkBook == null) {
				excelWorkBook = getExcelWorkBook(workBookName);
			}
		} catch (Exception e) {
			Log.error("*********** Creating WorkBook instance failed");
			Log.error("Exception is: ", e);
		}

		try {
			rowCount = (excelWorkBook.getSheet(sheetName).getLastRowNum()) + 1;

		} catch (Exception e) {
			Log.error("*********** Get sheet row count failed");
			Log.error("Exception is: ", e);
		}

		return rowCount;
	}

	private int getColumnCount(String workBookName, String sheetName) {
		int colCount = 0;

		try {
			if (excelWorkBook == null) {
				excelWorkBook = getExcelWorkBook(workBookName);
			}
		} catch (Exception e) {
			Log.error("*********** Creating WorkBook instance failed");
			Log.error("Exception is: ", e);
		}

		try {
			colCount = (excelWorkBook.getSheet(sheetName).getRow(0).getLastCellNum());

		} catch (Exception e) {
			Log.error("*********** Get sheet row count failed");
			Log.error("Exception is: ", e);
		}

		return colCount;
	}

	protected HashMap<String, String> getAllSheetData(String workBookName, String sheetName) {
		int totalRowCount = getRowCount(workBookName, sheetName);
		String cellValue = null;
		String key = null;
		String value = null;
		DataFormatter dataFormatter = new DataFormatter();

		excelWorkSheet = excelWorkBook.getSheet(sheetName);
		allSheetData = new HashMap<String, String>();

		try {
			for (int i = 0; i < totalRowCount; i++) {
				cellValue = dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(0));

				if (cellValue != null && cellValue.length() > 0) {
					key = dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(0));
					value = dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(1));
					allSheetData.put(key, value);
				}
			}
		} catch (Exception e) {
			Log.error("*********** Excel Cell Data Missing");
			Log.error("Exception is: ", e);
		}

		return allSheetData;
	}

	protected void openLoginPage(String moduleName) {
		try {
			TestData = getTestData(TESTDATA, getModuleSheetName(moduleName), testCaseID);
			Log.info("Test Data Read Successfully");
		} catch (Exception e) {
			Log.error("*********** Reading Test Data Failed ");
			Log.error("Exception is: ", e);
		}

		Log.info("-------------------------------------------------------------");
		Log.info("Test Case " + testCaseID + " is STARTED");
		Log.info("-------------------------------------------------------------");
	}

	protected String getModuleSheetName(String moduleName) {
		String moduleSheetName = null;

		try {
			switch (moduleName) {
			case "Login":
				moduleSheetName = LOGIN_SHEET;
				break;
			default:
				moduleSheetName = CONFIGDATA_SHEET;
			}
		} catch (Exception e) {
			Log.error("*********** Loading Module Name Sheet failed");
			Log.error("Exception is: ", e);
		}

		return moduleSheetName;
	}

	private int getTestCaseIDRow(String excelFileName, String excelSheetName, String testCaseID) {
		int testCaseIDRow = 0;
		int totalRowCount = getRowCount(excelFileName, excelSheetName);
		String cellValue = "";
		DataFormatter dataFormatter = new DataFormatter();

		excelWorkSheet = excelWorkBook.getSheet(excelSheetName);

		try {
			for (int i = 0; i < totalRowCount; i++) {
				cellValue = dataFormatter.formatCellValue(excelWorkSheet.getRow(i).getCell(1));

				if (cellValue != null && cellValue.length() > 0) {
					if (cellValue.trim().equalsIgnoreCase(testCaseID)) {
						testCaseIDRow = i;
						break;
					}
				}
			}
		} catch (Exception e) {
			Log.error("*********** Excel Cell Data Missing");
			Log.error("Exception is: ", e);
		}

		if (testCaseIDRow == 0) {
			Log.error("*********** Test Case Id not Matching");
		}

		return testCaseIDRow;
	}

	protected void closeBrowser() {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				Log.error("*********** Could not close FileInputStream");
				Log.error("Exception is: ", e);
			}
		}

		if (excelWorkBook != null) {
			try {
				// fis.close();
				excelWorkBook.close();
			} catch (IOException e) {
				Log.error("*********** Could not close Excel WorkBook");
				Log.error("Exception is: ", e);
			}
		}

		if (driver != null) {
			try {
				
				driver.quit();
				driver = null;
			} catch (Exception e) {
				Log.error("*********** Could not quit WebDriver Object");
				Log.error("Exception is: ", e);
			}
		}
	}

}

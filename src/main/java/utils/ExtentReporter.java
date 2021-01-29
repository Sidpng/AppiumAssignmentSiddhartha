package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class ExtentReporter implements ReporterInterface {
	
	//custom declaration to store the reports in the defined project path with the date of execution of the test case
	private static final String REPORTS_PATH = System.getProperty("user.dir") + File.separator + "Reports"
			+ File.separator + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	//Other Elements&Variables Declaration
	private static final String REPORT_SCREENSHOTS_PATH = REPORTS_PATH + File.separator + "screenshots";
	private static ExtentReports extent;
	private static ExtentHtmlReporter htmlReporter;
	private int screenshotNumber = 1;
	private String testName;
	public ExtentTest test;

	/*
	 * Description: Constructor for initializing the class variables and the parent class also gives the test a Name for further unique identification
	 * Created By: Siddhartha Upadhyay 
	 * Attribute: testName: name that will be given to the test
	 * 
	 */
	
	public ExtentReporter(String testName) {
		this.testName = testName;
		if (extent == null) {
			extent = new ExtentReports();
		}
		if (htmlReporter == null) {
			htmlReporter = new ExtentHtmlReporter(REPORTS_PATH + File.separator + "extentReport.html");
			extent.attachReporter(htmlReporter);
		}
		test = extent.createTest(testName);
	}

	/*
	 * Description: Applying Method Overriding on the method in the interface
	 * Created By: Siddhartha Upadhyay 
	 * Attribute: message: The message that has been passed to the log file
	 * 
	 */
	
	@Override
	public void log(String message) {
		test.log(Status.INFO, message);
		System.out.println(message);
	}

	/*
	 * Description: Applying Method Overriding on the method in the interface
	 * Created By: Siddhartha Upadhyay 
	 * Attribute: message: The message that has been passed to the log file
	 * 
	 */
	
	@Override
	public void pass(String message) {
		captureScreen(AmazonDriver.driver);
		test.pass(message);
	}

	/*
	 * Description: Applying Method Overriding on the method in the interface
	 * Created By: Siddhartha Upadhyay 
	 * Attribute: message: The message that has been passed to the log file
	 * 
	 */
	
	@Override
	public void fail(String message) {
		try {
			test.fail(message).addScreenCaptureFromPath(captureScreen(AmazonDriver.driver));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assert.fail(message);
	}

	@Override
	public void fail(Throwable t) {
		test.fail(t);
		Assert.fail(t.getLocalizedMessage(), t);
	}

	@Override
	public void error(String message) {
		test.error(message);
		System.err.println(message);
	}

	@Override
	public void warn(String message) {
		test.warning(message);
		System.out.println("Warn: " + message);
	}

	@Override
	public void flush() {
		extent.flush();
	}

	/*
	 * Description: Reusable function to capture Present Screen on the UI whenever called
	 * Created By: Siddhartha Upadhyay
	 * Attribute: driver- WebDriver element which will capture the screen and store the image
	 * 			  
	 */
	
	public String captureScreen(AppiumDriver<MobileElement> driver) {
		try {
			File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenShotDestination = REPORT_SCREENSHOTS_PATH + File.separator + testName
					+ String.format("_%02d", screenshotNumber++) + ".jpeg";
			File destination = new File(screenShotDestination);
			FileUtils.copyFile(sourceFile, destination);
			return screenShotDestination;
		} catch (IOException e) {
			fail(e);
		}
		return null;
	}

}

package tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.AmazonHomePage;
import pages.ProductsPage;
import pages.ShoppingCartPage;
import pages.SignInPromptPage;
import utils.AmazonDriver;
import utils.ExtentReporter;
import utils.ReporterInterface;

public class SkipSignIn {
	//Element declaration
	protected ReporterInterface reporter;

	/*
	 * Description: Before test method to initialize the drivers and reporting file
	 * Created By: Siddhartha Upadhyay 
	 */
	
	@BeforeTest
	public void setupTest() {
		reporter = new ExtentReporter("AmazonAutomation");
		AmazonDriver.driverInit(reporter);
	}

	/*
	 * Description: Test method for executing the test scenario on the Amazon Mobile Application
	 * Created By: Siddhartha Upadhyay 
	 */
	
	@Test
	public void appTest() {
		new SignInPromptPage(reporter).skipLogin();
		new AmazonHomePage(reporter, "AmazonAutomation.properties").searchProduct();
		new ProductsPage(reporter).addToCart();
		ShoppingCartPage shoppingCart = new ShoppingCartPage(reporter);
		shoppingCart.gotoCart();
		shoppingCart.verifyCartItem();
	}
	
	/*
	 * Description: After Method to tear down the driver and check execution status
	 * Created By: Siddhartha Upadhyay 
	 * Attribute: result - Class object of ITestResult to fetch the overall execution status
	 */

	@AfterMethod
	public void afterMethod(ITestResult result) {
		switch (result.getStatus()) {
		case ITestResult.FAILURE:
			reporter.fail("Failed");
			break;
		case ITestResult.SUCCESS:
			reporter.pass("Passed");
			break;
		}
		reporter.flush();
		AmazonDriver.driverShutdown(reporter);
	}

}

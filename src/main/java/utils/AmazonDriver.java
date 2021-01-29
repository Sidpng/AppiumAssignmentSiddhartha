package utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.touch.offset.PointOption;

public class AmazonDriver {

	//Elements and Variable declaration
	private static final String CART_BUTTON_ID = "com.amazon.mShop.android.shopping:id/action_bar_cart_count";
	private static final String AMAZON_LOGO_ID = "com.amazon.mShop.android.shopping:id/action_bar_home_logo";
	private static final String PRODUCT_DESCRIPTION_ID = "com.amazon.mShop.android.shopping:id/list_product_description_layout";
	private static final String PRODUCT_TITLE_ID = "com.amazon.mShop.android.shopping:id/item_title";
	private static final String PRODUCT_PRICE_ID = "com.amazon.mShop.android.shopping:id/rs_results_price";
	private static final int SHORT_SLEEP_TIME = 150;
	private static final int MAX_WAIT_TIMEOUT = 45;
	protected static AppiumDriver<MobileElement> driver;
	protected static DesiredCapabilities cap;
	protected ReporterInterface reporter;
	protected PropertyReader propertyReader;

	/*
	 * Description: Constructor for initializing the class variables and the parent class, default one initializes the capabilities properties which will be common for all Test Runs
	 * Created By: Siddhartha Upadhyay 
	 */
	
	public AmazonDriver(ReporterInterface reporter) {
		this.reporter = reporter;
		this.propertyReader = new PropertyReader();
	}
	
	/*
	 * Description: Constructor for initializing the class variables and the parent class, Parameterized with the custom property file which will be linked with its respective test cases
	 * Created By: Siddhartha Upadhyay 
	 */
	
	public AmazonDriver(ReporterInterface reporter, String propertyFilePath) {
		this.reporter = reporter;
		this.propertyReader = new PropertyReader(propertyFilePath);
	}

	/*
	 * Description: Reusable function initialize the driver
	 * Created By: Siddhartha Upadhyay 
	 */
	
	public static void driverInit(ReporterInterface reporter) {
		if (driver == null) {
			try {
				PropertyReader defaultProperties = new PropertyReader();
				cap = new DesiredCapabilities();
				reporter.log("Initializing driver...");
				cap.setCapability("deviceName", defaultProperties.getProperty("deviceName"));
				cap.setCapability("udid", defaultProperties.getProperty("udid"));
				cap.setCapability("platformName", defaultProperties.getProperty("platformName"));
				cap.setCapability("platformVersion", defaultProperties.getProperty("platformVersion"));
				cap.setCapability("appPackage", defaultProperties.getProperty("appPackage"));
				cap.setCapability("appActivity", defaultProperties.getProperty("appActivity"));
				URL url = new URL(defaultProperties.getProperty("url"));
				driver = new AndroidDriver<MobileElement>(url, cap);
				driver.resetApp();
			} catch (Exception e) {
				reporter.error(e.getMessage());
				reporter.fail(e);
			}
		} else {
			reporter.log("Driver already initialized, skipping...");
		}
	}

	/*
	 * Description: Reusable function shutdown the driver
	 * Created By: Siddhartha Upadhyay
	 */
	
	public static void driverShutdown(ReporterInterface reporter) {
		try {
			driver.resetApp();
			driver.quit();
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
	}
	
	/*
	 * Description: Reusable function to check if the element is visible or not
	 * Created By: Siddhartha Upadhyay
	 * Attribute: strategy- Element type String passed is an id or xpath
	 * 			  identifier- unique element identifier
	 */
	
	public boolean isElementVisible(String identifier, String strategy) {
		try {
			if (driver.findElements(strategy, identifier).isEmpty()) {
				return false;
			}
			return driver.findElement(strategy, identifier).isDisplayed();
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
		return false;
	}
	
	/*
	 * Description: Reusable function to get the list of Webelements 
	 * Created By: Siddhartha Upadhyay
	 * Attribute: partialText- webelement to compare with the extracted elements 
	 * 			  className- unique element identifier to get those elements
	 */
	
	public List<MobileElement> findElementsByText(String partialText, String className) {
		List<MobileElement> elements = new ArrayList<MobileElement>();
		try {
			Thread.sleep(SHORT_SLEEP_TIME);
			List<MobileElement> elementsByClassName = driver.findElements(By.className(className));
			for (MobileElement el : elementsByClassName) {
				if (el.getText().contains(partialText)) {
					elements.add(el);
				}
			}
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
		return elements;
	}

	/*
	 * Description: Reusable function to get the text present on the webelement
	 * Created By: Siddhartha Upadhyay
	 * Attribute: strategy- Element type String passed is an id or xpath
	 * 			  identifier- unique element identifier
	 */
	
	public String getText(String identifier, String strategy) {
		try {
			return driver.findElement(strategy, identifier).getText();
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
			return null;
		}
	}

	/*
	 * Description: Reusable function to wait for an element to be clickable
	 * Created By: Siddhartha Upadhyay
	 * Attribute: strategy- Element type String passed is an id or xpath
	 * 			  identifier- unique element identifier
	 */
	
	public void waitForElement(String identifier, String strategy) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, MAX_WAIT_TIMEOUT);
			switch (strategy) {
			case "id":
				wait.until(ExpectedConditions.elementToBeClickable(By.id(identifier)));
				break;
			case "xpath":
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(identifier)));
				break;
			default:
				reporter.fail("Invalid strategy: " + strategy);
			}
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
	}

	/*
	 * Description: Reusable function to click on an element
	 * Created By: Siddhartha Upadhyay 
	 * Attribute: strategy- Element type String passed is an id or xpath
	 * 			  identifier- unique element identifier stored in respective webelements for each page
	 * 			  
	 */
	
	public void click(String identifier, String strategy) {
		try {
			driver.findElement(strategy, identifier).click();
			reporter.log("Clicked: " + identifier);
			((ExtentReporter) reporter).captureScreen(driver);
			Thread.sleep(SHORT_SLEEP_TIME);
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
	}

	/*
	 * Description: Reusable function to click on an element
	 * Created By: Siddhartha Upadhyay 
	 * Attribute: element: The webelement itself which will be present in the UI, reason for declaring this click method is that sometimes the locators were not able to work
	 * 			  
	 */
	
	public void click(MobileElement element) {
		try {
			element.click();
			reporter.log("Clicked: " + element.getId());
			((ExtentReporter) reporter).captureScreen(driver);
			Thread.sleep(SHORT_SLEEP_TIME);
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
	}

	/*
	 * Description: Reusable function to click on an element
	 * Created By: Siddhartha Upadhyay 
	 * Attribute: className:other type of locator to search the desired web element
	 * 			  partialText:other type of locator to search the desired web element 
	 */
	
	public void clickByText(String partialText, String className) {
		try {
			List<MobileElement> elements = findElementsByText(partialText, className);
			elements.forEach(e -> System.out.println(e.getText()));
			if (!elements.isEmpty()) {
				for (MobileElement el : elements) {
					if (el.getText().contains(partialText)) {
						el.click();
						((ExtentReporter) reporter).captureScreen(driver);
						reporter.log("Clicked by text: " + partialText);
						Thread.sleep(SHORT_SLEEP_TIME);
						return;
					}
				}
			}
			throw new Exception("No element found by text: " + partialText);
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
	}
	

	/*
	 * Description: Reusable function to input value in a text field
	 * Created By: Siddhartha Upadhyay 
	 * Attribute: strategy- Element type String passed is an id or xpath
	 * 			  identifier- unique element identifier
	 * 			  text- String value to be inserted in the text field
	 */
	
	public void sendKeys(String identifier, String strategy, String text) {
		try {
			driver.findElement(strategy, identifier).sendKeys(text);
			((ExtentReporter) reporter).captureScreen(driver);
			reporter.log("Sent keys: " + text);
			Thread.sleep(SHORT_SLEEP_TIME);
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
	}
	

	/*
	 * Description: Reusable function to press enter after entering text in text fields
	 * Created By: Siddhartha Upadhyay
	 * 
	 */
	
	public void sendEnterKey() {
		try {
			((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
	}

	/*
	 * Description: Reusable function to scroll from one point to other
	 * Created By: Siddhartha Upadhyay 
	 * Attribute: a- initial point of touch
	 * 			  b- destination point
	 * 			  
	 */
	
	public void scroll(Point a, Point b) {
		try {
			Thread.sleep(SHORT_SLEEP_TIME);
			(new TouchAction(driver)).press(PointOption.point(a)).moveTo(PointOption.point(b)).release().perform();
			reporter.log("Scrolled: " + a + " to " + b);
			Thread.sleep(SHORT_SLEEP_TIME);
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
	}
	
	/*
	 * Description: Reusable function to scroll uptill an element
	 * Created By: Siddhartha Upadhyay 
	 * Attribute: strategy- Element type String passed is an id or xpath
	 * 			  identifier- unique element identifier
	 */
	public void scrollDownToElement(String identifier, String strategy) {
		final int maxScrolls = 5;
		int scrolls = 0;
		Dimension d = driver.manage().window().getSize();
		Point a = new Point(d.getWidth() / 2, (int) (d.getHeight() * 0.9f));
		Point b = new Point(d.getWidth() / 2, (int) (d.getHeight() * 0.4f));
		try {
			while (scrolls <= maxScrolls && driver.findElements(strategy, identifier).isEmpty()) {
				scroll(a, b);
				scrolls++;
				Thread.sleep(2 * 1000);
			}
			if (scrolls >= 5) {
				reporter.fail("Max number of scroll limit reached");
			}
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
	}
	
	/*
	 * Description: Reusable function to scroll and click on the webelement via visible text
	 * Created By: Siddhartha Upadhyay 
	 * Attribute: visibleText- the text visible on the webelement
	 * 			  
	 */
	
	public void scrollAndClickByText(String visibleText) {
		try {
			((FindsByAndroidUIAutomator<MobileElement>) driver).findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
							+ visibleText + "\").instance(0))")
					.click();
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
	}

	/*
	 * Description: Reusable function to touch at a particular coordinate on the screen
	 * Created By: Siddhartha Upadhyay 
	 * Attribute: x- x coordinate of the screen grid
	 * 			  y- y coordinate of the screen grid
	 * 			  
	 */
	
	public void touch(int x, int y) {
		try {
			Thread.sleep(SHORT_SLEEP_TIME);
			(new TouchAction(driver)).tap(PointOption.point(x, y)).perform();
			reporter.log("Touched at: " + x + "," + y);
			Thread.sleep(SHORT_SLEEP_TIME);
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
	}

	/*
	 * Description: Function to click Home button on the Amazon Application
	 * Created By: Siddhartha Upadhyay
	 * 
	 */
	
	public void clickHome() {
		try {
			// TODO check if the menu is open
			waitForElement(AMAZON_LOGO_ID, "id");
			click(AMAZON_LOGO_ID, "id");
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
	}

	/*
	 * Description: Function to click Cart button on the Amazon Application
	 * Created By: Siddhartha Upadhyay
	 * 
	 */
	
	public void clickCart() {
		try {
			waitForElement(CART_BUTTON_ID, "id");
			click(CART_BUTTON_ID, "id");
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
		}
	}

	/*
	 * Description: Reusable function to compare 2 strings
	 * Created By: Siddhartha Upadhyay
	 * Attribute: goodKeywords-String value to be compared with
	 * 			  badKeywords- String value to check that should not be present in the details
	 * 			  
	 */
	
	public boolean findProductByKeywords(String[] goodKeywords, String[] badKeywords) {
		final int maxProductsViewed = 20;
		int productsViewed = 0;
		boolean foundAProductAndClicked = false;
		try {
			while (productsViewed <= maxProductsViewed && !foundAProductAndClicked) {
				List<MobileElement> products = driver.findElements(By.id(PRODUCT_DESCRIPTION_ID));
				if (!products.isEmpty()) {
					for (MobileElement p : products) {
						// Check if the product is a good product
						if (isGoodProduct(p, goodKeywords, badKeywords)) {
							click(p);
							foundAProductAndClicked = true;
							break;
						}
					}
					if (!foundAProductAndClicked) {
						productsViewed += products.size();
						scroll(products.get(products.size() - 1).getCenter(), products.get(0).getCenter());
						Thread.sleep(2 * 1000);
					}
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			reporter.error(e.getMessage());
			reporter.fail(e);
			return false;
		}
		return foundAProductAndClicked;
	}

	/*
	 * Description: Function to Verify the product added to the cart
	 * Created By: Siddhartha Upadhyay
	 * Attribute: goodKeywords-String value to be compared with
	 * 			  badKeywords- String value to check that should not be present in the details
	 * 			  e- Mobilelement variable which will get the list of products 
	 */
	protected boolean isGoodProduct(MobileElement e, String[] goodKeywords, String[] badKeywords) {
		if (e == null) {
			return false;
		}
		try {
			if (e.findElements(By.id(PRODUCT_PRICE_ID)).isEmpty()) {
				return false;
			}
			String productName = e.findElement(By.id(PRODUCT_TITLE_ID)).getText().toLowerCase();
			for (String gkw : goodKeywords) {
				if (!productName.contains(gkw.toLowerCase())) {
					return false;
				}
			}
			for (String bkw : badKeywords) {
				if (productName.contains(bkw.toLowerCase())) {
					return false;
				}
			}
		} catch (StaleElementReferenceException e1) {
			reporter.warn("Ignoring stale element exception while finding a good product");
			return false;
		} catch (Exception e2) {
			reporter.error(e2.getMessage());
			reporter.fail(e2);
			return false;
		}
		return true;
	}

}

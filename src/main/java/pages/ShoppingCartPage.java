package pages;

import org.testng.Assert;

import utils.AmazonDriver;
import utils.ReporterInterface;

public class ShoppingCartPage extends AmazonDriver {
	
	//Elements Declaration
	String PRODUCT_NAME_XPATH = "(//*[@resource-id='activeCartViewForm']//*[@class='android.widget.TextView'])[1]";
	String PRODUCT_COUNT_XPATH = "(//*[@resource-id='activeCartViewForm']//*[@class='android.widget.TextView'])[2]";
	String CHECKOUT_XPATH = "(//*[@resource-id='sc-mini-buy-box']//*[@class='android.widget.Button'])";

	/*
	 * Description: Constructor for initializing the class variables and the parent class
	 * Created By: Siddhartha Upadhyay 
	 */
	
	public ShoppingCartPage(ReporterInterface reporter) {
		super(reporter);
	}
	
	/*
	 * Description: Method to navigate to cart
	 * Created By: Siddhartha Upadhyay 
	 *  
	 */
	
	public void gotoCart() {
		clickHome();
		clickCart();
	}
	
	/*
	 * Description: Method to verify the products in cart
	 * Created By: Siddhartha Upadhyay 
	 *  
	 */
	
	public void verifyCartItem() {
		waitForElement(PRODUCT_NAME_XPATH, "xpath");

		String productName = getText(PRODUCT_NAME_XPATH, "xpath").toLowerCase();
		String productCount = getText(PRODUCT_COUNT_XPATH, "xpath");
		Assert.assertNotNull(productName);
		Assert.assertTrue(productName.contains("4k"));
		Assert.assertTrue(Integer.parseInt(productCount) >= 1);
	}
	/*
	 * Description: Method to checkout the products in cart
	 * Created By: Siddhartha Upadhyay 
	 *  
	 */
	public void checkout() {
		click(CHECKOUT_XPATH, "xpath");
	}
}

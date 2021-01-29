package pages;

import org.openqa.selenium.Dimension;

import utils.AmazonDriver;
import utils.ReporterInterface;

public class ProductsPage extends AmazonDriver {

	//Element Declaration
	private static final String ADD_TO_CART_TEXT = "Add to Cart";
	private static final String ADDRESS_POPUP_ID = "com.amazon.mShop.android.shopping:id/loc_ux_gps_prompt_text";

	/*
	 * Description: Constructor for initializing the class variables and the parent class
	 * Created By: Siddhartha Upadhyay 
	 */
	
	public ProductsPage(ReporterInterface reporter) {
		super(reporter);
	}

	/*
	 * Description: Method to add the product(s) in cart
	 * Created By: Siddhartha Upadhyay 
	 *  
	 */
	
	public void addToCart() {
		if (isElementVisible(ADDRESS_POPUP_ID, "id")) {
			Dimension d = driver.manage().window().getSize();
			touch((int) (d.getWidth() * 0.5f), (int) (d.getHeight() * 0.4f));
		}
		scrollAndClickByText(ADD_TO_CART_TEXT);
	}
}

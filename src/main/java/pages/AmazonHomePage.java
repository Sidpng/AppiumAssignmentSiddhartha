package pages;

import utils.AmazonDriver;
import utils.ReporterInterface;

public class AmazonHomePage extends AmazonDriver {
	
	//Element Declaration
	private static final String SEARCH_FIELD_ID = "com.amazon.mShop.android.shopping:id/rs_search_src_text";
	private static final String SEARCH_RESULT_VIEW_ID = "com.amazon.mShop.android.shopping:id/rs_search_result_view";
	
	/*
	 * Description: Constructor for initializing the class variables and the parent class
	 * Created By: Siddhartha Upadhyay 
	 */
	
	public AmazonHomePage(ReporterInterface reporter, String propertyFilePath) {
		super(reporter, propertyFilePath);
	}


	/*
	 * Description: Method to search the products in cart
	 * Created By: Siddhartha Upadhyay 
	 *  
	 */
	
	public void searchProduct() {
		waitForElement(SEARCH_FIELD_ID, "id");
		click(SEARCH_FIELD_ID, "id");
		sendKeys(SEARCH_FIELD_ID, "id", propertyReader.getProperty("searchString"));
		sendEnterKey();
		waitForElement(SEARCH_RESULT_VIEW_ID, "id");

		findProductByKeywords(propertyReader.getProperty("goodKeywords").split(","),
				propertyReader.getProperty("badKeywords").split(","));
	}
}

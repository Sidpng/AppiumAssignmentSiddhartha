package pages;

import utils.AmazonDriver;
import utils.ReporterInterface;

public class SignInPromptPage extends AmazonDriver {
	
	//Element Declaration
	private static final String SKIP_SIGNIN_BUTTON_ID = "com.amazon.mShop.android.shopping:id/skip_sign_in_button";

	/*
	 * Description: Constructor for initializing the class variables and the parent class
	 * Created By: Siddhartha Upadhyay 
	 */
	
	public SignInPromptPage(ReporterInterface reporter) {
		super(reporter);
	}
	
	/*
	 * Description: Method to skip the Login Feature 
	 * Created By: Siddhartha Upadhyay 
	 *  
	 */
	
	public void skipLogin() {
		waitForElement(SKIP_SIGNIN_BUTTON_ID, "id");
		click(SKIP_SIGNIN_BUTTON_ID, "id");
		clickHome();
	}
}

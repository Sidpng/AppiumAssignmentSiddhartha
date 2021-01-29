Name: Amazon Mobile App Automation

Description: Using Appium with Java automate the amazon app to search a 
	     65 inch TV in the app and add to cart and verify its details

NOTE: Before you begin executing the testng.xml file please make sure you provide the details of the mobile device in the capabilities.properties file, It has been kept blank intentionally for now

Visuals: All Steps have been recorded and stored under Reports/Screenshots/*Date of execution*/Name of the Screenshots

Extent Reports have been used throughout the project to monitor all activities and log them in the report.
Project Module Details:
Using POM framework i have managed to create a project structure where Pages,Tests and the Test Utilities are seperate from
each other, 
Under Pages:
For each Page such as SignInPromptPage, AmazonHomePage, ProductsPage and ShoppingCartPage we have defined the page actions in them
and invoking the predefined methods in AmazonDriver class such as Click, WaitForElement, ScrolltoView, etc.  these functions are declared seperately to re-use the same functionalities in some other tc as well.

Under propertyFiles
we have 2 different property files,
One which is a common for all property file which we have used to store the initial configuration values like UDID,mobilename,platform, Platform Version, URL(appium server), AppActivity and AppPackage
The other property file(s) will be for the various Test Scenarios that will be required to test, 
For using dynamically any property file we have added the feature of accepting the file path name in the main method which then takes the property file based on the test case so in a way we have mapped the TestCase with their respective Property file

Under AmazonDriver
Constructor for initializing the class variables and the parent class, default one initializes the capabilities properties which will be common for all Test Runs
Constructor for initializing the class variables and the parent class, Parameterized with the custom property file which will be linked with its respective test cases

Under ReporterInterface
we have defined status functions like PASS,FAIL,ERROR,WARN and FLUSH(to write the logs at the end of the testing)
We are implementing this interface then in the Reporting Class and calling each method according to the Validation
i.e if the the test case result is going for Positive assertion then Pass(), else Fail(), etc.
And in the end FLUSH()

For Capturing Screenshots at each step of testing:
We have defined a method under ExtentReporter named captureScreen(driver)  this will be invoked after every definitive step in test case such as clicking on search bar, sending keys , pressing enter, adding to cart.
The method stores the screenshot in a custom name which includes the date of execution and the format function of the String for proper formatting

Testng XML
Is direct invoking the test package to run all the tests within it , we can customize it later to run class wise or test wise as per business requirement



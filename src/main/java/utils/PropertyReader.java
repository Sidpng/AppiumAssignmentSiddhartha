package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {

	//Variable Declaration
	private static final String PROPERTIES_PATH = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "main" + File.separator + "resources" + File.separator + "propertyFiles";
	private static final String CAPABILITIES_PROP_PATH = PROPERTIES_PATH + File.separator + "capabilities.properties";
	private Properties prop;

	/*
	 * Description: Reusable function to read the default property file containing the desired capabilities
	 * Created By: Siddhartha Upadhyay
	 * 			  
	 */
	
	public PropertyReader() {
		prop = new Properties();

		try (FileInputStream capabilitiesFileStream = new FileInputStream(CAPABILITIES_PROP_PATH);) {
			prop.load(capabilitiesFileStream);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/*
	 * Description: Reusable function to read the custom property file containing the Test Data specific to a Test Case
	 * Created By: Siddhartha Upadhyay
	 * 			  
	 */
	
	public PropertyReader(String filePath) {
		this();
		try (FileInputStream fileStream = new FileInputStream(PROPERTIES_PATH + File.separator + filePath);) {
			prop.load(fileStream);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/*
	 * Description: Reusable function to fetch the property from the property files
	 * Created By: Siddhartha Upadhyay
	 * 			  
	 */
	
	public Properties getProp() {
		return prop;
	}
	
	/*
	 * Description: Reusable function to fetch the property from the property files by name
	 * Created By: Siddhartha Upadhyay
	 * 			  
	 */
	
	public String getProperty(String name) {
		String demo = prop.getProperty(name);
		return demo;
	}

}

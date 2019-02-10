package driverManager;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import dataproviders.ConfigFileReader;
import utilities.Log;


public class WebDriverManager {
	
	private  static WebDriver driver = null;
	private static String driverType;
	
	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
	
	
	private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
	
	static ConfigFileReader cfr;
	 
	
	public WebDriverManager() {			
		
	}
	
	public static WebDriver getDriver() {
		//cfr = FileReaderManager.getConfigFileReader();
		driverType = ConfigFileReader.getConfigFileReader().getBrowser();	
		Log.info("Initializing Driver :"+driverType);
        switch (driverType) {        
        
        case "chrome" :
            driver = drivers.get("Chrome");
            System.out.println("The getdriver instance is :"+driver);
            
            if (driver==null || driver.toString().contains("(null)")) {
                 System.setProperty(CHROME_DRIVER_PROPERTY, ConfigFileReader.getConfigFileReader().getDriverPath());
                 //ChromeOptions options = new ChromeOptions();
                 //options.addArguments("--headless");
                 //options.addArguments("--disable-gpu");
                 //options.addArguments("--incognito");
                 //DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                 //capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                 //driver= new ChromeDriver(capabilities);
                 driver = new ChromeDriver();
                 drivers.put("Chrome", driver);
                
                /*System.setProperty(CHROME_DRIVER_PROPERTY, ConfigFileReader.getConfigFileReader().getDriverPath());
                ChromeOptions options = new ChromeOptions();
                //options.addArguments("--incognito");
                options.addArguments("--headless");
                //*****************************************************8
                //options.setBinary("/opt/google/chrome/");
                options.setBinary("/opt/google/chrome/chrome");
                //options.setBinary("/usr/bin/google-chrome");
                options.addArguments("--no-sandbox");
                //*****************************************************8
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                driver= new ChromeDriver(capabilities);
                drivers.put("Chrome", driver);
                Log.info("Created driver is :"+driver.toString());*/
            }
            /*System.setProperty(CHROME_DRIVER_PROPERTY, ConfigFileReader.getConfigFileReader().getDriverPath());
            driver = new ChromeDriver();
            drivers.put("Chrome", driver);*/
        	
    		break;
        }

        /*if(cfr.getBrowserWindowSize()) {
        	driver.manage().window().maximize();
        }
        
        driver.manage().timeouts().implicitlyWait(cfr.getImplicitlyWait(), TimeUnit.SECONDS);
        System.out.println("The driver Type is2 :"+driverType);*/
		return driver;
	}	
	
	public static void closeDriver() {
		try {
			Log.info("Closing The Driver");
			driver.close();
			Log.info("Driver is closed");
			driver.quit();
		}catch(Exception e) {
			Log.error("Either Driver is not initialized or closed before");
		}
		
		
	}
	
	

}

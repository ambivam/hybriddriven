package saskenpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import utilities.Log;
import utilities.SeleniumHelper;

public class SaskenLoginPage extends SeleniumHelper{
	
	//WebDriver driver;	
	
	
	public SaskenLoginPage(WebDriver driver) {
		super(driver);
	    PageFactory.initElements(driver, this);
	    
	    
	}
	
	@FindBy(how = How.XPATH, using = "//input[@name='sph_username']") 
	private WebElement username;
	
	
	@FindBy(how = How.XPATH, using = "//input[@name='sph_password']") 
	private WebElement password;
	
	@FindBy(how = How.XPATH, using = "//input[@type='submit']") 
	private WebElement loginbutton;
	
	@FindBy(how = How.XPATH, using = "//a[@role='button']") 
	private WebElement usericon;
	
	@FindBy(how = How.XPATH, using = "//ul[@class='dropdown-menu']/li[2]/a") 
	private WebElement logout;
	
	public void enterUserName(String userName) {
		Log.info("Enters username as : "+userName +" on LoginPage");
		enterText(username,userName, "Enters username");
	}
 
	public void enterPassword(String pwd) {
		Log.info("Enters password as : "+pwd+" on LoginPage");
		enterText(password,pwd, "Enters Password :");
	}
	
	public void clickOnLoginButton() {
		Log.info("Clicking on Login Button on LoginPage");
		click(loginbutton, "Click on Login Button on LoginPage");
		waitForPageLoad();
	}
	
	
	
}


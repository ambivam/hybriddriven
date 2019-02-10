package saskenpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import utilities.Log;
import utilities.SeleniumHelper;

public class SaskenPortalLandingPage extends SeleniumHelper{
	
	public SaskenPortalLandingPage(WebDriver driver) {
		super(driver);
	    PageFactory.initElements(driver, this);
	    
	    
	}
	
	@FindBy(how = How.XPATH, using = "//a[@href='http://www.sasken.com']") 
	private WebElement saskencom;	
	
	
	
	@FindBy(how = How.XPATH, using = "//div[@class='modal fade in']//button[@type='button'][contains(text(),'Close')]") 
	private WebElement close;
	
	public void validate(String elementTobeValidated) {
		
		click(close,"Close Button Popup");
		
		Log.info("validating "+elementTobeValidated);
		validateElementText(saskencom,elementTobeValidated);
		
	}
 
	

}

package pagesFactory;

import org.openqa.selenium.WebDriver;


import saskenpages.SaskenLoginPage;
import saskenpages.SaskenPortalLandingPage;
import utilities.Log;


public class AppPageFactory {

	
	
	private SaskenLoginPage saskenloginpage;
	private SaskenPortalLandingPage saskenPortalLandingPage;

	

	private WebDriver driver;


	public AppPageFactory(WebDriver driver) {

		this.driver = driver;

	}




	public SaskenLoginPage getSaskenLoginPage(){
		Log.info("Initializing LoginPage");

		return (saskenloginpage == null) ? saskenloginpage = new SaskenLoginPage(driver) : saskenloginpage;

	}



	public SaskenPortalLandingPage getSaskenPortalLandingPage(){
		Log.info("Initializing SaskenPortalLandingPage");

		return (saskenPortalLandingPage == null) ? saskenPortalLandingPage = new SaskenPortalLandingPage(driver) : saskenPortalLandingPage;

	}



}

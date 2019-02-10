package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

//import cucumber.api.Scenario;
import dataproviders.ConfigFileReader;
import driverManager.WebDriverManager;


public class SeleniumHelper {

	WebDriver driver;	
	Properties properties;
	//private static Scenario scenario;


	public SeleniumHelper(WebDriver driver){
		this.driver = driver;
		this.driver.manage().window().maximize();
	}


	public boolean isEnabled(WebElement element) {
		boolean temp=false;
		try {
			if (element.isEnabled()) {
				temp=true;
				return temp;
			}
		}catch(Exception e) {
			raiseException();
		}
		return temp;
		
	}

	public String getLocatorFromObjecRepo(String ObjectRepoPath,String locator,String... values)
	{

		String loc=null;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+ObjectRepoPath));
			properties = new Properties();
			try {
				properties.load(reader);
				loc=properties.getProperty(locator);
				for(int i=0;i<values.length;i++)
				{
					loc=loc.replace("parameterizedValue"+i, values[i]);
				}
				System.out.println(loc);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + System.getProperty("user.dir")+ObjectRepoPath);
		}
		return loc;
	}

	protected boolean isExists(WebElement element) {

		System.out.println(element.getText());
		boolean eleExists = false;
		try {
			if (element.getText() != null) {
				eleExists= true;
				Log.info("element is present");
			}
		} catch (NoSuchElementException  e) {
			Log.error("element is not present");
			raiseException();
		}
		return eleExists;

	}
	
	protected void validateElementText(WebElement element,String text) {

		boolean eleExists = false;
		Log.info("Elements Text is :"+element.getText());
		try {
			if (element.getText() != null && text.trim().equalsIgnoreCase(element.getText().trim())) {
				
				Log.info("Validated Text "+text);
			}else {
				Log.error("Validation failed as actual Text is "+text);
				raiseException();
			}
		} catch (NoSuchElementException  e) {
			Log.error("Failed to validate text :"+text);
			raiseException();
		}
		

	}



	/**
	 * @name : SelectCheckBox
	 * @author Avinash
	 * @description selects a check box
	 * @param element - PO
	 * @param strFieldName - name of the object
	 * @return - void
	 */ 
	public void SelectCheckBox(WebElement element, String strFieldName)  {
		try {
			if (this.isExists(element)) {//System.out.println("Before enter data");
				if (this.isEnabled(element)) {//System.out.println("Before enter data");
					driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

					if (!element.isSelected()) {
						element.click();
						Log.info(strFieldName +" check box is selected");
					}
				} else

					Log.error(strFieldName +" check box is selected");
			} else

				Log.error(strFieldName +" check box is selected");
		}
		catch(Exception e)
		{
			raiseException();
		}

	}

	/**
	 * @name : selectListValuebyIndex
	 * @author Avinash
	 * @description selects the element from a dropdown based on index
	 * @param element - PO
	 * @param strFieldName - Name of the dropdown
	 * @param index - index of the elemene to select - starts with 0
	 * @return - void
	 */ 
	public void selectListValuebyIndex(WebElement element, String strFieldName, int index) {
		try
		{if (this.isExists(element)) {
			new Select(element).selectByIndex(index);
			String selectedOption = new Select(element).getFirstSelectedOption().getText();
			if (selectedOption != null)
				Log.info(strFieldName +"  is selected");
			else
				Log.error(strFieldName +"  is not selected");
		}
		}catch(Exception e) {
			Log.error(strFieldName +"  is not selected");
			raiseException();
		}
	}

	public void selectListValuebyIndex(String element, String strFieldName, int index) {

		WebElement tempEle=getElement(element);
		try {
			if (this.isExists(tempEle)) {
				new Select(tempEle).selectByIndex(index);
				String selectedOption = new Select(tempEle).getFirstSelectedOption().getText();
				if (selectedOption != null)
					Log.info(strFieldName +"  is selected");
				else
					Log.error(strFieldName +"  is not selected");
			}
		}
		catch(Exception e)
		{
			Log.error(strFieldName +"  is not selected");
			raiseException();
		}
	}



	public void selectListValuebyVisbileText(WebElement element, String strFieldName,String description) {
		String selectedOption=null;
		try {
			if (this.isExists(element)) {
				new Select(element).selectByVisibleText(strFieldName);
				waitForPageLoad();
				selectedOption = new Select(element).getFirstSelectedOption().getText();
				if (selectedOption != null)
					Log.info(strFieldName +"  is selected");
				else
					Log.error(strFieldName +"  is not selected");
			}
		} catch (Exception e){
			Log.error(strFieldName +"  is not selected");
			raiseException();

		}
	}

	public void selectListValuebyVisbileText(String element, String strFieldName,String description) {
		WebElement tempEle=getElement(element);
		String selectedOption=null;
		try {
			if (this.isExists(tempEle)) {
				new Select(tempEle).selectByVisibleText(strFieldName);
				waitForPageLoad();
				selectedOption = new Select(tempEle).getFirstSelectedOption().getText();
				if (selectedOption != null)
					Log.info(strFieldName +"  is selected");
				else
					Log.error(strFieldName +"  is not selected");
			}
		} catch (Exception e){
			Log.error(strFieldName +"  is not selected");
			raiseException();

		}
	}

	/**
	 * @name : selectListValuebyValue
	 * @author Avinash
	 * @description gets the value entered in text box
	 * @param element - PO
	 * @param strFieldName - Name of the element
	 * @return - void
	 */ 
	public void selectListValuebyValue(WebElement element, String strFieldName) {
		String selectedOption=null;
		try {
			if (this.isExists(element)) {
				new Select(element).selectByValue(strFieldName);
				//waitForPageLoad();
				selectedOption = new Select(element).getFirstSelectedOption().getText();
				if (selectedOption != null)
					Log.info(strFieldName +"  is selected");
				else
					Log.error(strFieldName +"  is not selected");
			}
		} catch (Exception e){

			Log.error(strFieldName +"  is not selected");
			raiseException();
		}
	}

	public void selectListValuebyValue(String element, String strFieldName) {
		String selectedOption=null;
		/*WebElement tempEle=getElement(element);*/
		try {
			if (this.isExists(driver.findElement(By.xpath(element)))) {
				new Select(driver.findElement(By.xpath(element))).selectByValue(strFieldName);
				//waitForPageLoad();
				selectedOption = new Select(driver.findElement(By.xpath(element))).getFirstSelectedOption().getText();
				if (selectedOption != null)
					Log.info(strFieldName +"  is selected");
				else
					Log.error(strFieldName +"  is not selected");
			}
		} catch (Exception e){

			Log.error(strFieldName +"  is not selected");
			raiseException();
		}
	}

	/**
	 * @name : setValueTextBox
	 * @author Avinash
	 * @description enters the text into a text box
	 * @param element - PO
	 * @param data - text to enter
	 * @param strFieldName - name of the text box
	 * @return - void
	 */ 
	public void setValueTextBox(WebElement element, String data, String strFieldName) {
		try {
		if (this.isExists(element)) {//System.out.println("Before enter data");
			if (this.isEnabled(element)) {//System.out.println("Before enter data");
				element.click();
				element.clear();
				element.clear();
				driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

				element.click();
				element.clear();
				element.clear();
				element.sendKeys(data);
				Log.error(data +"  is entered");
			} else

				System.out.println();
			Log.error(data +"  is not entered");
		
		} else
			Log.error(data +"  is not entered");
		System.out.println();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}catch (Exception e) {
		raiseException();
	}
	}
	
	
	
	/**
	 * @name : setValueTextBox
	 * @author Avinash
	 * @description enters the text into a text box
	 * @param element - PO
	 * @param data - text to enter
	 * @param strFieldName - name of the text box
	 * @return - void
	 */ 
	public void setValueTextBox(String element, String data, String strFieldName) {
		waitForPageLoad();
		WebElement ele=getElement(element);
		try {
			
		if (this.isExists(ele)) {//System.out.println("Before enter data");
			if (this.isEnabled(ele)) {//System.out.println("Before enter data");
				ele.click();
				ele.clear();
				ele.clear();
				driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
				
				ele.click();
				ele.clear();
				ele.clear();
				waitForPageLoad();
				ele.sendKeys(data);
				Log.error(data +"  is entered");
			} else

				System.out.println();
			Log.error(data +"  is not entered");
		} else
			Log.error(data +"  is not entered");
		System.out.println();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}catch (Exception e) {
		raiseException();
	}
	}


	/**
	 * @name : switchToFirstBrowser
	 * @author Avinash
	 * @description swithces the driver to first opened browser. Works for any browser
	 * @param none
	 * @return void
	 */

	public void switchToFirstBrowser(){

		try {
			ArrayList<String> getAllWindows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(getAllWindows.get(0));
			driver.manage().window().maximize();
		}catch (Exception e) {
			raiseException();
		}
	}


	/**
	 * @name : switchToLatestBrowser
	 * @author Avinash
	 * @description switches the driver to latest opened browser. Works for any browser
	 * @param none
	 * @return void
	 */
	public void switchToLatestBrowser(){

		try {
		ArrayList<String> getAllWindows = new ArrayList<String>(driver.getWindowHandles());
		int maxWindowSize = driver.getWindowHandles().size();
		driver.switchTo().window(getAllWindows.get(maxWindowSize-1));
		driver.manage().window().maximize();
		}catch (Exception e) {
			raiseException();
		}

	}

	/**
	 * @name : verifyElementExists
	 * @author Avinash
	 * @description verifies if an element is visible in the application.  If the element is not present, will return FAILED in the reports
	 * @param elementValue - Page object 
	 * @param elementName - Name of the element
	 * @return Boolean
	 */
	public boolean verifyElementExists(WebElement elementValue, String elementName)  {
		boolean isElementExists=false;
		try {
			if (this.isExists(elementValue)) {

				Log.info("element exists");
				isElementExists= true;
			} else {
				Log.error("element does not exists");
			}
		} catch (NoSuchElementException  e) {
			Log.error("element does not exists");
			raiseException();
		}
		return isElementExists;
	}




	/**
	 * @author AnkireAv
	 * @MouseOver on element
	 * @param element
	 * @param strFieldName
	 */
	public void mouseOverObject(WebElement element, String strFieldName) {
		try {
			if (this.isExists(element)) {
				if (this.isEnabled(element)) {

					waitForPageLoad();
					Actions action = new Actions(driver);
					action.moveToElement(element).build().perform();
					Log.info("mouse hove is performed");
					//log
					//write a statement to log
					//write a statement to report
				} else
					//log
					Log.error("mouse hove is not performed");
				//write a statement to log saying object is disabled
				//write a statement to report object is disabled
			} else
				//log
				Log.error("mouse hove is not performed");
			//write a statement to log saying object does not exist
			//write a statement to report object is does not exist
			//waitForPageLoad();

		}catch(Exception e)
		{
			Log.error("mouse hove is not performed");
			raiseException();
		}

	}


	public void mouseOverObject(String element, String strFieldName) {
		WebElement ele=getElement(element);
		try {
			if (this.isExists(ele)) {
				if (this.isEnabled(ele)) {

					waitForPageLoad();
					Actions action = new Actions(driver);
					action.moveToElement(ele).build().perform();
					Log.info("mouse hove is performed");
					//log
					//write a statement to log
					//write a statement to report
				} else
					//log
					Log.error("mouse hove is not performed");
				//write a statement to log saying object is disabled
				//write a statement to report object is disabled
			} else
				//log
				Log.error("mouse hove is not performed");
			//write a statement to log saying object does not exist
			//write a statement to report object is does not exist
			//waitForPageLoad();

		}catch(Exception e)
		{
			Log.error("mouse hove is not performed");
			raiseException();
		}

	}


	public void compareValuesv2(String labelValueUI,String strToCompare,String labelName)
	{
		try {
			if (strToCompare != "" && strToCompare != null) {
				strToCompare = strToCompare.toUpperCase().trim();
				if (labelValueUI.trim().equalsIgnoreCase(strToCompare)) {
					System.out.println();
				} else {
					System.out.println();
				}
			} 

		} catch (NoSuchElementException e) {
			raiseException();
		}
	}

	public String convertOneDateFormatToOtherFormat(String dateUI,String initialFormat,String targetFormat) throws ParseException
	{

		String returnValue=null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(initialFormat);
			Date date = formatter.parse(dateUI);
			System.out.println(date);
			System.out.println(formatter.format(date));
			SimpleDateFormat formatter1 = new SimpleDateFormat(targetFormat);
			System.out.println(formatter1.format(date));
			returnValue=formatter1.format(date);
			
		}catch (Exception e) {
			raiseException();
		}return returnValue;
		
	}

	public long getNumberOfDays(String fromDate,String toDate){
		SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
		String todayDate = fromDate;
		if(todayDate==""){
			Date date = new Date();
			todayDate = (myFormat.format(date));
		}
		long diff = 0;
		try {
			Date date1 = myFormat.parse(todayDate);
			Date date2 = myFormat.parse(toDate);
			diff = date2.getTime() - date1.getTime();
			System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
			return  TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			raiseException();
		}
		return diff;
	}

	public String currentTime() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String currTime= sdf.format(cal.getTime()) ;
		return currTime;
	}

	public String getElementAttribute(WebElement element, String attributeName) {
		
		String txtValue="";
		try{
			if (this.isExists(element)) {
				txtValue = element.getAttribute(attributeName);
				//	System.out.println(txtValue);
			} else
				return null;

		} catch (Exception e){
			raiseException();
		}return txtValue;
	}

	public ArrayList<String> sortArrayList(ArrayList<String> UIRetValues) {

		try {
		Collections.sort(UIRetValues);
		return UIRetValues;
		
		}catch (Exception e) {
			raiseException();
		}return UIRetValues;
	}

	//xpath=_  //input=123

	protected WebElement getElement(String strElement) {
		WebElement tempWebElement = null;
		WebDriverWait wait = new WebDriverWait(driver,ConfigFileReader.getConfigFileReader().getWebDriverWait());
		try {
			Log.info("Into getElement and working on : "+strElement);

			String tempElementPrefix,tempElementId;
			String[] tempElement;


			tempElement = strElement.split(";");
			tempElementPrefix = tempElement[0];
			tempElementId = tempElement[1];		

			if (tempElementPrefix.toLowerCase().trim().startsWith("xpath")) {
				tempWebElement = wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath(tempElementId))));            
			}else if(tempElementPrefix.toLowerCase().trim().startsWith("id")) {
				tempWebElement = wait.until(ExpectedConditions.presenceOfElementLocated((By.id(tempElementId))));
			}else if(tempElementPrefix.toLowerCase().trim().startsWith("link")) {
				tempWebElement = wait.until(ExpectedConditions.presenceOfElementLocated((By.linkText(tempElementId))));
			}else if(tempElementPrefix.toLowerCase().trim().startsWith("css")) {
				tempWebElement = wait.until(ExpectedConditions.presenceOfElementLocated((By.cssSelector(tempElementId))));
			}else if(tempElementPrefix.toLowerCase().trim().startsWith("name")) {
				tempWebElement = wait.until(ExpectedConditions.presenceOfElementLocated((By.name(tempElementId))));
			}else if(tempElementPrefix.toLowerCase().trim().startsWith("tag")) {
				tempWebElement = wait.until(ExpectedConditions.presenceOfElementLocated((By.tagName(tempElementId))));
			}else if(tempElementPrefix.toLowerCase().trim().startsWith("class")) {
				tempWebElement = wait.until(ExpectedConditions.presenceOfElementLocated((By.className(tempElementId))));
			}
			Log.info("Into getElement WebElement returned is : "+tempWebElement.toString());

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			raiseException();
		}
		return tempWebElement;

	}


	//################################################################################	
	protected void enterText(String element,String strText,String description) {
		Log.info("Into enterText by using input text "+strText+" and locatior :"+element);
		WebElement tempWebElement = getElement(element);

		try {
			WebDriverWait wait = new WebDriverWait(driver,ConfigFileReader.getConfigFileReader().getWebDriverWait());

			if (isExists(tempWebElement)) {//System.out.println("Before enter data");
				if (this.isEnabled(tempWebElement)) {//System.out.println("Before enter data");
					wait.until(ExpectedConditions.elementToBeClickable((driver.findElement(By.xpath(element)))));
					tempWebElement.click();
					tempWebElement.clear();
					tempWebElement.clear();
					driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
					// System.out.println("Before enter data");

					tempWebElement.click();
					tempWebElement.clear();
					tempWebElement.clear();
					tempWebElement.sendKeys(strText);

					//write a statement to log
					//write a statement to report
				} else

					System.out.println();
				//write a statement to log saying object is disabled
				//write a statement to report object is disabled
			} else

				System.out.println();
			//write a statement to log saying object does not exist
			//write a statement to report object is does not exist
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		catch(Exception e){
			Log.error("Exception generated while working with enterText by using input text "+strText+" and locatior :"+element);
			raiseException();
		}
	}

	protected void enterText(WebElement element,String strText,String description) {
		Log.info("Into enterText by using input text "+strText+" and locatior element :"+element.toString());
		try {
			WebDriverWait wait = new WebDriverWait(driver,ConfigFileReader.getConfigFileReader().getWebDriverWait());

			if (isExists(element)) {//System.out.println("Before enter data");
				if (isEnabled(element)) {//System.out.println("Before enter data");
					wait.until(ExpectedConditions.elementToBeClickable(element));
					/*
					 * element.click(); element.clear(); element.clear();
					 * driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS); //
					 * System.out.println("Before enter data");
					 * 
					 * element.click(); element.clear();
					 */
					element.clear();

					element.sendKeys(strText);
					Log.info("Entered enterText: "+strText);
				}
			}
		}
		catch(Exception e){
			Log.error("Exception generated while working with enterText by using input text "+strText+" and locator element :"+element.toString());
			raiseException();
		}
	}

	//################################################################################


	protected void click(String element,String description) {
		Log.info("Into click by using locatior :"+element.toString());
		WebElement tempWebElement = null;
		try {
			tempWebElement=getElement(element);
			WebDriverWait wait = new WebDriverWait(driver,ConfigFileReader.getConfigFileReader().getWebDriverWait());

			if(isExists(tempWebElement))
			{
				wait.until(ExpectedConditions.elementToBeClickable(tempWebElement));
				
				tempWebElement.click();
				Log.info(description + "is clicked");
			}
			else
			{
				Log.info(description + "is NOT clicked");
			}
		}
		catch(Exception e) {  
			Log.error("Exception generated while working with Click and element " +element.toString() );
        	//System.out.println(e.getMessage());
			//e.printStackTrace();
			raiseException();
		}

	}

	protected void click(WebElement element,String description) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,ConfigFileReader.getConfigFileReader().getWebDriverWait());

			if(isExists(element))
			{
				wait.until(ExpectedConditions.elementToBeClickable(element));
				element.click();
				Log.info(description + " is clicked");
			}
			else
			{
				Log.info(description + " is NOT clicked");
				
			}
		}
		catch(Exception e) { 
			e.printStackTrace();
			System.out.println(e.getMessage());
			raiseException();
		}

	}


	//################################################################################

	protected String getText(String element,String description) {
		WebElement temEle;
		String text = "";
		WebDriverWait wait = new WebDriverWait(driver,ConfigFileReader.getConfigFileReader().getWebDriverWait());
		try {
			temEle = getElement(element);
			if(isExists(temEle))
			{
				wait.until(ExpectedConditions.visibilityOf(temEle));
				text = temEle.getText();
			}
			else
			{
				Log.info(description+ " is not retrieved");
			}
		}
		catch(Exception e) {
			Log.info("Element is not displayed");
			raiseException();
		}

		return text;
	}

	protected String getText(WebElement element,String description) {

		String text = "";
		WebDriverWait wait = new WebDriverWait(driver,ConfigFileReader.getConfigFileReader().getWebDriverWait());
		try {
			if(isExists(element))
			{
				wait.until(ExpectedConditions.visibilityOf(element));
				text = element.getText();
			}
			else
			{
				Log.info(description+ " is not retrieved");
			}
		}
		catch(Exception e) {
			raiseException();
		}
		return text;
	}

	protected String getText(WebElement element,String value, String description) {

		WebDriverWait wait = new WebDriverWait(driver,ConfigFileReader.getConfigFileReader().getWebDriverWait());
		try {
			if(isExists(element))
			{
				wait.until(ExpectedConditions.visibilityOf(element));
				element.sendKeys(value);

			}
			else
			{
				Log.info(description+ " is not retrieved");
			}
		}
		catch(Exception e) {
			raiseException();
		}
		return element.getAttribute("value");
	}

	/*//################################################################################

	protected boolean verifyText(String element,String elementText) {
		WebElement temEle;
		boolean result = false;
		try {
			temEle = getElement(element);            
			if(temEle.getText().toLowerCase().trim() == (elementText).toLowerCase().trim()){
				result = true;
			}
			else {
				result = false;
			}
		}
		catch(Exception e){
			raiseException();
		}
		return result;
	}

	protected boolean verifyText(WebElement element,String elementText) {
		WebElement temEle;
		boolean result = false;
		try {
			temEle = getElement(element);            
			if(temEle.getText().toLowerCase().trim() == (elementText).toLowerCase().trim()){
				result = true;
			}
			else {
				result = false;
			}
		}
		catch(Exception e){
			raiseException();
		}
		return result;
	}*/



	//################################################################################

	protected void pause() {

		try {
			Thread.sleep(ConfigFileReader.getConfigFileReader().getPause());
		} catch (InterruptedException e) {
			raiseException();
		}
	}

	protected void pageLoadPause() {

		try {
			Thread.sleep(ConfigFileReader.getConfigFileReader().getPageLoadPause());
		} catch (InterruptedException e) {
			raiseException();
		}
	}	

	//################################################################################
	protected boolean isElementVisible(String element,String description) {
		WebElement tempEle=getElement(element);
		Boolean eleStat=false;
		try {

			WebDriverWait wait = new WebDriverWait(driver,ConfigFileReader.getConfigFileReader().getWebDriverWait());
			if(isExists(tempEle))
			{
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(element))));
				if(tempEle.isDisplayed())
				{
					Log.info(description+" is displayed");
					eleStat=true;
				}
				else
				{
					Log.info(description+" is not displayed");
					eleStat=false;
				}
			}

		}
		catch(Exception e) {
			Log.error(description+" is not displayed");
			raiseException();

		}
		return eleStat;

	}
	
	
	//################################################################################
		protected boolean isElementVisibleAs(WebElement element,String description) {
			
			Boolean eleStat=false;
			try {

				WebDriverWait wait = new WebDriverWait(driver,ConfigFileReader.getConfigFileReader().getWebDriverWait());


				if(isExists(element))
				{
					wait.until(ExpectedConditions.visibilityOf((element)));
					if(element.isDisplayed())
					{
						Log.info(description+" is displayed");
						eleStat=true;
					}
					else
					{
						Log.info(description+" is not displayed");
						eleStat=false;
					}
				}

			}
			catch(Exception e) {
				Log.error(description+" is not displayed");
				raiseException();

			}
			return eleStat;

		}

	public void isElementVisible(WebElement element,String description) {


		try {
			WebDriverWait wait = new WebDriverWait(driver,ConfigFileReader.getConfigFileReader().getWebDriverWait());
			wait.until(ExpectedConditions.visibilityOf(element));

			if(isExists((element)))
			{
				if(element.isDisplayed())
				{
					Log.info(description+" is visible");
				}
				else
				{
					Log.info(description+" is not visible");
					raiseException();
				}
			}
		}
		catch(Exception e) {
			raiseException();
		}

	}


	//################################################################################


	protected void raiseException() {

		takeScreenShot();
		cleanup();
		//Assert.assertTrue(false);
		//Reporter.addScreenCaptureFromPath("imagePath");

	}
	
	protected void raiseException(String assertionMsg) {

		takeScreenShot();
		//cleanup();
		
		//Assert.assertTrue(assertionMsg,false);
		//Reporter.addScreenCaptureFromPath("imagePath");

	}

	/*protected void takeScreenShot() {
		 Random rand = new Random();
		try {
			//This takes a screenshot from the driver at save it to the specified location
			String screenshotName = "screenshotName";



			File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);


			//Building up the destination path for the screenshot to save
			//Also make sure to create a folder 'screenshots' with in the cucumber-report folder
			System.out.println("target screenshot : "+(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/" + screenshotName +"_"+rand.nextInt(10000)+".png"));

			File destinationPath = new File(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/" + screenshotName +"_"+rand.nextInt(10000)+".png");

			destinationPath.getParentFile().mkdirs();
			destinationPath.createNewFile();
			//Copy taken screenshot from source location to destination location
			Files.copy(sourcePath, destinationPath);   
			System.out.println("before reporter screenshot : "+destinationPath + ","+sourcePath);
			//This attach the specified screenshot to the test
			Reporter.addScreenCaptureFromPath(destinationPath.toString());


		} catch (IOException e) {

			System.out.println("Into Screenshot exception");
		} 

	}*/

	protected void takeScreenShot() {

		try {
			System.out.println("take screenshot");
			final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			
			//scenario.embed(screenshot, "image/png"); //stick it in the report
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void waitForPageLoad()  {

		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver,ConfigFileReader.getConfigFileReader().getWebDriverWait());
		wait.until(pageLoadCondition);
		try {
			//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			pause();
			System.out.println();
		} catch (Exception e) {

		}
	}


	public String getCurrentPageURL(){

		String url = driver.getCurrentUrl();
		return url;
	}

	protected void cleanup() {
		WebDriverManager.closeDriver();;

	}

	protected WebDriver getDriver() {
		return WebDriverManager.getDriver();
	}



	

	protected void browseFile(WebElement element, String filepath) {

		try {
			element.sendKeys(System.getProperty("user.dir")+filepath);
		}
		catch(Exception e) {  
			Log.error("File not found " + filepath);
			raiseException();
		}	
	}


	public Boolean isElementPresent(By by) {
		Boolean isElementPresent=false;
		try {
			waitForPageLoad();
			driver.manage().timeouts().implicitlyWait(400, TimeUnit.MILLISECONDS);
			isElementPresent=driver.findElements(by).size()!= 0;
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			return isElementPresent;
		} catch (NoSuchElementException e) {
			raiseException();
			
		}return isElementPresent;
	}


	public void isElementPresent(By by,String description) {
		Boolean isElementPresent=false;
		try {
			waitForPageLoad();
			driver.manage().timeouts().implicitlyWait(400, TimeUnit.MILLISECONDS);
			isElementPresent=driver.findElements(by).size()!= 0;
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			if(isElementPresent) {
				Log.info(description +" is displayed");
			}else
			{
				Log.error(description +" is not displayed");
			}

		} catch (NoSuchElementException e) {
			Log.error(description +" is not displayed");
			raiseException();
		}
	}




	/**
	 * @author Hari
	 * @ScrollView on element
	 * @param element
	 */
	public void scrollIntoView(WebElement element,String description) {
		try {
			if (this.isExists(element)) {
				if (this.isEnabled(element)) {
					waitForPageLoad();
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
					Log.info("scroll is performed");
					//log
					//write a statement to log
					//write a statement to report
				} else
					//log
					Log.error("scroll is not performed "+description);
				//write a statement to log saying object is disabled
				//write a statement to report object is disabled
			} else
				//log
				Log.error("scroll is not performed "+description);
			//write a statement to log saying object does not exist
			//write a statement to report object is does not exist
			//waitForPageLoad();

		}catch(Exception e)
		{
			Log.error("mouse hove is not performed "+description);
			raiseException();
		}

	}

	/**
	 * @author Hari
	 * @ScrollView on element
	 * @param element
	 */
	public void scrollIntoView(String element,String description) {
		WebElement ele=getElement(element);
		try {
			if (this.isExists(ele)) {
				if (this.isEnabled(ele)) {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
					Log.info("scroll is performed on " +description);
					//log
					//write a statement to log
					//write a statement to report
				} else
					//log
					Log.error("scroll is not performed on "+description);
				//write a statement to log saying object is disabled
				//write a statement to report object is disabled
			} else
				//log
				Log.error("scroll is not performed "+description);
			//write a statement to log saying object does not exist
			//write a statement to report object is does not exist
			//waitForPageLoad();

		}catch(Exception e)
		{
			Log.error("mouse hove is not performed "+description);
			raiseException();
		}

	}
	
	public static void scrollUp(WebDriver driver) {
        
        JavascriptExecutor js=((JavascriptExecutor)driver);
        js.executeScript("scroll(0, -1500);");
    }
   
    public static void scrollDown(WebDriver driver) {
        
        JavascriptExecutor js=((JavascriptExecutor)driver);
        js.executeScript("scroll(1500, 0);");
    }
    
    public void scrollIntoView(WebElement element, WebDriver driver) {
        try {
        	 JavascriptExecutor js=((JavascriptExecutor)driver);
             js.executeScript("arguments[0].scrollIntoView(true);", element);
        }catch (Exception e) {
        	raiseException();
        }
       
    }

}
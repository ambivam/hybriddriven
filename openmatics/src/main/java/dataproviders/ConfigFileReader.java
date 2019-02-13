package dataproviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
	private Properties properties;
	//private final String propertyFilePath= "/home/brk/BDDWS/ranger/configs/configurations.properties";
	private final String propertyFilePath= System.getProperty("user.dir")+"/configs/configurations.properties";
	
	//*************************************************
	private static ConfigFileReader configFileReader;
	public static ConfigFileReader getConfigFileReader() {		
		
		if (configFileReader != null) {
			 return configFileReader; 	
		}else {
			configFileReader = new ConfigFileReader();
			return configFileReader;
		}
	        
	}
	//*************************************************
	
	
	protected ConfigFileReader(){
		System.out.println("Into Config file reader");
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}		
	}
	
	public String getDriverPath() {
		String driverPath = System.getProperty("user.dir")+properties.getProperty("driverpath");
		if(driverPath!= null) return driverPath;
		else throw new RuntimeException("driverPath not specified in the Configuration.properties file.");		
	}
	
	public long getImplicitlyWait() {		
		String implicitlyWait = properties.getProperty("implicitlywait");
		if(implicitlyWait != null) return Long.parseLong(implicitlyWait);
		else throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");		
	}
	
	public String getApplicationUrl() {

		
		String url=null;
		try {
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			System.out.println(System.getenv("APP_URL"));
			url=System.getenv("APP_URL");
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			
		}
		catch(Exception e) {
			url = null;
		}
		if(url==null) {
			url = properties.getProperty("url");
		}
		
		

		if(url != null) return url;
		else throw new RuntimeException("url not specified in the Configuration.properties file.");
	}
	
	public String getSaskenApplicationUrl() {

		
		String url=null;
		try {
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			System.out.println(System.getenv("APP_URL"));
			url=System.getenv("APP_URL");
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			
		}
		catch(Exception e) {
			url = null;
		}
		if(url==null) {
			url = properties.getProperty("saskenURL");
		}
		
		

		if(url != null) return url;
		else throw new RuntimeException("saskenURL not specified in the Configuration.properties file.");
	}
	
	public String getApiGatewayUrl() {
		String url = properties.getProperty("apigateway");
		if(url != null) return url;
		else throw new RuntimeException("url not specified in the Configuration.properties file.");
	}
	
	public String getKeyStatus() {
		String url = properties.getProperty("Jsonkeys");
		if(url != null) return url;
		else throw new RuntimeException("url not specified in the Configuration.properties file.");
	}
	
	public String getUserName() {
		String username = properties.getProperty("username");
		if(username != null) return username;
		else throw new RuntimeException("username not specified in the Configuration.properties file.");
	}
	
	public String getPassword() {
		String password = properties.getProperty("password");
		if(password != null) return password;
		else throw new RuntimeException("password not specified in the Configuration.properties file.");
	}
	
	public int getWebDriverWait() {
		String webdriverwait = properties.getProperty("webdriverwait");
		if(webdriverwait != null) return Integer.parseInt(webdriverwait);
		else throw new RuntimeException("webdriverwait not specified in the Configuration.properties file.");
	}
	
	public long getPause() {
		String pause = properties.getProperty("pause");
		if(pause != null) return Long.parseLong(pause);
		else throw new RuntimeException("pause not specified in the Configuration.properties file.");
	}
	
	
	public long getPageLoadPause() {
		String pageloadpause = properties.getProperty("pageloadpause");
		if(pageloadpause != null) return Long.parseLong(pageloadpause);
		else throw new RuntimeException("pageloadpause not specified in the Configuration.properties file.");
	}
	
	public String getTemplatePath(){
        String templatePath = System.getProperty("user.dir")+properties.getProperty("templateloc");
        if(templatePath!= null) return templatePath;
        else throw new RuntimeException("templatePath not specified in the Configuration.properties file.");        
    }
	
	public String getBrowser() {
		String browserName = properties.getProperty("browser");
		if(browserName != null) return browserName;
		else throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : " + browserName);
	}
	
	public Boolean getBrowserWindowSize() {
		String windowSize = properties.getProperty("windowMaximize");
		if(windowSize != null) return Boolean.valueOf(windowSize);
		return true;
	}
	
	public String getReportConfigPath(){
		String reportConfigPath = properties.getProperty("reportConfigPath");
		if(reportConfigPath!= null) return reportConfigPath;
		else throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}
	
	public String getObjectRepoPath(){
		String objectConfigPath = properties.getProperty("ObjectRepoPath");
		if(objectConfigPath!= null) return objectConfigPath;
		else throw new RuntimeException("Object Repo Path not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}
	
	
	public String getDocumentType(){
		String template = System.getProperty("user.dir")+properties.getProperty("template");
		if(template!= null) return template;
		else throw new RuntimeException("template is not specified in the Configuration.properties file.");		
	}
	
	//*****************************************************************************
	public String getMySqlDriver(){
		String mysqldriver = properties.getProperty("mysqldriver");
		if(mysqldriver!= null) return mysqldriver;
		else throw new RuntimeException("mysqldriver is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}
	
	public String getMySqlHost(){
		String mysqlhost = properties.getProperty("mysqlhost");
		if(mysqlhost!= null) return mysqlhost;
		else throw new RuntimeException("mysqlhost is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}
	
	
	public String getMySqlPort(){
		String mysqlport = properties.getProperty("mysqlport");
		if(mysqlport!= null) return mysqlport;
		else throw new RuntimeException("mysqlport is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}
	
	
	public String getMySqlDatabase(){
		String mysqldatabase = properties.getProperty("mysqldatabase");
		if(mysqldatabase!= null) return mysqldatabase;
		else throw new RuntimeException("mysqldatabase is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}
	
	
	public String getMySqlUser(){
		String mysqluser = properties.getProperty("mysqluser");
		if(mysqluser!= null) return mysqluser;
		else throw new RuntimeException("mysqluser is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}
	
	
	
	public String getMySqlPassword(){
		String mysqlpassword = properties.getProperty("mysqlpassword");
		if(mysqlpassword!= null) return mysqlpassword;
		else throw new RuntimeException("mysqlpassword is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}
	
	public String getBaseUri(){
		String baseuri = properties.getProperty("baseuri");
		if(baseuri!= null) return baseuri;
		else throw new RuntimeException("baseuri is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}
	
	public String getJsonFolder(){
		String jsonfolder = properties.getProperty("jsonfolder");
		if(jsonfolder!= null) return jsonfolder;
		else throw new RuntimeException("jsonfolder is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}	
	
	
	public String getExcelData(){
		String exceldata = properties.getProperty("exceldata");
		if(exceldata!= null) return exceldata;
		else throw new RuntimeException("exceldata is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}


	public String getSqlServerHost() {
		String sqlserverhost = properties.getProperty("sqlserverhost");
		if(sqlserverhost!= null) return sqlserverhost;
		else throw new RuntimeException("sqlserverhost is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}


	public String getSqlServerPort() {
		String sqlserverport = properties.getProperty("sqlserverport");
		if(sqlserverport!= null) return sqlserverport;
		else throw new RuntimeException("sqlserverport is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}


	public String getSqlServerDatabase() {
		String sqlserverdatabase = properties.getProperty("sqlserverdatabase");
		if(sqlserverdatabase!= null) return sqlserverdatabase;
		else throw new RuntimeException("sqlserverdatabase is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}


	public String getSqlUser() {
		String sqluser = properties.getProperty("sqluser");
		if(sqluser!= null) return sqluser;
		else throw new RuntimeException("sqluser is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}


	public String getSqlPassword() {
		String sqlpassword = properties.getProperty("sqlpassword");
		if(sqlpassword!= null) return sqlpassword;
		else throw new RuntimeException("sqlpassword is not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}

	
	
}
	
	
 


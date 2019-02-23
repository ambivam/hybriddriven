package dataproviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import utilities.Log;

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


	public String getSqlServerDriver() {
		String sqlserverdriver = properties.getProperty("sqlserverdriver");
		if(sqlserverdriver!= null) 
		{
			Log.info("SQLServerDriver property from properties files is :"+sqlserverdriver);
			return sqlserverdriver;
		}
		else 
		{ 
			Log.error("SQLServerDriver property in properties file is not found");
			return (sqlserverdriver = null);
		}
	}


	public String getMySqlServer() {
		String mysqlserver = properties.getProperty("mysqlserver");
		if(mysqlserver!= null) 
		{
			Log.info("mysqlserver property from properties files is : "+mysqlserver);
			return mysqlserver;
		}
		else 
		{ 
			Log.error("mysqlserver property in properties file is not found");
			return (mysqlserver = null);
		}
	}


	public String getSqlServer() {
		String sqlserver = properties.getProperty("sqlserver");
		if(sqlserver!= null) 
		{
			Log.info("sqlserver property from properties files is : "+sqlserver);
			return sqlserver;
		}
		else 
		{ 
			Log.error("sqlserver property in properties file is not found");
			return (sqlserver = null);
		}
	}
	
	
	public String getTauriEnvironmentFile() {
		String TauriEnvironmentFile = properties.getProperty("TAURI_ENVIRONMENT_FILE");
		if(TauriEnvironmentFile!= null) 
		{
			Log.info("TauriEnvironmentFile property from properties files is : "+TauriEnvironmentFile);
			return TauriEnvironmentFile;
		}
		else 
		{ 
			Log.error("TauriEnvironmentFile property in properties file is not found");
			return (TauriEnvironmentFile = null);
		}
	}
	
	
	
	public String getGrantType() {
		String GrantType = properties.getProperty("GRANT_TYPE");
		if(GrantType!= null) 
		{
			Log.info("TauriEnvironmentFile property from properties files is : "+GrantType);
			return GrantType;
		}
		else 
		{ 
			Log.error("GrantType property in properties file is not found");
			return (GrantType = null);
		}
	}
	
	
	public String getAssetResource() {
		String AssetResource = properties.getProperty("ASSET_RESOURCE");
		if(AssetResource!= null) 
		{
			Log.info("AssetResource property from properties files is : "+AssetResource);
			return AssetResource;
		}
		else 
		{ 
			Log.error("AssetResource property in properties file is not found");
			return (AssetResource = null);
		}
	}
	
	
	public String getClientResource() {
		String ClientResource = properties.getProperty("CLIENT_RESOURCE");
		if(ClientResource!= null) 
		{
			Log.info("ClientResource property from properties files is : "+ClientResource);
			return ClientResource;
		}
		else 
		{ 
			Log.error("ClientResource property in properties file is not found");
			return (ClientResource = null);
		}
	}
	
	
	public String getAuthorizationContentType() {
		String AuthorizationContentType = properties.getProperty("AUTHORIZATION_CONTENT_TYPE");
		if(AuthorizationContentType!= null) 
		{
			Log.info("AuthorizationContentType property from properties files is : "+AuthorizationContentType);
			return AuthorizationContentType;
		}
		else 
		{ 
			Log.error("AuthorizationContentType property in properties file is not found");
			return (AuthorizationContentType = null);
		}
	}
	
	
	public String getLoginMicrosoft() {
		String LoginMicrosoft = properties.getProperty("LOGIN_MICROSOFT");
		if(LoginMicrosoft!= null) 
		{
			Log.info("LoginMicrosoft property from properties files is : "+LoginMicrosoft);
			return LoginMicrosoft;
		}
		else 
		{ 
			Log.error("LoginMicrosoft property in properties file is not found");
			return (LoginMicrosoft = null);
		}
	}


	public String getDeviceResource() {
		String DeviceResource = properties.getProperty("DEVICE_RESOURCE");
		if(DeviceResource!= null) 
		{
			Log.info("DeviceResource property from properties files is : "+DeviceResource);
			return DeviceResource;
		}
		else 
		{ 
			Log.error("DeviceResource property in properties file is not found");
			return (DeviceResource = null);
		}
	}
	
	
	public String getBaseUrl() {
		String BaseUrl = properties.getProperty("BASE_URL");
		if(BaseUrl!= null) 
		{
			Log.info("BaseUrl property from properties files is : "+BaseUrl);
			return BaseUrl;
		}
		else 
		{ 
			Log.error("BaseUrl property in properties file is not found");
			return (BaseUrl = null);
		}
	}
	
	
	public String getDeviceUrl() {
		String DeviceUrl = properties.getProperty("DEVICE_URL");
		if(DeviceUrl!= null) 
		{
			Log.info("DeviceUrl property from properties files is : "+DeviceUrl);
			return DeviceUrl;
		}
		else 
		{ 
			Log.error("DeviceUrl property in properties file is not found");
			return (DeviceUrl = null);
		}
	}
	
	
	public String getDevices() {
		String Devices = properties.getProperty("DEVICES");
		if(Devices!= null) 
		{
			Log.info("Devices property from properties files is : "+Devices);
			return Devices;
		}
		else 
		{ 
			Log.error("DeviceUrl property in properties file is not found");
			return (Devices = null);
		}
	}
	
	
	public String getCookie() {
		String cookie = properties.getProperty("COOKIE");
		if(cookie!= null) 
		{
			Log.info("cookie property from properties files is : "+cookie);
			return cookie;
		}
		else 
		{ 
			Log.error("cookie property in properties file is not found");
			return (cookie = null);
		}
	}
	
	
	public String getContentType() {
		String ContentType = properties.getProperty("CONTENT_TYPE");
		if(ContentType!= null) 
		{
			Log.info("ContentType property from properties files is : "+ContentType);
			return ContentType;
		}
		else 
		{ 
			Log.error("ContentType property in properties file is not found");
			return (ContentType = null);
		}
	}

	public String getAssetUrl() {
		String AssetUrl = properties.getProperty("ASSET_URL");
		if(AssetUrl!= null) 
		{
			Log.info("AssetUrl property from properties files is : "+AssetUrl);
			return AssetUrl;
		}
		else 
		{ 
			Log.error("AssetUrl property in properties file is not found");
			return (AssetUrl = null);
		}
	}
	
	
	public String getAssets() {
		String Assets = properties.getProperty("ASSETS");
		if(Assets!= null) 
		{
			Log.info("Assets property from properties files is : "+Assets);
			return Assets;
		}
		else 
		{ 
			Log.error("Assets property in properties file is not found");
			return (Assets = null);
		}
	}
	
}
	
	
 


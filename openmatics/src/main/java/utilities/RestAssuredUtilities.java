package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dataproviders.ConfigFileReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredUtilities {
	
	private static String appUri = ConfigFileReader.getConfigFileReader().getBaseUri();
	
	static public Response getResponse(String endpoint) {
		
		RestAssured.baseURI = appUri;
		System.out.println("appuri is : "+appUri);
		System.out.println("uri is : "+endpoint);
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.contentType(ContentType.JSON);
		Response response = httpRequest.request(Method.GET, endpoint);
		return response;
		
	}
	
	static public Response post(String endpoint,String fileName) {
		RestAssured.baseURI = appUri;
		System.out.println("appuri is : "+appUri);
		System.out.println("uri is : "+endpoint);
		RequestSpecification httpRequest = RestAssured.given();
		
		Header header1 = new Header("Content-Type", "application/json");
		httpRequest.header(header1);
		
		httpRequest.contentType(ContentType.JSON);
		
		String path = System.getProperty("user.dir")+ConfigFileReader.getConfigFileReader().getJsonFolder()+fileName;
		System.out.println("The path is : "+path);
				
		File file = new File(path);
		
		httpRequest.body(file);
		
		Response response = httpRequest.request(Method.POST, endpoint);
		return response;
		
	}
	
	@SuppressWarnings("unchecked")
	static public JSONObject readJSONFile(String fileName) {
		String path = System.getProperty("user.dir")+ConfigFileReader.getConfigFileReader().getJsonFolder()+fileName;
		System.out.println("The path is : "+path);
		File file = new File(path);
		JSONObject jsonobj = null;
		JSONParser jsonparser = new JSONParser();
		try {
			jsonobj = (JSONObject)jsonparser.parse(new FileReader(file));	
			
			//jsonobj.put("name", "Bhavya");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return jsonobj;
	}
	
	static public int getStatusCode(Response response) {		
		return response.getStatusCode();				
	}
	
	static public String getResponseJsonContent(Response response,String jsonPath) {
		/*
		 * JsonPath jpe = response.jsonPath();
		 * System.out.println(jpe.get("data.first_name").toString());
		 * System.out.println(jpe.get("data").toString());
		 */
		JsonPath jpe = response.jsonPath();
		return jpe.get(jsonPath).toString();
		
	}
	
	
	@SuppressWarnings("deprecation")
	static public Response getResponseTemp() {
		
		//**************************Getting Authorization nginx**************************************
		
		appUri = "https://login.microsoftonline.com/968ff878-be61-4cc1-8139-d08e4a9f8ec8/oauth2/token";
		RestAssured.baseURI = appUri;
		System.out.println("appuri is : "+appUri);
		//System.out.println("uri is : "+endpoint);
		RequestSpecification httpRequest = RestAssured.given();
		
		httpRequest.formParam("grant_type","client_credentials");
		httpRequest.formParam("client_id","eefa91f7-4b7c-43aa-9a45-b3ac45f91a83");
		httpRequest.formParam("client_secret","MGNjZTEyYzMtMjBiMC00Mzk3LTk3YmYtNzJmMDU2NWM5YTgy");
		httpRequest.formParam("resource","http://client-management-service");
		httpRequest.header("Content-Type", "application/x-www-form-urlencoded");
		Response response = httpRequest.request(Method.POST);
		
		String access_token = response.jsonPath().getJsonObject("access_token");
		System.out.println("The access token is : "+access_token);
		String token_type = response.jsonPath().getJsonObject("token_type");
		
		//***************************Getting Users****************************************************
		
		String appUsersURI = "https://backend-tauri.openmatics.com/global/client-management-service/users/";
		RestAssured.baseURI = appUsersURI;
		RequestSpecification httpRequestUsers = RestAssured.given();
		
		
		httpRequestUsers.header("Authorization", "Bearer "+access_token);
		//httpRequestUsers.header("Content-Type", ContentType.JSON);
		//httpRequestUsers.header("Accept", ContentType.JSON);
		//httpRequestUsers.header("authorization","Bearer\n"+access_token);
		//httpRequestUsers.auth().oauth2(access_token);
		//httpRequestUsers.header("authorization",access_token);
		httpRequestUsers.header("extendedinfo", "true");
		
		
		Response responseUsers = httpRequest.request(Method.GET);	
		
		
		return responseUsers;
		
	}
	
	
	static public Response ResponseTemp() {
		
		//**************************Getting Authorization nginx**************************************
		
		appUri = "https://login.microsoftonline.com/968ff878-be61-4cc1-8139-d08e4a9f8ec8/oauth2/token";
		RestAssured.baseURI = appUri;
		System.out.println("appuri is : "+appUri);
		//System.out.println("uri is : "+endpoint);
		RequestSpecification httpRequest = RestAssured.given();
		
		httpRequest.formParam("grant_type","client_credentials");
		httpRequest.formParam("client_id","692f1b30-3776-4b49-997b-34d0b97ca7a6");
		httpRequest.formParam("client_secret","ZWViZGRkMGEtZjBiMC00ZWNhLTk5NTUtODU1MDUwZTAxMzY4");
		httpRequest.formParam("resource","692f1b30-3776-4b49-997b-34d0b97ca7a6");
		httpRequest.header("Content-Type", "application/x-www-form-urlencoded");
		Response response = httpRequest.request(Method.POST);
		//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
		Map<String,String> tempMap = response.getCookies();
		
		Set<String> tempSet = tempMap.keySet();
		
		for(String i : tempSet) {
			System.out.println(" HHHHHHHHHHHHHHHHHHHHHHHHHHH key is "+i+":" +tempMap.get(i));
		}
		
		System.out.println("Cookies received from authorization are : "+response.getCookies());
		System.out.println("Cookies received from authorization are : "+response.getContentType());
		//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
		
		String access_token = response.jsonPath().getJsonObject("access_token");
		System.out.println("The access token is : "+access_token);
		String token_type = response.jsonPath().getJsonObject("token_type");
		
		//***************************Getting Users****************************************************
		
		String appUsersURI = "https://backend-tauri.openmatics.com/19309c32-1769-42ba-9829-d18d5e9e072d/authorization-provider-service/roles/";
		RestAssured.baseURI = appUsersURI;
		RequestSpecification httpRequestUsers = RestAssured.given();
		
		
//		JSONObject requestParams = new JSONObject();
//		requestParams.put("displayName", "TempSushilKumar"); 
//		requestParams.put("enabled", "true");
//		 
//		requestParams.put("email", "michal.lepsi@zf.com");
//		requestParams.put("supporterFlag", "false");
//		requestParams.put("emailSent", "false");
		
		
		httpRequestUsers.header("Authorization", "Bearer "+access_token);
		
		
		//httpRequestUsers.header("Content-Type", "application/json");
		//httpRequestUsers.header("Accept","*/*");
		//httpRequestUsers.header("User-Agent","insomnia/6.3.2");
		//httpRequestUsers.cookie("JSESSIONID","EC7892DF7F8005A6D56F13E968B39E19");
		//httpRequestUsers.cookie("f9f67cf3e3a0a1c180f2633967f4fd09","99280e4517376aedea090928d89bd312");
		
		httpRequestUsers.header("cookie", "JSESSIONID=EC7892DF7F8005A6D56F13E968B39E19;f9f67cf3e3a0a1c180f2633967f4fd09=99280e4517376aedea090928d89bd312");
		//httpRequestUsers.header("f9f67cf3e3a0a1c180f2633967f4fd09","99280e4517376aedea090928d89bd312");
		
		//httpRequestUsers.header("Accept", ContentType.JSON);
		//httpRequestUsers.header("authorization","Bearer\n"+access_token);
		//httpRequestUsers.auth().oauth2(access_token);
		//httpRequestUsers.header("authorization",access_token);
		//httpRequestUsers.header("extendedinfo", "true");
		
		//System.out.println("The json string is : "+requestParams.toJSONString());
		
		//httpRequestUsers.body(requestParams.toJSONString());
		
		
		Response responseUsers = httpRequestUsers.request(Method.GET);	
		
		
		return responseUsers;
		
	}
	
	static public Response ResponseCreateRole() {
		
		appUri = "https://login.microsoftonline.com/968ff878-be61-4cc1-8139-d08e4a9f8ec8/oauth2/token";
		RestAssured.baseURI = appUri;
		System.out.println("appuri is : "+appUri);
		//System.out.println("uri is : "+endpoint);
		RequestSpecification httpRequest = RestAssured.given();
		
		httpRequest.formParam("grant_type","client_credentials");
		httpRequest.formParam("client_id","692f1b30-3776-4b49-997b-34d0b97ca7a6");
		httpRequest.formParam("client_secret","ZWViZGRkMGEtZjBiMC00ZWNhLTk5NTUtODU1MDUwZTAxMzY4");
		httpRequest.formParam("resource","692f1b30-3776-4b49-997b-34d0b97ca7a6");
		httpRequest.header("Content-Type", "application/x-www-form-urlencoded");
		Response response = httpRequest.request(Method.POST);
		String access_token = response.jsonPath().getJsonObject("access_token");
		
		//***************************Creating Roles****************************************************
		
		JSONObject requestParams = new JSONObject();
		requestParams.put("code", "TEST_ROLE666_Auto"); 
		requestParams.put("name", "TEST_ROLE666_Auto_name");
		 
		requestParams.put("description","TEST_ROLE666_Auto_name Description");
		requestParams.put("groups", "null");
		requestParams.put("users", "null");
		requestParams.put("privileges", "[]");
		
		
		JSONObject createRoleJson = readJSONFile("CreateRole.json");
		
		//********************************************************************************************
		String appUsersURI = "https://backend-tauri.openmatics.com/19309c32-1769-42ba-9829-d18d5e9e072d/authorization-provider-service/roles/";
		RestAssured.baseURI = appUsersURI;
		RequestSpecification httpRequestCreateRoles = RestAssured.given();
		
		httpRequestCreateRoles.header("Authorization", "Bearer "+access_token);
		httpRequestCreateRoles.header("cookie", "JSESSIONID=EC7892DF7F8005A6D56F13E968B39E19;f9f67cf3e3a0a1c180f2633967f4fd09=99280e4517376aedea090928d89bd312");
		httpRequestCreateRoles.header("Content-Type", "application/json");
		httpRequestCreateRoles.body(createRoleJson.toJSONString());
		
		Response responseRoles = httpRequestCreateRoles.request(Method.POST);	
		
		
		return responseRoles;
		
	}
	
	
	static public String getAssettoken_(String authorizationURL) {
		Response response = null;
		String access_token = null;
		RestAssured.baseURI = authorizationURL;
		RequestSpecification httpRequest = RestAssured.given();
		
		String grant_type = ConfigFileReader.getConfigFileReader().getGrantType();
		String client_id = readJSONFile(ConfigFileReader.getConfigFileReader().getTauriEnvironmentFile()).get("asset_add_id").toString();
		String client_secret = readJSONFile(ConfigFileReader.getConfigFileReader().getTauriEnvironmentFile()).get("asset_add_secret").toString();
		String resource = ConfigFileReader.getConfigFileReader().getAssetResource();	
		String authorizationContenTtype = ConfigFileReader.getConfigFileReader().getAuthorizationContentType();
						
		httpRequest.formParam("grant_type",grant_type);
		httpRequest.formParam("client_id",client_id);
		httpRequest.formParam("client_secret",client_secret);
		httpRequest.formParam("resource",resource);
		httpRequest.header("Content-Type", authorizationContenTtype);
		response = httpRequest.request(Method.POST);
		System.out.println(response.getStatusCode());
		access_token = response.jsonPath().getJsonObject("access_token");		
		return access_token;
	}
	
	static public String getClienttoken_(String authorizationURL) {
		Response response = null;
		String access_token = null;
		RestAssured.baseURI = authorizationURL;
		RequestSpecification httpRequest = RestAssured.given();
		
		String grant_type = ConfigFileReader.getConfigFileReader().getGrantType();
		String client_id = readJSONFile(ConfigFileReader.getConfigFileReader().getTauriEnvironmentFile()).get("client_aad_id").toString();
		String client_secret = readJSONFile(ConfigFileReader.getConfigFileReader().getTauriEnvironmentFile()).get("client_aad_sercet").toString();
		String resource = ConfigFileReader.getConfigFileReader().getClientResource();	
		String authorizationContenTtype = ConfigFileReader.getConfigFileReader().getAuthorizationContentType();
						
		httpRequest.formParam("grant_type",grant_type);
		httpRequest.formParam("client_id",client_id);
		httpRequest.formParam("client_secret",client_secret);
		httpRequest.formParam("resource",resource);
		httpRequest.header("Content-Type", authorizationContenTtype);
		response = httpRequest.request(Method.POST);
		System.out.println(response.getStatusCode());
		access_token = response.jsonPath().getJsonObject("access_token");		
		return access_token;
	}
	
	
	
	static public String getAssettoken(String authorizationURL,String serviceName) {		
				
		Map<String,String> form = new HashMap<String,String>();
		form.put("auth_url", authorizationURL);
		form.put("grant_type", ConfigFileReader.getConfigFileReader().getGrantType());
		form.put("client_id", readJSONFile(ConfigFileReader.getConfigFileReader().getTauriEnvironmentFile()).get("asset_add_id").toString());
		form.put("client_secret", readJSONFile(ConfigFileReader.getConfigFileReader().getTauriEnvironmentFile()).get("asset_add_secret").toString());
		form.put("resource", ConfigFileReader.getConfigFileReader().getAssetResource());
		form.put("Content-Type", ConfigFileReader.getConfigFileReader().getAuthorizationContentType());		
		return getToken(form,serviceName);
		
	}
	
	static public String getDevicetoken(String authorizationURL,String serviceName) {
				
		Map<String,String> form = new HashMap<String,String>();
		form.put("auth_url", authorizationURL);
		form.put("grant_type", ConfigFileReader.getConfigFileReader().getGrantType());
		form.put("client_id", readJSONFile(ConfigFileReader.getConfigFileReader().getTauriEnvironmentFile()).get("device_add_id").toString());
		form.put("client_secret", readJSONFile(ConfigFileReader.getConfigFileReader().getTauriEnvironmentFile()).get("device_add_secret").toString());
		form.put("resource", ConfigFileReader.getConfigFileReader().getDeviceResource());
		form.put("Content-Type", ConfigFileReader.getConfigFileReader().getAuthorizationContentType());		
		return getToken(form,serviceName);		
	}
	
	static public String getClienttoken(String authorizationURL,String serviceName) {		
				
		Map<String,String> form = new HashMap<String,String>();
		form.put("auth_url", authorizationURL);
		form.put("grant_type", ConfigFileReader.getConfigFileReader().getGrantType());
		form.put("client_id", readJSONFile(ConfigFileReader.getConfigFileReader().getTauriEnvironmentFile()).get("client_aad_id").toString());
		form.put("client_secret", readJSONFile(ConfigFileReader.getConfigFileReader().getTauriEnvironmentFile()).get("client_aad_sercet").toString());
		form.put("resource", ConfigFileReader.getConfigFileReader().getClientResource());
		form.put("Content-Type", ConfigFileReader.getConfigFileReader().getAuthorizationContentType());
		return getToken(form,serviceName);
		
	}
	
	static public String getUsertoken(String authorizationURL,String serviceName) {		
		
		Map<String,String> form = new HashMap<String,String>();
		form.put("auth_url", authorizationURL);
		form.put("grant_type", ConfigFileReader.getConfigFileReader().getGrantType());
		form.put("client_id", readJSONFile(ConfigFileReader.getConfigFileReader().getTauriEnvironmentFile()).get("client_aad_id").toString());
		form.put("client_secret", readJSONFile(ConfigFileReader.getConfigFileReader().getTauriEnvironmentFile()).get("client_aad_sercet").toString());
		form.put("resource", ConfigFileReader.getConfigFileReader().getClientResource());
		form.put("Content-Type", ConfigFileReader.getConfigFileReader().getAuthorizationContentType());
		return getToken(form,serviceName);
		
	}
	
	static public RequestSpecification setFormParameters(RequestSpecification httpRequest,Map<String,String> form) {
		httpRequest.formParam("grant_type",form.get("grant_type"));
		httpRequest.formParam("client_id",form.get("client_id"));
		httpRequest.formParam("client_secret",form.get("client_secret"));
		httpRequest.formParam("resource",form.get("resource"));
		httpRequest.header("Content-Type", form.get("Content-Type"));
		
		return httpRequest;
	}
	
	static public String getToken(Map<String,String> form,String servicename) {
		Response response = null;
		String access_token = null;
		RestAssured.baseURI = form.get("auth_url");
		RequestSpecification httpRequest = RestAssured.given();
		
		switch(servicename) {
		case "device":
			setFormParameters(httpRequest,form);
			break;	
		case "asset":
			setFormParameters(httpRequest,form);
			break;
		case "client":
			setFormParameters(httpRequest,form);
			break;		
		case "user":
			setFormParameters(httpRequest,form);
			break;		
		}
		
		response = httpRequest.request(Method.POST);	
				
		System.out.println(response.getCookies());
		access_token = response.jsonPath().getJsonObject("access_token");		
		return access_token;
		
	}	
	
	
	static public String getAuthorizationToken(String serviceName) {
		String base_url = ConfigFileReader.getConfigFileReader().getLoginMicrosoft();
		String aad_tenant = readJSONFile(ConfigFileReader.getConfigFileReader().getTauriEnvironmentFile()).get("aad_tenant").toString();
		String tauri_authorization_url = "https://"+base_url+"/"+aad_tenant+"/oauth2/token";
		
		System.out.println("The authorization url is : "+tauri_authorization_url);
		String token = null; 
		
		switch(serviceName) {
		case "asset":			
			token = getAssettoken(tauri_authorization_url,serviceName);
			break;
		case "client":			
			token = getClienttoken(tauri_authorization_url,serviceName);
			break;
		case "device":
			token = getDevicetoken(tauri_authorization_url,serviceName);
			break;
		case "user":
			token = getUsertoken(tauri_authorization_url,serviceName);
			break;
			
		}
		
		//return token.jsonPath().getJsonObject("access_token");
		return token;
	}
	
	
	static public JsonPath Get(String url,String token) {
		
		RestAssured.baseURI = url;
		RequestSpecification httpRequest = RestAssured.given();
		
		//***************************************************************************
		httpRequest.header("authorization", "Bearer "+token);
		Response getResponse = httpRequest.request(Method.GET);
		
		return getResponse.jsonPath();
		//################################################################################				
		
		/*
		 * Map<String,String> tempMap = getResponse.getCookies(); Set<String> tempSet =
		 * tempMap.keySet(); StringBuilder crook = new StringBuilder(); for(String i :
		 * tempSet) { crook.append(i+"="+tempMap.get(i)+";"); } String actual =
		 * crook.toString(); actual = actual.substring(0, actual.length() - 1);
		 * System.out.println("The crook is : "+actual); RequestSpecification
		 * httpRequest2 = RestAssured.given(); httpRequest2.header("authorization",
		 * "Bearer "+token); httpRequest2.header("cookie", actual); Response
		 * getResponse2 = httpRequest2.request(Method.GET);
		 * System.out.println("hahahahaha is : "+getResponse2.getBody().asString());
		 * return getResponse2.jsonPath().getJsonObject("$");
		 */
		//################################################################################
		
		
		//***************************************************************************
		
		
	}
	
	static public JSONObject CreateAsset()	{
		
		JSONObject CreateAssetTemplate = readJSONFile(ConfigFileReader.getConfigFileReader().getCreateAssetJson());
		JSONObject CreateAssetData = readJSONFile(ConfigFileReader.getConfigFileReader().getCreateAssetData());	
		
		CreateAssetTemplate.put("name",CreateAssetData.get("name").toString() + getRandomNumber());
		CreateAssetTemplate.put("description",CreateAssetData.get("description").toString() + getRandomNumber());		
		List<String> temp = (List<String>) CreateAssetTemplate.get("clientIdList");
		
		String updatedListVal = (String) CreateAssetData.get("clientIdList");
		System.out.println("The list value is : "+temp.get(0));
		temp.set(0, updatedListVal);
		CreateAssetTemplate.put("clientIdList",temp );
		
		return CreateAssetTemplate;
	}
	
	static public JSONObject CreateUser()	{
		
		JSONObject CreateUserTemplate = readJSONFile(ConfigFileReader.getConfigFileReader().getCreateUserJson());
		JSONObject CreateUserData = readJSONFile(ConfigFileReader.getConfigFileReader().getCreateUserData());	
		CreateUserTemplate.put("displayName",CreateUserData.get("displayName").toString() + getRandomNumber());
		System.out.println(" Createeeeeee userrrrrrrrrrrr templateeeeeee is :"+CreateUserTemplate.toJSONString());
		return CreateUserTemplate;
	}
	
	static public long getRandomNumber() {
		Random r = new Random();
		int low = 10;
		int high = 100;
		int result = r.nextInt(high-low) + low;
		return result;
	}
	
	static public String Post(String url,String token,String servicename) {
		
		RestAssured.baseURI = url;
		RequestSpecification httpRequest = RestAssured.given();
		JSONObject JsonService = new JSONObject();
		//*****************************************************************************
		switch(servicename) {
		case "asset":
			
			JsonService = CreateAsset();
			break;
		case "user":
			JsonService = CreateUser();
			break;
		}		
		//*****************************************************************************
		httpRequest.header("authorization", "Bearer "+token);
		httpRequest.header("Content-Type", "application/json");		
		httpRequest.body(JsonService.toJSONString());		
		Response getResponse = httpRequest.request(Method.POST);
		return getResponse.body().asString();
		//################################################################################				
		
		/*
		 * Map<String,String> tempMap = getResponse.getCookies(); Set<String> tempSet =
		 * tempMap.keySet(); StringBuilder crook = new StringBuilder(); for(String i :
		 * tempSet) { crook.append(i+"="+tempMap.get(i)+";"); } String actual =
		 * crook.toString(); actual = actual.substring(0, actual.length() - 1);
		 * System.out.println("The crook is : "+actual); RequestSpecification
		 * httpRequest2 = RestAssured.given(); httpRequest2.header("authorization",
		 * "Bearer "+token); httpRequest2.header("cookie", actual); Response
		 * getResponse2 = httpRequest2.request(Method.GET);
		 * System.out.println("Duplicate is : "+getResponse2.getBody().asString());
		 * return getResponse2.jsonPath().getJsonObject("$");
		 */
		//################################################################################
		
		
		//***************************************************************************
		
		
	}
	
	static public String Delete(String url,String token) {
			String deleteMessage = null;
			RestAssured.baseURI = url;
			RequestSpecification httpRequest = RestAssured.given();
			httpRequest.header("authorization", "Bearer "+token);
			httpRequest.header("Content-Type", "application/json");		
			Response getResponse = httpRequest.request(Method.DELETE);
			System.out.println("The delete message body is : "+getResponse.getBody().asString());
			System.out.println("The delete message is : "+getResponse.getStatusLine() +" **** "+getResponse.statusCode());
			
			deleteMessage = "The deleted service name is "+getResponse.jsonPath().get("name") +" and the id is " + getResponse.jsonPath().get("id");
			
			return deleteMessage;
	}
	
	
	
	static public JsonPath GetServices(String servicename) {
		
		String base_url = ConfigFileReader.getConfigFileReader().getBaseUrl();
		String token = getAuthorizationToken(servicename);
		
		JsonPath getJSON = null;
		
		switch(servicename) {
			case "device":
				String DevicesUrl = ConfigFileReader.getConfigFileReader().getDeviceUrl();
				String Devices = ConfigFileReader.getConfigFileReader().getDevices();
				String urlDevice = base_url +"/"+DevicesUrl+"/"+Devices+"/";
				getJSON = Get(urlDevice,token);
				break;
			case "asset":
				String AssetsUrl = ConfigFileReader.getConfigFileReader().getAssetUrl();
				String Assets = ConfigFileReader.getConfigFileReader().getAssets();
				String urlAsset = base_url +"/"+AssetsUrl+"/"+Assets+"/";
				System.out.println("The asset url is :"+urlAsset);
				getJSON = Get(urlAsset,token);
				break;
			case "client":
				String ClientsUrl = ConfigFileReader.getConfigFileReader().getClientsUrl();
				String Clients = ConfigFileReader.getConfigFileReader().getClients();
				String urlClient = base_url +"/"+ClientsUrl+"/"+Clients;
				System.out.println("The asset url is :"+urlClient);
				getJSON = Get(urlClient,token);
				break;
				
		}
		
		return getJSON;
	}
	
	static public String CreateServices(String servicename) {
		String CreateServiceString = null;
		String base_url = ConfigFileReader.getConfigFileReader().getBaseUrl();
		String token = getAuthorizationToken(servicename);
		switch(servicename) {
		case "asset":
			String AssetsUrl = ConfigFileReader.getConfigFileReader().getAssetUrl();
			String Assets = ConfigFileReader.getConfigFileReader().getAssets();
			String urlAsset = base_url +"/"+AssetsUrl+"/"+Assets;
			System.out.println("The asset url is :"+urlAsset);
			CreateServiceString =  Post(urlAsset,token,servicename);
			break;
		case "user":
			String ClientUrl = ConfigFileReader.getConfigFileReader().getClientsUrl();
			String Users = ConfigFileReader.getConfigFileReader().getUsers();
			String urlUser = base_url +"/"+ClientUrl+"/"+Users+"/";
			System.out.println("The Users url is :"+urlUser);
			CreateServiceString =  Post(urlUser,token,servicename);
			break;
		}
		
		return CreateServiceString;
	}
	
	
	static public String DeleteServices(String servicename,String id) {
		String DeleteServiceString = null;
		String base_url = ConfigFileReader.getConfigFileReader().getBaseUrl();
		String token = getAuthorizationToken(servicename);
		switch(servicename) {
		case "asset":
			String AssetsUrl = ConfigFileReader.getConfigFileReader().getAssetUrl();
			String Assets = ConfigFileReader.getConfigFileReader().getAssets();
			String urlAssetDelete = base_url +"/"+AssetsUrl+"/"+Assets+"/"+id;
			System.out.println("The asset url is :"+urlAssetDelete);
			DeleteServiceString =  Delete(urlAssetDelete,token);
			break;
		case "user":
			String UsersUrl = ConfigFileReader.getConfigFileReader().getClientsUrl();
			String Users = ConfigFileReader.getConfigFileReader().getUsers();
			String urlUserDelete = base_url +"/"+UsersUrl+"/"+Users+"/"+id;
			System.out.println("The asset url is :"+urlUserDelete);
			DeleteServiceString =  Delete(urlUserDelete,token);
			break;
		}
		
		return DeleteServiceString;
	}
	
	
	
	
	public static void main(String a[]) {
		
		
		
		  System.out.println("The client token is : "+getAuthorizationToken("client"));
		  System.out.println("The asset token is : "+getAuthorizationToken("asset"));
		  
		  
		  System.out.println("The device token is : "+getAuthorizationToken("device"));
		 
		
		//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$ "+ResponseTemp());
		  
		 
		
		//System.out.println("JSONObject of device is "+GetServices("client").getJsonObject("address.street"));
		
		 String createUser =  CreateServices("user");
		System.out.println("Delete user services are : "+DeleteServices("user",createUser));
		
		  
		  
//	Response tempResult = RestAssuredUtilities.ResponseCreateRole();
//	System.out.println("The status code is :"+ tempResult.getStatusLine());
//	System.out.println("The response result is ##########: "+tempResult.getBody().asString());
	//System.out.println("The response result is : "+tempResult.getBody().toString().contains("login"));
	  
//	  System.out.println(RestAssuredUtilities.getStatusCode(RestAssuredUtilities.
//			  getResponseTemp("asset")));
//	  System.out.println("The response is : "+response);
//	  System.out.println(
//	  "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"
//	  );
	  
		/*
		 * System.out.println(RestAssuredUtilities.getStatusCode(RestAssuredUtilities.
		 * post("users","TempPost.json")));
		 * 
		 * 
		 * System.out.println("Get Json name From file is : "+readJSONFile(
		 * "TempPost.json").get("name"));
		 */
	  
	  }
	 

}

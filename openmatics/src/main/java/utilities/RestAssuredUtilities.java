package utilities;

import java.io.File;

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
	
	public static void main(String a[]) {		
		
		System.out.println(RestAssuredUtilities.getStatusCode(RestAssuredUtilities.getResponse("users/2")));
		
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		
		System.out.println(RestAssuredUtilities.getStatusCode(RestAssuredUtilities.post("users","TempPost.json")));
		
	}

}
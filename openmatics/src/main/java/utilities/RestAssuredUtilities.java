package utilities;

import dataproviders.ConfigFileReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredUtilities {
	
	private static String appUri = ConfigFileReader.getConfigFileReader().getBaseUri();
	
	static public Response getResponse(String uri) {
		
		RestAssured.baseURI = appUri;
		System.out.println("appuri is : "+appUri);
		System.out.println("uri is : "+uri);
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.contentType(ContentType.JSON);
		Response response = httpRequest.request(Method.GET, uri);
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
	}

}

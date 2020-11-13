package com.companyname.project.module1;

import java.util.Scanner;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.companyname.project.commonUtil.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC01_Get_SearchByCountryCode extends BaseTest{	
	public static String countryCode;

	@Test
	@SuppressWarnings("resource")
	public static void searchByCountrycodeTest() {
		int choice;
		logger.info("***********Started TC001_SearchByCountryCode************");
		try {
			do {
				System.out.println("1.col\n2.no \n3.ee \n4.Exit");
				System.out.println("Enter your choice of country code: ");
				choice = -1;
				Scanner sc = new Scanner(System.in);
				if(sc.hasNextInt() || sc.hasNextFloat()) {
					String value = sc.nextLine();
					float floatValue = Float.parseFloat(value);
					choice = (int)floatValue;
					
					switch(choice) {
					case 1: 
						countryCode = "col";
						System.out.println("Your selected country code is: "+countryCode);
						searchByCountryCode(countryCode);
						break;
					case 2: 
						countryCode = "no";
						System.out.println("Your selected country code is: "+countryCode);
						searchByCountryCode(countryCode);
						break;
					case 3: 
						countryCode = "ee";
						System.out.println("Your selected country code is: "+countryCode);
						searchByCountryCode(countryCode);
						break;
					case 4:            		
						System.out.println("You are going to exit, Program ends.");
						logger.info("***********End of TC001_SearchByCountryCode*********");
						System.exit(0);
						break;
					default:
						System.out.println("Please enter the valid selection.");
						break;            		
					}
				}else {
					System.out.println("Please enter the valid integer selection.");
				}
			}while(choice != 4);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void searchByCountryCode(String code) {
		RestAssured.baseURI = "https://restcountries.eu/rest/v2";
		httpRequest = RestAssured.given();		
		response = httpRequest.request(Method.GET, "/alpha/"+code);

		//Verify the response status code
		logger.info("Checking Response Status code...");
		Assert.assertEquals(response.getStatusCode(), 200, "Actual Status code is not matched with Expected.");

		//Verify the response body
		logger.info("checking Response Body...");
		String responseBody = response.getBody().asString();
		logger.info(responseBody);
		Assert.assertEquals(responseBody!=null, true);
		String actualCapital = response.jsonPath().getString("capital");
		String expectedCapital;
		if(countryCode.equals("col")) {
			expectedCapital = "Bogotá";
		}else if(countryCode.equals("no")) {
			expectedCapital = "Oslo";
		}else {
			expectedCapital = "Tallinn";
		}
		Assert.assertEquals(actualCapital, expectedCapital);

		//Verify the header content-type
		logger.info("checking header Content-Type...");
		String contentType = response.header("Content-Type");
		logger.info("response header ContentType is:" + contentType);
		Assert.assertEquals(contentType, "application/json;charset=utf-8");

		//Verify the header content-encoding
		logger.info("checking header Content-Encoding...");
		String contentEncoding = response.header("Content-Encoding");	
		logger.info("response header Content Encoding is:" + contentEncoding);
		Assert.assertEquals(contentEncoding, "gzip");

		//Verify the response time
		logger.info("checking Response Time...");
		long responseTime = response.getTime();
		if(responseTime>6000) {
			logger.warn("Response time is more than expected.");
		}
		logger.info("Response Time is:"+responseTime);
		Assert.assertTrue(responseTime<=50000);		

		//Verify cookies
		logger.info("checking cookies...");
		String cookie = response.getCookie("__cfduid");
		logger.info("cookie is: " + cookie);
		Assert.assertTrue(cookie!=null);
	}
}

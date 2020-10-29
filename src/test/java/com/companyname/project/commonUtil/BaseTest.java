package com.companyname.project.commonUtil;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
	public static RequestSpecification httpRequest;
	public static Response response;
	public static Logger logger;
	
	@BeforeClass
	public void setup() {
		logger = Logger.getLogger("CountriesRestApi");
		PropertyConfigurator.configure("log4j.properties");
		logger.setLevel(Level.DEBUG);
	}
}
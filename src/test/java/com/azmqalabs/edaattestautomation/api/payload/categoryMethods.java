package com.azmqalabs.edaattestautomation.api.payload;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.azmqalabs.edaattestautomation.api.common.commonMethods;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class categoryMethods {

	ExtentReports extent;
	static ExtentTest test;
//	String URI=commonMethods.launchapiUrl();
//	Log Log;

	public categoryMethods(ExtentTest test) {
		this.test = test;

	}

	public void Categories(Map<Object, Object> testdatamap) {
		try {
			String URI = commonMethods.launchapiUrl();
			System.out.println(URI + testdatamap.get("ParamQueryURL"));
			System.out.println(commonMethods.generateToken(URI));
			String Method = testdatamap.get("Method").toString();
			Response response = null;

			switch (Method) {
			case "GET":
				response = given().headers("Authorization", "bearer " + commonMethods.generateToken(URI))
						.contentType(ContentType.JSON).when().get(URI + testdatamap.get("ParamQueryURL").toString());
				response.then().log().all();
				Thread.sleep(1000);
				test.log(Status.PASS, "Categories Generic GET Method ");
				Assert.assertEquals(response.getStatusCode(), Integer.valueOf(testdatamap.get("StatusCode").toString()));
				break;
			case "GETWITHID":
			
				response = given().headers("Authorization", "bearer " + commonMethods.generateToken(URI))
						.contentType(ContentType.JSON).pathParam("categoryId", testdatamap.get("CategoryID").toString())
						.get(URI + testdatamap.get("ParamQueryURL").toString());
				response.then().log().all();
				test.log(Status.PASS, response.asString());
				Assert.assertEquals(response.getStatusCode(),Integer.valueOf(testdatamap.get("StatusCode").toString()));
				break;
			
		}
			} catch (Exception e) {
			test.log(Status.FATAL, "Failed");
		}
	}
}

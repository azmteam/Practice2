package com.azmqalabs.edaattestautomation.api.common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.azmqalabs.edaattestautomation.api.uitility.GlobalConstant;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class commonMethods {
	ExtentReports extent;
	static ExtentTest test;
	static String authstring;
	static String URL = "";
	
	public commonMethods(ExtentTest test) {
		this.test = test;
	}	
	public static String generateToken(String URI) {
		String auth="";
		try {
			RestAssured.baseURI = URI;
			RequestSpecification req = RestAssured.given();
			String payload = "grant_type=password&username=ENGLISHBILLER&password=Us%23rP%40ss1";
			req.header("Content-type", "application/json;charset=UTF-8");
			Response res = req.body(payload).post("/auth");
			authstring = res.getBody().asString();
			JsonPath.from(authstring).get("access_token");
			auth=JsonPath.from(authstring).get("access_token");
			
			} catch (Exception e) {
			test.log(Status.FATAL, "#Failure in the Authorization");		}
		return auth;		
		}

	public static String launchapiUrl() {
		try {
			URL = GlobalConstant.GLOBALTESTDATALOGINMAP.get("URL").toString();
		} catch (Exception e) {
			test.log(Status.FATAL, "Test URL: " + GlobalConstant.GLOBALTESTDATALOGINMAP.get("URL").toString());
		}
		return URL;

	}
	public static void Verificationstatuscode(int act,int exp) {		
		if(exp==act) {
			test.log(Status.PASS, "#Vaeried values");
		}
		else {
			test.log(Status.FAIL, "#Failure in the Authorization");
		}
	}	
	public static void Verficationstatusstring(String act,String exp) {		
		if(exp.equals(act)) {
			test.log(Status.PASS, "#Failure in the Authorization");
		}
		else {
			test.log(Status.FAIL, "#Failure in the Authorization");
		}
	}
	

}

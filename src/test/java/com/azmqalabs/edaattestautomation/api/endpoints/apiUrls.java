package com.azmqalabs.edaattestautomation.api.endpoints;

/*
 * https://petstore.swagger.io/v2/
 * https://petstore.swagger.io/v2/
 * https://petstore.swagger.io/v2/
 * https://petstore.swagger.io/v2/user/username
 * https://petstore.swagger.io/v2/
 * /user/{username}
 * 
 */
public class apiUrls {
	
	public static String baseURL="https://api-qa.edaaat.com/api/v2/";
	//GET /api/v2/Customers/Individuals/{nationalId
	//post
	public static String  authApikey ="https://api-qa.edaaat.com/auth";
	//GET
	public static String getIndcustomer=baseURL+"Customers/Individuals/{nationalId}";
	public static String getcountryCode=baseURL+"Lookups/CountryCode";
	public static String getcategoriesCode=baseURL+"Categories";
	public static String getproductsCode=baseURL+"products";
	public static String getproductsID=baseURL+"products/{productId}";
	
}

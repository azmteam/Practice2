package com.azmqalabs.edaattestautomation.api.test;

import java.util.ArrayList;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.azmqalabs.edaattestautomation.api.payload.DiscountMethods;
import com.azmqalabs.edaattestautomation.api.uitility.ExtentReportManager;
import com.azmqalabs.edaattestautomation.api.uitility.ReadData;
import com.codoid.products.fillo.Recordset;
import com.azmqalabs.edaattestautomation.api.common.Log;

public class PostDiscounttest {
	
	static String TestDataTab = "discountPOST";
	public Logger logger;
	Map<Object, Object> testdatamap;
	Map<Object, Object> testresultsmap;
	private String TestScriptID = "";
	static Recordset recTestData;
	static ArrayList<String> arrListTestDataColumnNames;
	ExtentReports extent;
	ExtentTest test;
	Log Log;
	DiscountMethods discountMethods;

	@Factory(dataProvider = "TestDataProvider")
	public PostDiscounttest(Map<Object, Object> testdatamap) {
		this.testdatamap = testdatamap;
		this.TestScriptID = testdatamap.get("TestScriptID").toString();

	}

	// DATA PROVIDER - FOR TEST DATA
	@DataProvider
	public static Object[][] TestDataProvider() throws Exception {
		Map<String, String> TestDataColNames;
		ReadData.retrieveLoginEnvDetails();
		arrListTestDataColumnNames = ReadData.readDataTableColumns(TestDataTab);
		TestDataColNames = ReadData.mapTestDataTableColumns(arrListTestDataColumnNames);
		recTestData = ReadData.readTestData(TestDataTab);
		int lastRowOfTestData = recTestData.getCount();
		Object[][] testdataobj = new Object[lastRowOfTestData][1];
		testdataobj = ReadData.mapTestData(TestDataColNames, recTestData);
		return testdataobj;
	}

	@Override
	public String toString() {
		return String.format("%S", TestScriptID);
	}

	@Test
	public void testpostDiscount() throws Exception {
		try {
			discountMethods.Discount(testdatamap);		
			test.log(Status.PASS, "Discount Has been Created");
			Log.PostTestStatus(TestScriptID);

		} catch (Exception e) {
			Log.ReportEvent("FATAL", "<< !!!!!!!!!!!!!!!!!!!!! TEST INCOMPLETE !!!!!!!!!!!!!!!!!!!!! >> " +e);
			Log.PostTestStatus(TestScriptID);
			throw new NullPointerException("********FATAL EXCEPTION TRIGGERED********");
		}
	}

	@BeforeClass
	public void setup() throws Exception {
		extent = ExtentReportManager.CreateExtentReportExtent();
		String className = this.getClass().getSimpleName();
		test = ExtentReportManager.CreateExtentReportTest(extent, className, testdatamap.get("TestDescription").toString(),
				TestScriptID);
		discountMethods= new DiscountMethods(test);
		Log =new Log(test);
		logger = LogManager.getLogger(this.getClass());
		Log.QAMachineInfo();
	}

	@AfterClass
	public void afterClass() {
		extent.flush();
	}

	// AFTER TEST - NEED TO USE FOR LOGIN/LOGOUT HANDLING PURPOSE
	@AfterTest
	public void afterTest() {
		System.out.println("After test");
	}

}

package com.azmqalabs.edaattestautomation.api.uitility;

import java.io.File;

//Extent report 5.x

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager  {
	//public ExtentSparkReporter sparkReporter;
	private static ExtentReports extent;
	private static String extentReportLocation = "ReportsConfig.xml";
	private static String filePath;
	private static ExtentHtmlReporter htmlReporter;
	private static ExtentTest test;

	String repName;
	
	public static ExtentReports GetExtent() {
		if (extent != null)
			return extent;
		extent = new ExtentReports();
		extent.attachReporter(getHtmlReporter());
		return extent;
	}

	private static ExtentHtmlReporter getHtmlReporter() {
		String sProjectName = "";
		sProjectName = System.getProperty("projectname");
		if (sProjectName == null) {
			sProjectName = Config.Get("PROJECT.NAME");
		}
		File classpathRoot = new File(System.getProperty("user.dir"));
		File app = new File(classpathRoot.getAbsolutePath() + "//src//test//resources//testConfig//extentreport//",
				extentReportLocation);
		File app1 = new File(classpathRoot.getPath() + "//","src//test//resources//testReport//");
		
		LocalDate today = LocalDate.now();
		LocalTime time=LocalTime.now();
	//	DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH_mm_ss");
	//	String formatDateTime = now.format(format);  
		
		filePath = app1.toString() + "\\" + Config.Get("TESTCYCLE.NAME")+"_"+today+"_"+time.toString().replace(".", "").replace(":", "")+".html";
		System.out.println(filePath);
		htmlReporter = new ExtentHtmlReporter(filePath.toString());
		htmlReporter.loadXMLConfig(app.toString());
		return htmlReporter;
	}

	public static ExtentReports CreateExtentReportExtent() {
		String sModuleName = "";
		extent = ExtentReportManager.GetExtent();
		return (extent);
	}

	public static ExtentTest createTestNew(String name, String description, String sTestIDBrowserName) {
		name = "Test" + sTestIDBrowserName;
		test = extent.createTest(name, description);
		return test;
	}
	
	public static ExtentTest CreateExtentReportTest(ExtentReports extent, String cName, String scName, String sTestID) {
		String sModuleName = "";
		test = ExtentReportManager.createTestNew(sModuleName, cName+" : "+scName, "_" + sTestID);
		return (test);
	}

	
	
	
	
	

	/*
	 * public void onStart(ITestContext testContext) {
	 * 
	 * String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new
	 * Date());//time stamp repName="Test-Report-"+timeStamp+".html"; File
	 * classpathRoot = new File(System.getProperty("user.dir"));
	 * System.out.println(classpathRoot);
	 * 
	 * // sparkReporter=new ExtentSparkReporter(".\\testReport\\"+repName);//specify
	 * location of the report sparkReporter=new ExtentSparkReporter(
	 * "E:\\EdaatAPI\\EdaatAPIAutomation\\src\\test\\resources\\testReport\\" +
	 * repName);//specify location of the report
	 * 
	 * sparkReporter.config().setDocumentTitle("RestAssuredAutomationEdaat"); //
	 * Title of report sparkReporter.config().setReportName("Edaat API Automation");
	 * // name of the report sparkReporter.config().setTheme(Theme.STANDARD);
	 * 
	 * extent=new ExtentReports(); extent.attachReporter(sparkReporter);
	 * extent.setSystemInfo("Application", "Edaat API Automation");
	 * extent.setSystemInfo("Operating System", System.getProperty("os.name"));
	 * extent.setSystemInfo("User Name", System.getProperty("user.name"));
	 * extent.setSystemInfo("Environemnt","QA");
	 * extent.setSystemInfo("user","Kathir");
	 * 
	 * 
	 * }
	 * 
	 * 
	 * public void onTestSuccess(ITestResult result) {
	 * test=extent.createTest(result.getName());
	 * test.assignCategory(result.getMethod().getGroups());
	 * test.createNode(result.getName()); test.log(Status.PASS, "Test Passed"); }
	 * 
	 * public void onTestFailure(ITestResult result) {
	 * test=extent.createTest(result.getName()); test.createNode(result.getName());
	 * test.assignCategory(result.getMethod().getGroups()); test.log(Status.FAIL,
	 * "Test Failed"); test.log(Status.FAIL, result.getThrowable().getMessage()); }
	 * 
	 * public void onTestSkipped(ITestResult result) {
	 * test=extent.createTest(result.getName()); test.createNode(result.getName());
	 * test.assignCategory(result.getMethod().getGroups()); test.log(Status.SKIP,
	 * "Test Skipped"); test.log(Status.SKIP, result.getThrowable().getMessage()); }
	 */
	/*
	 * public void onFinish(ITestContext testContext) { extent.flush(); }
	 */

}

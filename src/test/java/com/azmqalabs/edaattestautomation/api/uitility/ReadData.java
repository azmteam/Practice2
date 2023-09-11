package com.azmqalabs.edaattestautomation.api.uitility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ReadData {

	static String projectName = "";
	static String testData = "";

	public static ArrayList<String> readDataTableColumns(String sTabName) throws Exception {

		ArrayList<String> arrayColumnName;
		String sFilename = "";
		projectName = System.getProperty("projectname");
		if (projectName == null) {
			projectName = Config.Get("PROJECT.NAME");
		}
		testData = System.getProperty("testdata");
		if (testData == null) {
			testData = Config.Get("TESTDATA.LOCATION");
		}
		File classpathRoot = new File(System.getProperty("user.dir"));
		File app = new File(classpathRoot.getAbsolutePath() + "//src//test//resources//testdata//", testData);
		sFilename = app.toString();

		Connection connectionDB;
		Recordset recordsetTable;
		String strQuery = "";
		System.out.println("Before Fillo Object: " + sFilename);
		Fillo fillo = new Fillo();

		connectionDB = fillo.getConnection(sFilename);
		System.out.println("After: " + sFilename);
		strQuery = "Select " + "" + sTabName + "" + ".columns from " + sTabName + "";
		System.out.println("Query Tested: " + strQuery);
		recordsetTable = connectionDB.executeQuery(strQuery);
		arrayColumnName = recordsetTable.getFieldNames();

		return (arrayColumnName);
	}

	public static Map<String, String> mapTestDataTableColumns(ArrayList<String> arrayListTestDataColumns)
			throws Exception {
		HashMap<String, String> TestDataColNames = new HashMap<String, String>();
		for (int i = 0; i < arrayListTestDataColumns.size(); i++) {
			TestDataColNames.put("ColumnName" + i, arrayListTestDataColumns.get(i).trim());
		}
		return (TestDataColNames);
	}

	public static Recordset GetSpecifiedValueFromRecordset(Recordset recDefaultTestData, String sValue)
			throws FilloException {
		int recCount = recDefaultTestData.getCount();
		int i = 0;
		int j = 0;
		boolean matchFound = false;
		ArrayList<String> arrListTestDataColumnNames = recDefaultTestData.getFieldNames();
		String sReqData = "";
		recDefaultTestData.next();
		while (i < recCount) {
			j = 0;
			while (j < arrListTestDataColumnNames.size()) {
				sReqData = recDefaultTestData.getField(arrListTestDataColumnNames.get(j).trim());
				if (sReqData.equalsIgnoreCase(sValue)) {
					System.out.print("Matched Data Found");
					matchFound = true;
					break;
				}
				j = j + 1;
			}
			if (matchFound == true)
				break;
			i = i + 1;
			recDefaultTestData.next();
		}
		return (recDefaultTestData);
	}

	public static Recordset readTestData(String sTabName) throws Exception {

		String MavenCmdLineTestType = "";
		MavenCmdLineTestType = System.getProperty("testtype");
		System.out.println("Maven externalized parameter: " + MavenCmdLineTestType);
		// MavenCmdLineTestType="smoke";
		projectName = System.getProperty("projectname");
		if (projectName == null) {
			projectName = Config.Get("PROJECT.NAME");
		}
		testData = System.getProperty("testdata");
		if (testData == null) {
			testData = Config.Get("TESTDATA.LOCATION");
		}

		String autname = "";
		autname = System.getProperty("autname");
		if (autname == null) {
			autname = Config.Get("AUT.NAME");
		}
		String bankname = "";
		bankname = System.getProperty("bankname");
		if (bankname == null) {
			bankname = Config.Get("BANK.NAME");
		}
		String testCategory = "";
		if (MavenCmdLineTestType == null) {
			testCategory = Config.GetProperty("TEST.CATEGORY").toString();
		}

		String sFilename = "";
		File classpathRoot = new File(System.getProperty("user.dir"));
		File app = new File(classpathRoot.getAbsolutePath() + "//src//test//resources//testdata//", testData);
		sFilename = app.toString();
		// int TEST_SCENARIOS_COUNT;
		Connection connectionDB;
		Recordset recordsetTable;
		String strQuery = "";
		Fillo fillo = new Fillo();
		connectionDB = fillo.getConnection(sFilename);
		if (MavenCmdLineTestType == null)
			MavenCmdLineTestType = "";

		if (!MavenCmdLineTestType.equalsIgnoreCase("")) {
			if (!sTabName.equalsIgnoreCase("TestData") && !MavenCmdLineTestType.equalsIgnoreCase("")
					&& !bankname.equalsIgnoreCase("Default") && !bankname.equalsIgnoreCase(""))
				strQuery = "Select * from " + sTabName + " where TestScriptID<>'' and TestCategory='"
						+ MavenCmdLineTestType + "'" + " and BankName='" + bankname + "'"
						+ " and TestType='Automated' and (ScriptRun<> 'SKIP')";

			else if (!sTabName.equalsIgnoreCase("TestData") && !MavenCmdLineTestType.equalsIgnoreCase(""))
				strQuery = "Select * from " + sTabName + " where TestScriptID<>'' and TestCategory='"
						+ MavenCmdLineTestType + "'" + " and TestType='Automated' and (ScriptRun<> 'SKIP')";

		}

		else {
			if (!sTabName.equalsIgnoreCase("TestData") && !bankname.equalsIgnoreCase("Default")	&& !bankname.equalsIgnoreCase("") && testCategory.equalsIgnoreCase(""))
				strQuery = "Select * from " + sTabName + " where TestScriptID<>'' and BankName='" + bankname + "'"+ " and TestType='Automated' and (ScriptRun<> 'SKIP')";

			else if (testCategory.equalsIgnoreCase("") && !sTabName.equalsIgnoreCase("TestData"))
				strQuery = "Select * from " +sTabName+ " where TestScriptID<>'' and TestType='Automated' and (ScriptRun<> 'SKIP')";

			else if (!sTabName.equalsIgnoreCase("TestData") && !testCategory.equalsIgnoreCase("") && !bankname.equalsIgnoreCase("Default") && !bankname.equalsIgnoreCase(""))
				strQuery = "Select * from " + sTabName + " where TestScriptID<>'' and TestCategory='"+testCategory+ "'"+" and BankName='"+bankname+"'"+"and TestType='Automated' and (ScriptRun<> 'SKIP')";

			else if (!sTabName.equalsIgnoreCase("TestData") && !testCategory.equalsIgnoreCase(""))
				strQuery = "Select * from " + sTabName + " where TestScriptID<>'' and TestCategory='"+testCategory+ "'"+" and TestType='Automated' and (ScriptRun<> 'SKIP')";

			else if (!sTabName.equalsIgnoreCase("TestData") && !MavenCmdLineTestType.equalsIgnoreCase("") && !autname.equalsIgnoreCase(""))
				strQuery = "Select * from " + sTabName + " where TestScriptID<>'' and TestCategory='"+MavenCmdLineTestType+ "'" + " and AppName='" + autname + "'"
						+ " and TestType='Automated' and (ScriptRun<> 'SKIP')";

			else if (!sTabName.equalsIgnoreCase("TestData") && !MavenCmdLineTestType.equalsIgnoreCase("") && !autname.equalsIgnoreCase("") && !autname.equalsIgnoreCase("blank"))
				strQuery = "Select * from " + sTabName + " where TestScriptID<>'' and TestCategory='"+ MavenCmdLineTestType + "'" + " and AppName='" + autname + "'" + " and BankName='" + bankname
						+ "'" + " and TestType='Automated' and (ScriptRun<> 'SKIP')";

			else if (sTabName.equalsIgnoreCase("TestData"))
				strQuery = "Select * from " + sTabName + " where DataNotInScope<> 'no'";

		}
		System.out.println("Query Tested: " + strQuery);
		recordsetTable = connectionDB.executeQuery(strQuery);

		return (recordsetTable);
	}

	public static Recordset readTestDataBySheetOnly(String sDataSheetName) throws Exception {

		projectName = System.getProperty("projectname");
		if (projectName == null) {
			projectName = Config.Get("PROJECT.NAME");
		}
		testData = System.getProperty("testdata");
		if (testData == null) {
			testData = Config.Get("TESTDATA.LOCATION");
		}
		String sFilename = "";
		File classpathRoot = new File(System.getProperty("user.dir"));
		File app = new File(classpathRoot.getAbsolutePath() + "//src//test//resources//testdata//", testData);
		sFilename = app.toString();
		// int TEST_SCENARIOS_COUNT;
		Connection connectionDB;
		Recordset recordsetTable;
		String strQuery = "";
		Fillo fillo = new Fillo();
		connectionDB = fillo.getConnection(sFilename);

		strQuery = "Select * from " + sDataSheetName + "";

		System.out.println("Query Tested: " + strQuery);
		recordsetTable = connectionDB.executeQuery(strQuery);

		return (recordsetTable);
	}

	public static Recordset readTestDataBySpecifiedValue(String sDataSheetName, String sKeyName, String sKeyValue)
			throws Exception {

		projectName = System.getProperty("projectname");
		if (projectName == null) {
			projectName = Config.Get("PROJECT.NAME");
		}
		testData = System.getProperty("testdata");
		if (testData == null) {
			testData = Config.Get("TESTDATA.LOCATION");
		}
		String sFilename = "";
		File classpathRoot = new File(System.getProperty("user.dir"));
		File app = new File(classpathRoot.getAbsolutePath() + "//src//test//resources//testdata//", testData);
		sFilename = app.toString();
		// int TEST_SCENARIOS_COUNT;
		Connection connectionDB;
		Recordset recordsetTable;
		String strQuery = "";
		Fillo fillo = new Fillo();
		connectionDB = fillo.getConnection(sFilename);
		String sWhereCondition = "";

		if (sKeyName.contains(";")) {
			String ArraysKeyName[] = sKeyName.split(";");
			String ArraysKeyValue[] = sKeyValue.split(";");
			for (int i = 0; i < ArraysKeyName.length; i++) {
				sWhereCondition += ArraysKeyName[i] + "='" + ArraysKeyValue[i] + "'";
				if (i < ArraysKeyName.length - 1) {
					sWhereCondition = sWhereCondition + " and ";
				}

			}
			strQuery = "Select * from " + sDataSheetName + " where " + sWhereCondition + "";
		} else
			strQuery = "Select * from " + sDataSheetName + " where " + sKeyName + "='" + sKeyValue + "'";

		System.out.println("Query Tested: " + strQuery);
		recordsetTable = connectionDB.executeQuery(strQuery);

		return (recordsetTable);
	}

	public static Object[][] mapTestData(Map<String, String> TestDataColNames, Recordset recordsetTestData)
			throws Exception {

		String sColumnName = "";

		int lastCellNum = TestDataColNames.size();
		int lastRowNum = recordsetTestData.getCount();
		Object[][] obj = new Object[lastRowNum][1];
		for (int i = 0; i < lastRowNum; i++) {
			Map<Object, Object> datamap = new HashMap<>();
			recordsetTestData.next();
			Set set = TestDataColNames.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry mentry = (Map.Entry) iterator.next();
				sColumnName = mentry.getValue().toString();
				datamap.put(sColumnName, recordsetTestData.getField(sColumnName).trim());
			}
			obj[i][0] = datamap;
		}

		return obj;
	}

	public static Recordset readLoginData(String sTabName) throws Exception {

		projectName = System.getProperty("projectname");
		if (projectName == null) {
			projectName = Config.Get("PROJECT.NAME");
		}
		testData = System.getProperty("testdata");
		if (testData == null) {
			testData = Config.Get("TESTDATA.LOCATION");
		}
		String sAUTName = Config.GetProperty("AUT.NAME");
		String sEnvironment = Config.GetProperty("AUT.ENVIRONMENT");
		String sBankName = Config.GetProperty("BANK.NAME");
		///String sLoginCat = Config.GetProperty("TEST.LOGIN.CATEGOTY");
		String sFilename = "";
		File classpathRoot = new File(System.getProperty("user.dir"));

		File app = new File(classpathRoot.getAbsolutePath() + "//src//test//resources//testdata//", testData);
		sFilename = app.toString();
		Connection connectionDB;
		Recordset recordsetTable;
		String strQuery = "";
		Fillo fillo = new Fillo();
		connectionDB = fillo.getConnection(sFilename);

		if (sTabName.equalsIgnoreCase("Login"))
			strQuery = "Select * from Login";
		
		/*
		 * else if (sTabName.equalsIgnoreCase("Login") &&
		 * !sLoginCat.equalsIgnoreCase("") ) strQuery =
		 * "Select * from Login where LoginCategory='" + sLoginCat + "'";
		 */

		else if (sTabName.equalsIgnoreCase("URL"))
			strQuery = "Select * from URL where Environment='" + sEnvironment + "'" + " and AppName='" + sAUTName + "'"
					+ " and BankName='" + sBankName + "'" + "";
		recordsetTable = connectionDB.executeQuery(strQuery);

		return (recordsetTable);
	}

	public static HashMap<String, String> mapLoginDataTableColumns(ArrayList<String> arrayListTestDataColumns)
			throws Exception {

		HashMap<String, String> LoginColmsMap = new HashMap<String, String>();
		int j = LoginColmsMap.size();
		if (j > 0)
			j = j + 1;
		int k = 0;
		for (int i = j; i < arrayListTestDataColumns.size() + j; i++) {
			GlobalConstant.GLOBALTESTDATALOGINCOLUMNSMAP.put("ColumnName" + i, arrayListTestDataColumns.get(k).trim());
			k = k + 1;
		}
		return (LoginColmsMap);
	}

	public static void mapLoginTestData(Recordset recordsetTestData) throws Exception {

		String sColumnName = "";

		recordsetTestData.next();
		Set set = GlobalConstant.GLOBALTESTDATALOGINCOLUMNSMAP.entrySet();
		Iterator iterator = set.iterator();

		while (iterator.hasNext()) {
			try {
				Map.Entry mentry = (Map.Entry) iterator.next();
				sColumnName = mentry.getValue().toString();
				try {
					if (recordsetTestData.getField(sColumnName) != null) {
						GlobalConstant.GLOBALTESTDATALOGINMAP.put(sColumnName,
								recordsetTestData.getField(sColumnName).trim());
					}
				} catch (Exception e) {
				}
			} catch (Exception e) {
				System.out.print("Exception: " + e);
			}
		}
	}

	public static void retrieveLoginEnvDetails() throws Exception {
		ArrayList<String> arrayListTestDataColumns;
		Recordset recordsetTestData;
		/*
		 * System.out.println("##################Reading Table Columns - BEFORE+++++++"
		 * ); arrayListTestDataColumns = ReadData.readDataTableColumns("Login");
		 * System.out.
		 * println("##################Reading Table Columns  - AFTER ???????????");
		 * ReadData.mapLoginDataTableColumns(arrayListTestDataColumns);
		 * recordsetTestData = ReadData.readLoginData("Login");
		 * ReadData.mapLoginTestData(recordsetTestData);
		 */
		arrayListTestDataColumns = ReadData.readDataTableColumns("URL");
		ReadData.mapLoginDataTableColumns(arrayListTestDataColumns);
		recordsetTestData = ReadData.readLoginData("URL");
		ReadData.mapLoginTestData(recordsetTestData);
	}

	public static Recordset retrieveLoginEnvDetailsAPI() throws Exception {
		ArrayList<String> arrayListTestDataColumns;
		Recordset recordsetTestData;
		System.out.println("##################Reading Table Columns - BEFORE+++++++");
		arrayListTestDataColumns = ReadData.readDataTableColumns("Config");
		System.out.println("##################Reading Table Columns  - AFTER ???????????");
		ReadData.mapLoginDataTableColumns(arrayListTestDataColumns);
		recordsetTestData = ReadData.readLoginDataAPI("Config");
		ReadData.mapLoginTestData(recordsetTestData);
		return recordsetTestData;

	}

	public static Recordset readLoginDataAPI(String sTabName) throws Exception {

		projectName = System.getProperty("projectname");
		if (projectName == null) {
			projectName = Config.Get("PROJECT.NAME");
		}
		testData = System.getProperty("testdata");
		if (testData == null) {
			testData = Config.Get("TESTDATA.LOCATION");
		}
		String sAUTName = Config.GetProperty("AUT.NAME");
		String sEnvironment = Config.GetProperty("AUT.ENVIRONMENT");
		String sBankName = Config.GetProperty("BANK.NAME");
		String sFilename = "";
		File classpathRoot = new File(System.getProperty("user.dir"));

		File app = new File(classpathRoot.getAbsolutePath() + "//src//test//resources//testdata//", testData);
		sFilename = app.toString();
		Connection connectionDB;
		Recordset recordsetTable;
		String strQuery = "";
		Fillo fillo = new Fillo();
		connectionDB = fillo.getConnection(sFilename);

		if (sTabName.equalsIgnoreCase("Config"))
			strQuery = "Select * from Config";

		recordsetTable = connectionDB.executeQuery(strQuery);

		return (recordsetTable);
	}

	public static Recordset readTestDataAPI(String sTabName) throws Exception {

		String MavenCmdLineTestType = "";
		MavenCmdLineTestType = System.getProperty("testtype");
		System.out.println("Maven externalized parameter: " + MavenCmdLineTestType);
		// MavenCmdLineTestType="smoke";
		projectName = System.getProperty("projectname");
		if (projectName == null) {
			projectName = Config.Get("PROJECT.NAME");
		}
		testData = System.getProperty("testdata");
		if (testData == null) {
			testData = Config.Get("TESTDATA.LOCATION");
		}

		String autname = "";
		autname = System.getProperty("autname");
		if (autname == null) {
			autname = Config.Get("AUT.NAME");
		}
		String bankname = "";
		bankname = System.getProperty("bankname");
		if (bankname == null) {
			bankname = Config.Get("BANK.NAME");
		}
		String testCategory = Config.GetProperty("TEST.CATEGORY").toString();
		String sFilename = "";
		File classpathRoot = new File(System.getProperty("user.dir"));
		File app = new File(classpathRoot.getAbsolutePath() + "//src//test//resources//testdata//", testData);
		sFilename = app.toString();
		// int TEST_SCENARIOS_COUNT;
		Connection connectionDB;
		Recordset recordsetTable;
		String strQuery = "";
		Fillo fillo = new Fillo();
		connectionDB = fillo.getConnection(sFilename);
		if (MavenCmdLineTestType == null)
			MavenCmdLineTestType = "";

		if (!MavenCmdLineTestType.equalsIgnoreCase("")) {

			strQuery = "Select * from " + sTabName + " where TestScriptID<>'' and TestCategory='" + MavenCmdLineTestType
					+ "'" + " and BankName='" + bankname + "'" + " and TestType='Automated' and (ScriptRun<> 'SKIP')";

			System.out.println("Maven Query Tested: " + strQuery);
		}

		else
			strQuery = "Select * from " + sTabName + "";
		System.out.println("Maven Query Tested: " + strQuery);

		recordsetTable = connectionDB.executeQuery(strQuery);

		return (recordsetTable);
	}

	public static Recordset readTestDataBySpecifiedValueLike(String sDataSheetName, String sKeyName, String sKeyValue)
			throws Exception {

		String sFilename = "";
		projectName = System.getProperty("projectname");
		if (projectName == null) {
			projectName = Config.Get("PROJECT.NAME");
		}
		testData = System.getProperty("testdata");
		if (testData == null) {
			testData = Config.Get("TESTDATA.LOCATION");
		}
		File classpathRoot = new File(System.getProperty("user.dir"));
		File app = new File(classpathRoot.getAbsolutePath() + "//src//test//resources//testdata//", testData);
		sFilename = app.toString();
		// int TEST_SCENARIOS_COUNT;
		Connection connectionDB;
		Recordset recordsetTable;
		String strQuery = "";
		Fillo fillo = new Fillo();
		connectionDB = fillo.getConnection(sFilename);
		String sWhereCondition = "";

		if (sKeyName.contains(";")) {
			String ArraysKeyName[] = sKeyName.split(";");
			String ArraysKeyValue[] = sKeyValue.split(";");
			for (int i = 0; i < ArraysKeyName.length; i++) {
				sWhereCondition += ArraysKeyName[i] + "='" + ArraysKeyValue[i] + "'";
				if (i < ArraysKeyName.length - 1) {
					sWhereCondition = sWhereCondition + " and ";
				}

			}
			strQuery = "Select * from " + sDataSheetName + " where " + sWhereCondition + "";
		} else
			strQuery = "Select * from " + sDataSheetName + " where " + sKeyName + " Like '%" + sKeyValue + "%'";

		System.out.println("Query Tested: " + strQuery);
		recordsetTable = connectionDB.executeQuery(strQuery);

		return (recordsetTable);
	}
}
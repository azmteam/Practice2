package com.azmqalabs.edaattestautomation.api.uitility;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

//E:\EdaatAPI\EdaatAPIAutomation\src\test\resources\testData\Edaat_API_Userdata.xls
	@DataProvider(name="Data")
	public String[][] getAllData() throws IOException
	{
		String path=System.getProperty("user.dir")+"//src//test//resources//testData//Edaat_API_Userdata.xlsx";
		excelUtility xl=new excelUtility(path);
	
		int rownum=xl.getRowCount("Sheet1");	
		int colcount=xl.getCellCount("Sheet1",1);
		
		String apidata[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++)
		{		
			for(int j=0;j<colcount;j++)
			{
				apidata[i-1][j]= xl.getCellData("Sheet1",i, j);  
			}
		}
	
		return apidata;
	}
	
	@DataProvider(name="nationalId")
	public String[] getvalueFromexcel() throws IOException
	{
		String path=System.getProperty("user.dir")+"//src//test//resources//testData//Edaat_API_Userdata.xlsx";
		excelUtility xl=new excelUtility(path);
	
		int rownum=xl.getRowCount("Citycode");	
			
		String apidata[]=new String[rownum];
		
		for(int i=1;i<=rownum;i++)
		{		
			apidata[i-1]= xl.getCellData("Citycode",i, 1);  
			
		}
	
		return apidata;
	}
	
}

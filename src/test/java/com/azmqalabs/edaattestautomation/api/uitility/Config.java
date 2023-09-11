package com.azmqalabs.edaattestautomation.api.uitility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.testng.annotations.Test;

public class Config {
	public static String Get(String sPropName) {
		return GetProperty(sPropName);
	}

	public static String GetProperty(String sKeyName) {
		File classpathRoot = new File(System.getProperty("user.dir"));
		File app = new File(classpathRoot, "src//test//resources//testconfig//Config.properties");
		String PROP_FILE = app.getAbsolutePath();

		String sValue = null;
		try {

			Properties props = new Properties();
			props.load(new FileInputStream(PROP_FILE.toString()));
			sValue = props.getProperty(sKeyName);

		} catch (Exception e) {
			System.out.println("Failed to read from " + PROP_FILE + " file.");
		}
		return sValue;
	}

	
}

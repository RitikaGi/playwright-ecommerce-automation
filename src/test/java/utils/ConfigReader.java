package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties;
	
	 static {
		 try {
			 properties = new Properties();
			 FileInputStream fis = new FileInputStream("src/test/java/resources/config.properties");
			 properties.load(fis);
		 }catch(IOException e) {
			 e.printStackTrace();
		 }
	 
	 }
	 public static String getProperty(String key) {
		 return properties.getProperty(key);
	 }
	 
	 public static String getProperty(String key, String defaultValue) {
		 return properties.getProperty(key, defaultValue);
	 }
	 public static boolean isHeadless() {
		String headlessConfig = getProperty("headless", "auto").toLowerCase();
		switch(headlessConfig) {
		case "true":
			return true;
		case "false":
			return false;
		case "auto":
		   default:
			return System.getenv("CI")!=null;
		}
	 }

}

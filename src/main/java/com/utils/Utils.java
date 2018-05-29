/**
 * 
 */
package com.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.testng.Assert;

/**
 * @author soujanyaj
 *
 */
public class Utils {
	/**
	 * Gets the property.
	 *
	 * @param propertyName the property name
	 * @return the property
	 */
	public static String getProperty(String propertyName) {
		Properties prop = new Properties();
		InputStream input = null;
		InputStream inputEnv = null;

		String propertyValue = null;
		try {
			input = new FileInputStream("./data.properties");
			inputEnv = new FileInputStream("./environment.properties");

			prop.load(input);
			prop.load(inputEnv);
			propertyValue = prop.getProperty(propertyName);
			if (propertyValue.startsWith("$")) {
				propertyValue = System.getenv(propertyValue.substring(2, propertyValue.length() - 1));
			}
		} catch (IOException io) {
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ io.getStackTrace()[0].getLineNumber() + " And Exception is", io);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException io) {
					Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
							+ io.getStackTrace()[0].getLineNumber() + " And Exception is", io);
				}
			}
		}
		return propertyValue;
	}
	
	public static String getDateInFormat(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE, dd.MM.YYYY");
		LocalDate localDate = LocalDate.now().plusDays(2);
		String formattedDate = dtf.format(localDate).toString();
		return formattedDate.substring(0,2) + formattedDate.substring(3);
	}
		
	public static void main(String args[]){
		System.out.println(getDateInFormat());
	}

}

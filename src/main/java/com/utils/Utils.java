/**
 * 
 */
package com.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
	 * @param propertyName
	 *            the property name
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

	public static String getDateInFormat() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE, dd.MM.YYYY");
		LocalDate localDate = LocalDate.now().plusDays(2);
		String formattedDate = dtf.format(localDate).toString();
		return formattedDate.substring(0, 2) + formattedDate.substring(3);
	}

	public static void executeShellScript(String scriptPath, String... args) {

		try {
			System.out.println("scriptPath: " + scriptPath);
			Runtime.getRuntime().exec("chmod u+x " + scriptPath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// String path = Utils.getProperty("pathToShellScript");
		String[] command = { scriptPath, args[0] };
		ProcessBuilder p = new ProcessBuilder(command);
		System.out.println(Arrays.toString(command));
		try {

			Process p2 = p.start();
			Thread.sleep(3000);
			p2.waitFor();
		} catch (Exception e) {
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e.getStackTrace()[0].getLineNumber() + " And Exception is", e);
		}

	}
	
	/**
	 * Return file content as string.
	 *
	 * @param fileName the file name
	 * @return the string
	 */
	public static String returnFileContentAsString(String fileName) {
		InputStream is = null;
		StringBuilder sb = null;
		try {
			is = new FileInputStream(fileName);
			@SuppressWarnings("resource")
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = null;
			line = buf.readLine();
			sb = new StringBuilder();
			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}
		} catch (IOException io) {
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ io.getStackTrace()[0].getLineNumber() + " And Exception is", io);
		}
		return sb.toString();
	}

	/**
	 * Write to file.
	 *
	 * @param fileName the file name
	 * @param contents the contents
	 * @param color the color
	 */
	public static void writeToFile(String logFile, String contents, String color) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter("./TestResultFiles/" + logFile + ".html", true);
			bw = new BufferedWriter(fw);
			bw.newLine();
			if (color != null)
				//bw.write("<font color=\"" + color + "\">" + contents + "</font>");
				bw.write("<font color=\"" + color + "\">" + contents + "</font>");
			else
				bw.write(contents);
			bw.write("<br>");
			bw.newLine();
		} catch (IOException io) {
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ io.getStackTrace()[0].getLineNumber() + " And Exception is", io);
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException io) {
				Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
						+ io.getStackTrace()[0].getLineNumber() + " And Exception is", io);
			}
		}

	}
	
	public static void main(String args[]) {
		System.out.println(getDateInFormat());
	}

}

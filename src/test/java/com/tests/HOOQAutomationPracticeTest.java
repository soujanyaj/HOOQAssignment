package com.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HOOQAutomationPracticeTest extends BaseTest {

	@BeforeSuite
	public static void initialization() {
		testInitialize("core");
	}

	@Test (priority=2)
	public static void signUpTests() {
		String logFile = "signUpTests";
		config.openURL(logFile);
		config.clickSignIn(logFile);
		config.clickCreateAnAccount(logFile);
		config.createAnAccount(logFile);
	}

	@DataProvider(name = "loginData")
	public static Object[][] getLoginData() {
		return config.getLoginData();
	}
	
	@DataProvider(name = "loginAPIData")
	public static Object[][] getLoginAPIData() {
		return new Object[][] { { "jaggu.souj@gmail.com", "Souj@2322"}};
	}

	@Test(dataProvider="loginData", priority=1)
	public static void loginTests(String email, String password, String testType, String expectedErrorMsg) {
		String logFile = email + password + testType + expectedErrorMsg;
		
		config.openURL(logFile);
		config.clickSignIn(logFile);
		config.login(logFile,email.split("_")[1].trim(), password.split("_")[1].trim(), testType.split("_")[1].trim(), expectedErrorMsg.split("_")[1].trim());
		System.out.println("testType: "+testType);
		if(testType.split("_")[1].trim().equals("positive"))
			config.clickSignOut(logFile);
	}

	@Test(dataProvider="loginAPIData", priority=3)
	public static void loginAPITests(String email, String password) {
		String logFile = email + password ;
		config.loginUsingAPI(logFile, email, password);
	}
	
	@AfterSuite
	public static void quitDriver() {
		config.quitDriver();
	}
}

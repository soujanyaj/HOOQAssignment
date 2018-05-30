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

	@Test
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

	@Test(dataProvider="loginData")
	public static void loginTests(String email, String password, String testType, String expectedErrorMsg) {
		String logFile = email + password + testType + expectedErrorMsg;
		
		config.openURL(logFile);
		config.clickSignIn(logFile);
		config.login(logFile,email, password, testType, expectedErrorMsg);
		
		if(testType.equals("positive"))
			config.clickSignOut(logFile);
	}

	@Test(dataProvider="loginAPIData")
	public static void loginAPITests(String email, String password) {
		String logFile = email + password ;
		config.loginUsingAPI(logFile, email, password);
	}
	
	@AfterSuite
	public static void quitDriver() {
		config.quitDriver();
	}
}

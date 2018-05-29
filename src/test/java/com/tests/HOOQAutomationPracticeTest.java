package com.tests;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HOOQAutomationPracticeTest extends BaseTest{
	
	@BeforeSuite
	public static void initialization() {
		testInitialize("core");
	}
	
	
	//@Test
	public static void signUpTests(){
		config.signUp();
	}

	
	@DataProvider (name = "loginData")
	public static Object[][] getSignUpData(){
		
		return new Object[][] {{"jaggu.souj@gmail.com","Souj@2322"}};
	}
	
	@Test(dataProvider="loginData")
	public static void loginTests(String email, String password) {
		config.login(email,password);
	}
}

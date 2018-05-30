package com.configurations;

import com.core.pages.AuthenticationPage;
import com.core.pages.CreateAnAccountPage;
import com.core.pages.HomePage;

public interface IConfiguration {

	public HomePage openURL(String logFile);

	public AuthenticationPage clickSignIn(String logFile);

	public AuthenticationPage clickSignOut(String logFile);

	public CreateAnAccountPage clickCreateAnAccount(String logFile);

	public void createAnAccount(String logFile);

	public void login(String logFile,String email, String password,String testType, String expectedErrorMsg);

	public void loginUsingAPI(String logFile,String email, String password);

	public void quitDriver();
	
	public Object[][] getLoginData();
}

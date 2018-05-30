package com.configurations;

import com.core.pages.AuthenticationPage;
import com.core.pages.CreateAnAccountPage;
import com.core.pages.Data;
import com.core.pages.HomePage;
import com.core.pages.MyAccountPage;

public class Core extends AbstractTemplateConfig implements IConfiguration {


	public Core(String url) {
		this.url = url;
		this.homePage = new HomePage(this.url);
		this.authenticationPage = new AuthenticationPage(this.url);
		this.createAnAccountPage = new CreateAnAccountPage(this.url);
		this.myAccountPage = new MyAccountPage(this.url);
		this.data = new Data();
	}	

	public HomePage openURL(String logFile) {
		return this.homePage.openURL(logFile);
	}

	public void login(String logFile,String email, String password, String testType, String expectedErrorMsg) {

		this.authenticationPage.login(logFile,email, password,testType, expectedErrorMsg);
	}

	public void loginUsingAPI(String logFile,String email, String password) {

		this.authenticationPage.loginUsingAPI(logFile,email, password);
	}

	public AuthenticationPage clickSignIn(String logFile) {
		return this.homePage.clickSignIn(logFile);
	}

	public AuthenticationPage clickSignOut(String logFile) {
		return this.myAccountPage.clickSignOut(logFile);
	}

	
	public CreateAnAccountPage clickCreateAnAccount(String logFile) {
		return this.authenticationPage.clickCreateAnAccount(logFile);
	}

	public void createAnAccount(String logFile) {
		this.createAnAccountPage.createAnAccount(logFile);
	}

	@Override
	public void quitDriver() {
		this.homePage.quitDriver();
		
	}

	@Override
	public Object[][] getLoginData() {
		return this.data.getLoginData();
	}

}

package com.core.pages;

import java.net.URL;
import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.utils.APIUtils;
import com.utils.Utils;

public class AuthenticationPage extends AbstractTemplatePage {

	protected String url;
	private static By createAccountEmail_textfield = By.id("email_create");
	private static By createSubmit_button = By.id("SubmitCreate");

	private static By titleMrs_radioButton = By.id("id_gender2");
	private static By email_textfield = By.id("email");
	private static By password_textfield = By.id("passwd");

	private static By errorMsgSectionMsg = By.xpath("//div[@class='alert alert-danger']/ol/li");

	private static By submitLogin_button = By.id("SubmitLogin");

	private static By signOut_link = By.xpath("//a[@class='logout']");

	public AuthenticationPage(String url) {
		this.url = url;
	}

	public void loginUsingAPI(String logFile, String email, String password) {

		char[] loginResp;

		try {
			loginResp = APIUtils.getPOSTResponseText(
					new URL("http://automationpractice.com/index.php?controller=authentication"),
					"email=" + email + "&passwd=" + password + "&SubmitLogin= ", "", "POST");
			System.out.println("loginResp: " +Arrays.toString(loginResp) );
			Utils.writeToFile(logFile, Arrays.toString(loginResp), "green");
		} catch (Exception e) {
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e.getStackTrace()[0].getLineNumber() + " And Exception is", e);
		}

	}

	public void login(String logFile,String email, String password,String testType, String expectedErrorMsg) {
		try {
			driver.findElement(email_textfield).sendKeys(email);
			driver.findElement(password_textfield).sendKeys(password);
			driver.findElement(submitLogin_button).click();
			takeScreenshot(logFile);
			
			if(testType.equals("negative")) {
				String actualErrorMsg = driver.findElement(errorMsgSectionMsg).getText();
				System.out.println("actualErrorMsg: "+actualErrorMsg);
				if(expectedErrorMsg.equals(actualErrorMsg))
					Utils.writeToFile(logFile, "expectedErrorMsg: "+expectedErrorMsg+ "AND Actual Error Msg: "+actualErrorMsg+ "are same" , "green");
				else {
					Utils.writeToFile(logFile, "expectedErrorMsg: "+expectedErrorMsg+ "AND Actual Error Msg: "+actualErrorMsg+ "are NOT same","red");
					softAssert.fail("expectedErrorMsg: "+expectedErrorMsg+ "AND Actual Error Msg: "+actualErrorMsg+ "are NOT same");
				}
			}
			else
			{
				if(driver.findElement(signOut_link).isDisplayed())
					Utils.writeToFile(logFile, "Login Successful" , "green");
				else {
					Utils.writeToFile(logFile, "Login Failed" , "red");
					softAssert.fail("Login Failed");
				}

			}
		} catch (Exception e) {
			takeScreenshot(logFile);
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e.getStackTrace()[0].getLineNumber() + " And Exception is", e);
		}
		softAssert.assertAll();
	}

	public CreateAnAccountPage clickCreateAnAccount(String logFile) {
		try {

			driver.findElement(createAccountEmail_textfield)
					.sendKeys(RandomStringUtils.randomAlphanumeric(10) + "@gmail.com");
			driver.findElement(createSubmit_button).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(titleMrs_radioButton));
			softAssert.assertTrue(driver.findElement(titleMrs_radioButton).isDisplayed());
			Utils.writeToFile(logFile, "Successfully clicked CreateAnAccount link by entering email address", "green");
			takeScreenshot(logFile);
		} catch (Exception e) {
			takeScreenshot(logFile);
			Utils.writeToFile(logFile, "Error occured while clicking CreateAnAccount link by entering email address", "red");

			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e.getStackTrace()[0].getLineNumber() + " And Exception is", e);
		}
		softAssert.assertAll();

		return new CreateAnAccountPage(url);
	}

}

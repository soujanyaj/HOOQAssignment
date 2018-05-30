package com.core.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.utils.Utils;

public class HomePage extends AbstractTemplatePage {

	protected String url;
	private static By signIn_link = By.xpath("//a[@class='login']");
	private static By createAccountEmail_textfield = By.id("email_create");

	public HomePage(String url) {
		this.url = url;
	}

	public HomePage openURL(String logFile) {
		try {
			driver.get(url);
			wait.until(ExpectedConditions.elementToBeClickable(signIn_link));
			Utils.writeToFile(logFile, "Successfully Opened URL", "green");
			takeScreenshot(logFile);
		} catch (Exception e) {
			takeScreenshot(logFile);
			Utils.writeToFile(logFile, "Error occured while Opening URL", "red");

			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e.getStackTrace()[0].getLineNumber() + " And Exception is", e);
		}

		return new HomePage(url);
	}

	public AuthenticationPage clickSignIn(String logFile) {
		try {
			driver.findElement(signIn_link).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(createAccountEmail_textfield));
			Utils.writeToFile(logFile, "Successfully clicked SignIn link", "green");

			takeScreenshot(logFile);
		} catch (Exception e) {
			takeScreenshot(logFile);
			Utils.writeToFile(logFile, "Error occured while clicking SignIn link", "red");
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e.getStackTrace()[0].getLineNumber() + " And Exception is", e);
		}

		return new AuthenticationPage(url);
	}

	public void quitDriver() {
		if (driver != null)
			driver.quit();
	}
}

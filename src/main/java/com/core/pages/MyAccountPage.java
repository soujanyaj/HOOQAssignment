package com.core.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.utils.Utils;

public class MyAccountPage extends AbstractTemplatePage {

	protected String url;
	private static By signOut_link = By.xpath("//a[@class='logout']");

	public MyAccountPage(String url) {
		this.url = url;
	}

	

	public AuthenticationPage clickSignOut(String logFile) {
		try {
			
			wait.until(ExpectedConditions.presenceOfElementLocated(signOut_link));
			driver.findElement(signOut_link).click();
			Utils.writeToFile(logFile, "Successfully clicked SignOut link", "green");
			
			takeScreenshot(logFile);

		} catch (Exception e) {
			takeScreenshot(logFile);
			Utils.writeToFile(logFile, "Error occured while clicking SignOut link", "red");
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e.getStackTrace()[0].getLineNumber() + " And Exception is", e);
		}

		return new AuthenticationPage(url);
	}
}

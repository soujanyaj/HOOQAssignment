/**
 * 
 */
package com.core.pages;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.configurations.WebDriverFactory;
import com.utils.Utils;

/**
 * @author soujanyaj
 *
 */
public class AbstractTemplatePage {
	protected static WebDriver driver = null;
	protected static WebDriverWait wait = null;
	protected static SoftAssert softAssert = null;
	
	protected AbstractTemplatePage() {
		System.out.println("browserPlatform:" + Utils.getProperty("browserPlatform"));
		driver = WebDriverFactory.getWebDriver(Utils.getProperty("browserPlatform"));
		wait = new WebDriverWait(driver, 10000);
		softAssert = new SoftAssert();
	}

	public void takeScreenshot(String fileName) {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("extentReports/Screenshots/" + fileName + "screenshot.png"));
		} catch (IOException e) {
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e.getStackTrace()[0].getLineNumber() + " And Exception is", e);		}
	}
	
}

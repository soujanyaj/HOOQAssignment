package com.core.pages;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.utils.Utils;

public class HomePage extends AbstractTemplatePage {

	protected String url;
	private static By signIn_link = By.xpath("//a[@class='login']");
	private static By createAccountEmail_textfield = By.id("email_create");
	private static By createSubmit_button = By.id("SubmitCreate");
	
	private static By titleMrs_radioButton = By.id("id_gender2");
	private static By firstName_textfield = By.id("customer_firstname");
	private static By lastName_textfield = By.id("customer_lastname");
	private static By email_textfield = By.id("email");
	private static By password_textfield = By.id("passwd");
	private static By dob_days_dropDown = By.id("days");
	private static By dob_months_dropDown = By.id("months");
	private static By dob_years_dropDown = By.id("years");

	private static By address_firstName_textfield = By.id("firstname");
	private static By address_lastName_textfield = By.id("lastname");
	private static By address_company_textfield = By.id("company");
	private static By address_textfield = By.id("address1");
	private static By address_line2_textfield = By.id("address2");
	private static By address_city_textfield = By.id("city");
	private static By address_state_dropDown = By.id("id_state");
	private static By address_postalCode_textfield = By.id("postcode");
	private static By address_country_dropDown = By.id("id_country");
	private static By address_mobile_textfield = By.id("phone_mobile");
	private static By address_alias_textfield = By.id("alias");

	private static By register_button = By.id("submitAccount");
	
	private static By myAccount_Title = By.xpath("//h1[text()='My account']");
	private static By submitLogin_button = By.id("SubmitLogin");
	
	public HomePage(String url) {
		this.url = url;
	}

	public void login(String email, String password) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10000);
			driver.get(url);
			wait.until(ExpectedConditions.elementToBeClickable(signIn_link));
			driver.findElement(signIn_link).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(email_textfield));

			driver.findElement(email_textfield).sendKeys(email);
			driver.findElement(password_textfield).sendKeys(password);

			driver.findElement(submitLogin_button).click();
			
			takeScreenshot();
			driver.quit();
		} catch (Exception e) {
			takeScreenshot();
			if (driver != null)
				driver.quit();
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e.getStackTrace()[0].getLineNumber() + " And Exception is", e);
		}
	}
	public void signUp() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10000);
			driver.get(url);
			wait.until(ExpectedConditions.elementToBeClickable(signIn_link));
			driver.findElement(signIn_link).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(createAccountEmail_textfield));
			driver.findElement(createAccountEmail_textfield).sendKeys(RandomStringUtils.randomAlphanumeric(10)+"@gmail.com");
			driver.findElement(createSubmit_button).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(titleMrs_radioButton));
			driver.findElement(titleMrs_radioButton).click();
			
			driver.findElement(firstName_textfield).sendKeys(RandomStringUtils.randomAlphabetic(5)+"FN");
			driver.findElement(lastName_textfield).sendKeys(RandomStringUtils.randomAlphabetic(5)+"LN");
			driver.findElement(password_textfield).sendKeys("test123");

			new Select(driver.findElement(dob_days_dropDown)).selectByValue("11");
			new Select(driver.findElement(dob_months_dropDown)).selectByVisibleText("June ");
			new Select(driver.findElement(dob_years_dropDown)).selectByValue("1984");

			driver.findElement(address_firstName_textfield).sendKeys(RandomStringUtils.randomAlphabetic(5)+"FN");
			driver.findElement(address_lastName_textfield).sendKeys(RandomStringUtils.randomAlphabetic(5)+"LN");
			driver.findElement(address_company_textfield).sendKeys(RandomStringUtils.randomAlphanumeric(10)+" Company");
			driver.findElement(address_textfield).sendKeys(RandomStringUtils.randomAlphanumeric(10)+" AddressLine1");
			driver.findElement(address_line2_textfield).sendKeys(RandomStringUtils.randomAlphanumeric(10)+" AddressLine2");

			driver.findElement(address_city_textfield).sendKeys("Charlotte");
			new Select(driver.findElement(address_state_dropDown)).selectByVisibleText("North Carolina");
			driver.findElement(address_postalCode_textfield).sendKeys("28201");
			new Select(driver.findElement(address_country_dropDown)).selectByVisibleText("United States");

			driver.findElement(address_mobile_textfield).sendKeys("1111111111");
			driver.findElement(address_alias_textfield).sendKeys(RandomStringUtils.randomAlphanumeric(10)+"Alias");			
			
			driver.findElement(register_button).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(myAccount_Title));

			takeScreenshot();
			driver.quit();
		} catch (Exception e) {
			takeScreenshot();
			if (driver != null)
				driver.quit();
			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e.getStackTrace()[0].getLineNumber() + " And Exception is", e);
		}
	}

	public void takeScreenshot() {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("./screenshot.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}

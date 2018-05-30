package com.core.pages;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.utils.Utils;

public class CreateAnAccountPage extends AbstractTemplatePage {

	protected String url;

	private static By titleMrs_radioButton = By.id("id_gender2");
	private static By firstName_textfield = By.id("customer_firstname");
	private static By lastName_textfield = By.id("customer_lastname");
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

	public CreateAnAccountPage(String url) {
		this.url = url;
	}

	public void createAnAccount(String logFile) {
		try {
			driver.findElement(titleMrs_radioButton).click();
			driver.findElement(firstName_textfield).sendKeys(RandomStringUtils.randomAlphabetic(5) + "FN");
			driver.findElement(lastName_textfield).sendKeys(RandomStringUtils.randomAlphabetic(5) + "LN");
			driver.findElement(password_textfield).sendKeys("test123");

			new Select(driver.findElement(dob_days_dropDown)).selectByValue("11");
			new Select(driver.findElement(dob_months_dropDown)).selectByVisibleText("June ");
			new Select(driver.findElement(dob_years_dropDown)).selectByValue("1984");

			driver.findElement(address_firstName_textfield).sendKeys(RandomStringUtils.randomAlphabetic(5) + "FN");
			driver.findElement(address_lastName_textfield).sendKeys(RandomStringUtils.randomAlphabetic(5) + "LN");
			driver.findElement(address_company_textfield)
					.sendKeys(RandomStringUtils.randomAlphanumeric(10) + " Company");
			driver.findElement(address_textfield).sendKeys(RandomStringUtils.randomAlphanumeric(10) + " AddressLine1");
			driver.findElement(address_line2_textfield)
					.sendKeys(RandomStringUtils.randomAlphanumeric(10) + " AddressLine2");

			driver.findElement(address_city_textfield).sendKeys("Charlotte");
			new Select(driver.findElement(address_state_dropDown)).selectByVisibleText("North Carolina");
			driver.findElement(address_postalCode_textfield).sendKeys("28201");
			new Select(driver.findElement(address_country_dropDown)).selectByVisibleText("United States");

			driver.findElement(address_mobile_textfield).sendKeys("1111111111");
			driver.findElement(address_alias_textfield).sendKeys(RandomStringUtils.randomAlphanumeric(10) + "Alias");

			driver.findElement(register_button).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(myAccount_Title));

			Utils.writeToFile(logFile, "Creation of account is successful", "green");
			takeScreenshot(logFile);
		} catch (Exception e) {
			takeScreenshot(logFile);
			Utils.writeToFile(logFile, "Error occured while creating account", "red");

			Assert.fail(Thread.currentThread().getStackTrace()[1].getMethodName() + " FAILED, Line No: "
					+ e.getStackTrace()[0].getLineNumber() + " And Exception is", e);
		}
	}

}

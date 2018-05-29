/**
 * 
 */
package com.core.pages;

import org.openqa.selenium.WebDriver;

import com.configurations.WebDriverFactory;
import com.utils.Utils;

/**
 * @author soujanyaj
 *
 */
public class AbstractTemplatePage {
	 protected static WebDriver driver = null;

	protected AbstractTemplatePage()
     {
		System.out.println("browserPlatform:"+Utils.getProperty("browserPlatform"));
        driver = WebDriverFactory.getWebDriver(Utils.getProperty("browserPlatform"));
     }
}

/**
 * 
 */
package com.configurations;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import com.utils.Utils;

/**
 * @author soujanyaj
 *
 */
public class WebDriverFactory {
	private static WebDriver uniqueInstanceWebDriver = null;

	public static WebDriver getWebDriver(String browserPlatform) {
		if (uniqueInstanceWebDriver == null) {
			SessionId sessionId = null;
			String seleniumAddress = null;
			try {
				DesiredCapabilities capability = DesiredCapabilities.chrome();
				switch (browserPlatform) {
				case "chrome":

					System.out.println("System.getProperty(\"user.dir\"): " + System.getProperty("user.dir"));
					File seleniumServerFile = new File("startSeleniumServer.sh");

					Utils.executeShellScript(seleniumServerFile.getAbsolutePath(),
							System.getProperty("user.dir") + "/lib");
					System.out.println("inside chrome");
					File file = new File("lib/chromedriver_2.38");
					System.out.println("file.getAbsolutePath(): " + file.getAbsolutePath());
					System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
					// capability = DesiredCapabilities.chrome();
					capability.setPlatform(Platform.MAC);

					seleniumAddress = "http://127.0.0.1:4444/wd/hub";
					break;
				case "iOSSafari":
					capability.setCapability("automationName", "XCUITest");
					capability.setCapability("browserName", "Safari");
					capability.setCapability("platformName", "iOS");
					capability.setCapability("deviceName", "iPhone 7 Plus");
					/** set UDID for real devices **/
					// capability.setCapability("udid", "86D84961-02E3-55E4-8E58-949297219A13");
					capability.setCapability("version", "10.3");
					seleniumAddress = "http://127.0.0.1:4723/wd/hub";
					break;
				default:
					break;
				}

				System.out.println("seleniumAddress: " + seleniumAddress);
				uniqueInstanceWebDriver = new RemoteWebDriver(new URL(seleniumAddress), capability);
				sessionId = ((RemoteWebDriver) uniqueInstanceWebDriver).getSessionId();
				System.out.println(sessionId.toString());
				uniqueInstanceWebDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

				// uniqueInstanceWebDriver.manage().window().maximize();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return uniqueInstanceWebDriver;
	}
}

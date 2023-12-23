package com.demo.qa.common;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.deser.SettableAnyProperty;

import net.bytebuddy.dynamic.TypeResolutionStrategy.Passive;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;

//import com.demo.qa.signup.*;

public class BaseSelenium {

	public ObjectRepositoryReader ObjectRepositoryReader;
	protected WebDriver driver;
	WebDriverWait wait;
	public HashMap<String, String> TestData = new HashMap<String, String>();
	public StringBuffer error_message = new StringBuffer();
	public BrowserMobProxy proxy;
	public Date TestCase_StartTime = new Date();

	protected enum LOCATOR_TYPE {
		ID, NAME, LINK, XPATH, JSSCRIPT, PARTIALLINK, CSS
	};

	public BaseSelenium() throws Exception {
		TestData.put("email", "");
		TestData.put("userid", "");
		TestData.put("EU", "-1");

	}

	static String workingDir = System.getProperty("user.dir");

	public static void initialize() throws Exception {
		try {
			if (!Constants.is_initialized) {
				Constants.prop.load(new FileInputStream(new File(workingDir + "/Environment.properties")));
				String env = System.getProperty("envVariable");
				System.out.println("Test Environment:- " + env);
				switch (env) {
					case "production":
						Constants.base_url = Constants.prop.getProperty("BaseURLProduction");
						break;

					case "PreProd":
						Constants.base_url = Constants.prop.getProperty("BaseURLPreProd");
						break;

					default:
						Constants.base_url = Constants.prop.getProperty("BaseURLPreProd");
						System.out.println(Constants.base_url);
						break;

				}
				System.out.println("Initialization Completed!");
			}
		} catch (Exception e) {
			System.out.println("Error during initialization, Hence Quittting");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void initializeVariable() throws Exception {
		try {
			if (!Constants.is_initialized_variable) {
				Constants.prop.load(new FileInputStream(new File(workingDir + "/Automation.properties")));
				Constants.browser = Constants.prop.getProperty("Browser");
				Constants.firefox_driver_path = workingDir + "/" + Constants.prop.getProperty("FirefoxDriverPath");
				Constants.chrome_driver_path = workingDir + "/" + Constants.prop.getProperty("ChromeDriverPath");
				Constants.explicit_wait_time = Integer.parseInt(Constants.prop.getProperty("Explicit_wait_time"));
				Constants.implicit_wait_time = Integer.parseInt(Constants.prop.getProperty("Implicit_wait_time"));
				System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
				System.setProperty("webdriver.gecko.driver", Constants.firefox_driver_path);
				System.setProperty("webdriver.chrome.driver", Constants.chrome_driver_path);
				Constants.is_initialized_variable = true;
			}
		} catch (Exception e) {
			System.out.println("Error during initialization of Variables, Hence Quittting");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void OpenBrowser() throws Exception {
		try {
			proxy = getProxyServer();
			Proxy seleniumProxy = getSeleniumProxy(proxy);
			if (Constants.browser.toLowerCase().equals("firefox")) {
				FirefoxOptions options = new FirefoxOptions();
				options.setCapability(CapabilityType.PROXY, seleniumProxy);
				driver = new FirefoxDriver(options);
			} else if (Constants.browser.toLowerCase().equals("chrome")) {
				ChromeOptions options = new ChromeOptions();
				// options.setCapability(CapabilityType.PROXY, seleniumProxy);
				options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				options.setCapability(ChromeOptions.CAPABILITY, true);
				options.addArguments("--ignore-certificate-errors");
				options.addArguments("--enable-automation");
				options.addArguments("--window-size=1920x1080");
				options.addArguments("--start-maximized");
				// options.addArguments("--headless");
				options.addArguments("--disable-gpu");
				options.addArguments("--disable-extensions");
				options.addArguments("--remote-debugging-port=9222");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-setuid-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--dns-prefetch-disable");
				options.addArguments("--disable-infobars");
				options.addArguments("log-level=3");
				options.addArguments(
						"user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.5993.117 Safari/537.36");
				options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				if (TestData.get("EU").equalsIgnoreCase("true")) {
					options.addExtensions(new File(workingDir + "/driver/extension_3_27_9_0.crx"));
				}
				options.setCapability("chrome-switches", Arrays.asList("--disable-extension", "--disable-logging",
						"--ignore-certificate-errors", "--log-level=0", "--silent", "--headless"));
				options.setCapability("silent", true);
				driver = new ChromeDriver(options);

			} else if (Constants.browser.toLowerCase().equals("ie")) {
				driver = new InternetExplorerDriver();
			} else {
				throw new Exception("Browser not supported");
			}
			driver.manage().timeouts().implicitlyWait(Constants.implicit_wait_time, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Constants.explicit_wait_time, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			proxy.newHar();
			driver.get(Constants.base_url);
			Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	public void click(String Locator, String LocatorType) throws Exception {
		switch (LOCATOR_TYPE.valueOf(LocatorType)) {
			case ID:
				driver.findElement(By.id(Locator)).click();
				break;

			case NAME:
				driver.findElement(By.name(Locator)).click();
				break;

			case XPATH:
				driver.findElement(By.xpath(Locator)).click();
				break;

			case CSS:
				driver.findElement(By.cssSelector(Locator)).click();
				break;

			case PARTIALLINK:
				driver.findElement(By.partialLinkText(Locator)).click();
				break;

			case LINK:
				driver.findElement(By.linkText(Locator)).click();
				break;

			default:
				throw new Exception("Invalid Locator Type/Locator Type may not be supported");
		}
	}

	public void type(String Locator, String LocatorType, String Value) throws Exception {
		switch (LOCATOR_TYPE.valueOf(LocatorType)) {
			case ID:
				driver.findElement(By.id(Locator)).sendKeys(Value);
				break;

			case NAME:
				driver.findElement(By.name(Locator)).sendKeys(Value);
				break;

			case XPATH:
				driver.findElement(By.xpath(Locator)).sendKeys(Value);
				break;

			case CSS:
				driver.findElement(By.cssSelector(Locator)).sendKeys(Value);
				break;

			default:
				throw new Exception("Invalid Locator Type/Locator Type may not be supported");
		}
	}

	public void clearDefaultText(String Locator, String LocatorType) throws Exception {
		switch (LOCATOR_TYPE.valueOf(LocatorType)) {
			case ID:
				driver.findElement(By.id(Locator)).clear();
				break;
			case NAME:
				driver.findElement(By.name(Locator)).clear();
				break;
			case XPATH:
				driver.findElement(By.xpath(Locator)).clear();
				break;
			case CSS:
				driver.findElement(By.cssSelector(Locator)).clear();
				break;
			default:
				throw new Exception("Invalid Locator Type/Locator Type may not be supported");
		}
	}

	// Checks if an element exists on the page identified by object locator
	public boolean checkElementExist(String Locator, String LocatorType) throws Exception {
		boolean ElementExists = false;
		switch (LOCATOR_TYPE.valueOf(LocatorType)) {
			case ID:
				ElementExists = driver.findElements(By.id(Locator)).size() != 0;
				break;

			case NAME:
				ElementExists = driver.findElements(By.name(Locator)).size() != 0;
				break;

			case XPATH:
				ElementExists = driver.findElements(By.xpath(Locator)).size() != 0;
				break;

			case CSS:
				ElementExists = driver.findElements(By.cssSelector(Locator)).size() != 0;
				break;
			default:
				throw new Exception("Invalid Locator Type/Locator Type may not be supported");
		}
		return ElementExists;
	}

	// Checks if an element is displayed on the page identified by object locator
	public boolean checkElementDisplayed(String Locator, String LocatorType) throws Exception {
		boolean ElementDisplayed = false;

		switch (LOCATOR_TYPE.valueOf(LocatorType)) {
			case ID:
				ElementDisplayed = driver.findElement(By.id(Locator)).isDisplayed();
				break;

			case NAME:
				ElementDisplayed = driver.findElement(By.name(Locator)).isDisplayed();
				break;

			case XPATH:
				ElementDisplayed = driver.findElement(By.xpath(Locator)).isDisplayed();
				break;

			case CSS:
				ElementDisplayed = driver.findElement(By.cssSelector(Locator)).isDisplayed();
				break;
			default:
				throw new Exception("Invalid Locator Type/Locator Type may not be supported");
		}
		return ElementDisplayed;
	}

	public String getText(String Locator, String LocatorType) throws Exception {

		String TextMessage = null;
		switch (LOCATOR_TYPE.valueOf(LocatorType)) {

			case ID:
				TextMessage = driver.findElement(By.id(Locator)).getText().toString();
				break;

			case NAME:
				TextMessage = driver.findElement(By.name(Locator)).getText().toString();
				break;

			case XPATH:
				TextMessage = driver.findElement(By.xpath(Locator)).getText().toString();
				break;

			case CSS:
				TextMessage = driver.findElement(By.cssSelector(Locator)).getText().toString();
				break;

			default:
				throw new Exception("Invalid Locator Type/Locator Type may not be supported");
		}
		return TextMessage.trim();
	}

	public void waitUntilElementClickable(String Locator, String LocatorType, int WaitTime) throws Exception {
		wait = new WebDriverWait(driver, WaitTime);
		switch (LOCATOR_TYPE.valueOf(LocatorType)) {

			case ID:
				wait.until(ExpectedConditions.elementToBeClickable(By.id(Locator)));
				break;

			case NAME:
				wait.until(ExpectedConditions.elementToBeClickable(By.name(Locator)));
				break;

			case XPATH:
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Locator)));
				break;

			case CSS:
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(Locator)));
				break;

			default:
				throw new Exception("Invalid Locator Type/Locator Type may not be supported");
		}
	}

	public void refresh() throws Exception {
		driver.navigate().refresh();
	}

	public void waitUntilElementVisible(String Locator, String LocatorType, int WaitTime) throws Exception {
		wait = new WebDriverWait(driver, WaitTime);
		switch (LOCATOR_TYPE.valueOf(LocatorType)) {

			case ID:
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator)));
				break;

			case NAME:
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator)));
				break;

			case XPATH:
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator)));
				break;

			case CSS:
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(Locator)));
				break;

			default:
				throw new Exception("Invalid Locator Type/Locator Type may not be supported");
		}
	}

	public BrowserMobProxy getProxyServer() {
		BrowserMobProxy proxy = new BrowserMobProxyServer();
		proxy.setTrustAllServers(true);
		proxy.start();
		return proxy;
	}

	public Proxy getSeleniumProxy(BrowserMobProxy proxyServer) {
		Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxyServer);
		try {
			System.setProperty("jsse.enableSNIExtension", "false");
			String hostIp = Inet4Address.getLocalHost().getHostAddress();
			seleniumProxy.setHttpProxy(hostIp + ":" + proxyServer.getPort());
			seleniumProxy.setSslProxy(hostIp + ":" + proxyServer.getPort());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Assert.fail("invalid Host Address");
		}
		return seleniumProxy;
	}

	public void stopProxyServer() throws Exception {
		proxy.stop();
	}
	public String getCurrentURL() throws Exception {
		return driver.getCurrentUrl();
	}
}

package com.demo.qa.common;

import com.itextpdf.text.log.SysoCounter;

import java.awt.Image;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.NeedsLocalLogs;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import com.mysql.cj.ParseInfo;
import com.mysql.cj.jdbc.ha.ReplicationMySQLConnection;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import io.qameta.allure.Attachment;
import ru.qatools.properties.providers.SysPropPathReplacerProvider;

public class ApplicationLibrary extends BaseSelenium {

	public ObjectRepositoryReader obj;
	public String ObjectArray[];

	public ApplicationLibrary() throws Exception {
		super();
	}

	public void getScenario(cucumber.api.Scenario scenarios) {
		String presentscenario = scenarios.getId().split(";")[0];
		System.out.println(presentscenario);
		ObjectRepositoryReader obj = new ObjectRepositoryReader();
		try {
			switch (presentscenario) {
			case "verify-online-java-compiler-website-component":
				obj.readWebObjects("DemoLocators");
				this.obj = obj;
				break;
			default:
				System.out.println("Invalid scenario");
				fail("Invalid scenario");

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Attachment(value = "Pass", type = "text/plain")
	public String pass(String message) {
		String output = message;
		return output;
	}

	@Attachment(value = "Fail", type = "text/plain")
	public String fail(String message) {
		System.out.println(message);
		String output = message;
		takeScreenShotShutterburg();
		return output;
	}

	@Attachment(value = "Warning", type = "text/plain")
	public String warning(String message) {
		String output = message;
		return output;
	}

	public void fail(String message, boolean fail) throws Exception {
		if (fail) {
			fail(message);
			throw new Exception(message);
		} else {
			fail(message);
		}
	}

	public String replace(String Data) throws ParseException, Exception {
		String Temp = Data;
		Temp = Temp.replace("$USER_ID", TestData.get("userid"));
		Temp = Temp.replace("$EMAIL_ID", TestData.get("email"));
		return Temp;
	}

	public void launchBrowser() throws Exception {
		OpenBrowser();
	}

	public boolean compareErrorMessage(String ActualMessage, String ExpectedMessage) {
		if (ActualMessage != null) {
			if (ActualMessage.equals(ExpectedMessage))
				return true;
			else {
				error_message.append("Expected Result is not Matching with Actual Result=" + ActualMessage
						+ " Expected Message=" + ExpectedMessage);
				return false;
			}
		} else {
			error_message.append("Actual Error Message is null");
			return false;
		}
	}					

	public void closeBrowser() {
		driver.close();
	}

	@Attachment
	public byte[] attachScreenShotToAllure(byte[] snapshot) {
		return snapshot;
	}

	public void takeScreenShotShutterburg() {
		Shutterbug.shootPage(driver).withName("Screenshot1").save();
		// save(System.getProperty("user.dir")+File.separator+"//ScreenShot1.png").w
		File file = new File(
				System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + "Screenshot1.png");
		try {
			// Reading a Image file from file system
			FileInputStream imageInFile = new FileInputStream(file);
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			attachScreenShotToAllure(imageData);
			imageInFile.close();
			System.out.println("Image Successfully Manipulated!");
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
	}
	@Attachment
	public String attachment(String harlogs) {
		return harlogs;
	}

	public boolean headingText(String ExpectedMessage) throws Exception {
		ObjectArray = obj.getObjectArray("OnlineJavaCompilerIDEText");
		waitUntilElementVisible(ObjectArray[3], ObjectArray[2], Constants.explicit_wait_time);
		String ActualMessage = getText(ObjectArray[3], ObjectArray[2]);
		pass("ActualMessage  :  " + ActualMessage + "");
		pass("ExpectedMessage  :  " + ExpectedMessage + "");
		pass("Verified Heading Text Successfully");
		return (compareErrorMessage(ActualMessage, ExpectedMessage));

	}

	public boolean MenuText(String ExpectedMessage) throws Exception {
		ObjectArray = obj.getObjectArray("ProductsText");
		waitUntilElementVisible(ObjectArray[3], ObjectArray[2], Constants.explicit_wait_time);
		String ActualMessage = getText(ObjectArray[3], ObjectArray[2]);
		pass("ActualMessage  :  " + ActualMessage + "");
		pass("ExpectedMessage  :  " + ExpectedMessage + "");
		pass("Verified Product Text Successfully");
		return (compareErrorMessage(ActualMessage, ExpectedMessage));

	}

	public boolean Text1(String ExpectedMessage) throws Exception {
		ObjectArray = obj.getObjectArray("PricingText");
		waitUntilElementVisible(ObjectArray[3], ObjectArray[2], Constants.explicit_wait_time);
		String ActualMessage = getText(ObjectArray[3], ObjectArray[2]);
		pass("ActualMessage  :  " + ActualMessage + "");
		pass("ExpectedMessage  :  " + ExpectedMessage + "");
		pass("Verified Pricing Text Two Successfully");
		return (compareErrorMessage(ActualMessage, ExpectedMessage));

	}
	public boolean Text2(String ExpectedMessage) throws Exception {
		ObjectArray = obj.getObjectArray("ExternalLibrariesText");
		waitUntilElementVisible(ObjectArray[3], ObjectArray[2], Constants.explicit_wait_time);
		String ActualMessage = getText(ObjectArray[3], ObjectArray[2]);
		pass("ActualMessage  :  " + ActualMessage + "");
		pass("ExpectedMessage  :  " + ExpectedMessage + "");
		pass("Verified External Libraries Text Successfully");
		return (compareErrorMessage(ActualMessage, ExpectedMessage));

	}
	public boolean Text3(String ExpectedMessage) throws Exception {
		ObjectArray = obj.getObjectArray("OutputText");
		waitUntilElementVisible(ObjectArray[3], ObjectArray[2], Constants.explicit_wait_time);
		String ActualMessage = getText(ObjectArray[3], ObjectArray[2]);
		pass("ActualMessage  :  " + ActualMessage + "");
		pass("ExpectedMessage  :  " + ExpectedMessage + "");
		pass("Verified Output Text Successfully");
		return (compareErrorMessage(ActualMessage, ExpectedMessage));

	}
	public boolean Text4(String ExpectedMessage) throws Exception {
		ObjectArray = obj.getObjectArray("FontSizeText");
		waitUntilElementVisible(ObjectArray[3], ObjectArray[2], Constants.explicit_wait_time);
		String ActualMessage = getText(ObjectArray[3], ObjectArray[2]);
		pass("ActualMessage  :  " + ActualMessage + "");
		pass("ExpectedMessage  :  " + ExpectedMessage + "");
		pass("Verified Font Size Text Successfully");
		return (compareErrorMessage(ActualMessage, ExpectedMessage));

	}
	public void clickOnFontSizeDropdown() throws Exception {
		ObjectArray = obj.getObjectArray("FontSizeButton");
		waitUntilElementClickable(ObjectArray[3], ObjectArray[2], Constants.explicit_wait_time);
		click(ObjectArray[3], ObjectArray[2]);
		pass("Clicked on Dropdown Button Successfully");
	}
	public void choose18PxResolution() throws Exception {
		ObjectArray = obj.getObjectArray("Choose18Px");
		waitUntilElementClickable(ObjectArray[3], ObjectArray[2], Constants.explicit_wait_time);
		click(ObjectArray[3], ObjectArray[2]);
		pass("Clicked on 18Px Successfully");
	}
	public void clickOnExecuteButton() throws Exception {
		ObjectArray = obj.getObjectArray("ExecuteButton");
		waitUntilElementClickable(ObjectArray[3], ObjectArray[2], Constants.explicit_wait_time);
		click(ObjectArray[3], ObjectArray[2]);
		pass("Clicked on Execute Button Successfully");
	}
	public boolean outputResult(String ExpectedMessage) throws Exception {
		Thread.sleep(10000);
		ObjectArray = obj.getObjectArray("OutputResult");
		waitUntilElementVisible(ObjectArray[3], ObjectArray[2], Constants.explicit_wait_time);
		String ActualMessage = getText(ObjectArray[3], ObjectArray[2]);
		pass("ActualMessage  :  " + ActualMessage + "");
		pass("ExpectedMessage  :  " + ExpectedMessage + "");
		pass("Verified Output Result Successfully");
		return (compareErrorMessage(ActualMessage, ExpectedMessage));

	}
	public boolean ProcessingResult(String ExpectedMessage) throws Exception {
		ObjectArray = obj.getObjectArray("OutputResult");
		waitUntilElementVisible(ObjectArray[3], ObjectArray[2], Constants.explicit_wait_time);
		String ActualMessage = getText(ObjectArray[3], ObjectArray[2]);
		pass("ActualMessage  :  " + ActualMessage + "");
		pass("ExpectedMessage  :  " + ExpectedMessage + "");
		pass("Verified Processing result Successfully");
		return (compareErrorMessage(ActualMessage, ExpectedMessage));

	}
}

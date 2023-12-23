package com.demo.qa.common;

import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Attachment;

public class CustomException extends Exception {
	
	WebDriver d;
	
	public CustomException()
	{
		
	}
	
	public CustomException(WebDriver driver,String Message,Throwable e)
	{
		super(Message,e);
		d=driver;
		screenShot();
		Assert.fail(Message);
	}
	
	@Attachment
	public byte[] screenShot()
	{
		byte[] screenshot = ((TakesScreenshot)d).getScreenshotAs(OutputType.BYTES);
		return screenshot;
	}


}

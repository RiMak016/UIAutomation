package com.demo.qa.common;
import com.demo.qa.common.ApplicationLibrary;

import java.io.IOException;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public  class Hooks   {
	
	public ApplicationLibrary Shutdownhook;
	public ObjectRepositoryReader obj;

	
	public Hooks(ApplicationLibrary app)
	{
		this.Shutdownhook=app;
	}
	
	@Before
	public void beforeLaunch(Scenario scenario) throws Exception{
		if(!Constants.is_initialized)
		{
			BaseSelenium.initialize();
			BaseSelenium.initializeVariable();
		//	Shutdownhook.createnewUser(Constants.global_email, Constants.global_phonenumber, Constants.global_password,Constants.global_country_code);
		//	Shutdownhook.closeBrowser();
		}
	System.out.println("-----------------------Scenario Name="+scenario.getName()+" Started-----------------");
	Shutdownhook.getScenario(scenario);
	}
	
	@After
	public void afterExecution(Scenario scenario) throws Exception {
		System.out.println("------------------------Scenario Name="+scenario.getName()+" Ended Status="+scenario.getStatus()+" --------------------");
		if(scenario.isFailed())
		{
		Shutdownhook.stopProxyServer();
		Shutdownhook.takeScreenShotShutterburg();
		}
		Shutdownhook.closeBrowser();
	}
}


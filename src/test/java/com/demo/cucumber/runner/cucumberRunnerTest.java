package com.demo.cucumber.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

@RunWith(Cucumber.class)
// @RunWith(ExtendedCucumber.class)

@CucumberOptions(plugin = { "io.qameta.allure.cucumberjvm.AllureCucumberJvm" }, features = {

		"src/test/java/FeatureFiles/Demo/Demo.Feature",

}, glue = { "com.demo.qa.demo.stepdefinitions", "com.demo.qa.common" }, monochrome = true)
// @ExtendedCucumberOptions(jsonReport="target/cucumber.json",retryCount = 1)
public class cucumberRunnerTest {

	/**
	 * @BeforeClass public static void before() { System.out.println("Inside Junit
	 *              Before class"); }
	 * 
	 * @AfterClass public static void after() { System.out.println("Inside Junit
	 *             after class"); }
	 **/

}

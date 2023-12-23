package com.demo.qa.demo.stepdefinitions;

import com.demo.qa.common.ApplicationLibrary;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import junit.framework.Assert;

public class DemoJdoddle {

	ApplicationLibrary app;

	public DemoJdoddle(ApplicationLibrary app) {
		this.app = app;
	}

	@Given("^user navigates to jdoddle website$")
	public void user_navigates_to_jdoddle_website() {
		try {
			app.launchBrowser();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	@Then("^Verify Heading Text \"([^\"]*)\"$")
	public void Check_For_Heading_Text_1(String ExpectedMessage) {
		try {

			if (!app.headingText(ExpectedMessage))
				Assert.fail(app.error_message.toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	@Then("^Verify Menu Text \"([^\"]*)\"$")
	public void Verify_Menu_Text(String ExpectedMessage) {
		try {

			if (!app.MenuText(ExpectedMessage))
				Assert.fail(app.error_message.toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	@Then("^Verify Text1 \"([^\"]*)\"$")
	public void Verify_Text1(String ExpectedMessage) {
		try {

			if (!app.Text1(ExpectedMessage))
				Assert.fail(app.error_message.toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	@Then("^Verify Text2 \"([^\"]*)\"$")
	public void Verify_Text2(String ExpectedMessage) {
		try {

			if (!app.Text2(ExpectedMessage))
				Assert.fail(app.error_message.toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	@Then("^Verify Text3 \"([^\"]*)\"$")
	public void Verify_Text3(String ExpectedMessage) {
		try {

			if (!app.Text3(ExpectedMessage))
				Assert.fail(app.error_message.toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	@Then("^Verify Text4 \"([^\"]*)\"$")
	public void Verify_Text4(String ExpectedMessage) {
		try {

			if (!app.Text4(ExpectedMessage))
				Assert.fail(app.error_message.toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	@And("^Click on Font Size button$")
	public void Click_on_Font_Size_button() {
		try {
			app.clickOnFontSizeDropdown();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	@Then("^Choose 18Px Resolution$")
	public void Choose_18Px_Resolution() {
		try {
			app.choose18PxResolution();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	@And("^click on Execute Button$")
	public void click_on_Execute_Button() {
		try {
			app.clickOnExecuteButton();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	@Then("^Verify the Processing Text before showing the Result \"([^\"]*)\"$")
	public void Verify_the_Processing_Text_before_showing_the_Result(String ExpectedMessage) {
		try {

			if (!app.ProcessingResult(ExpectedMessage))
				Assert.fail(app.error_message.toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	@Then("^Verify The output Result \"([^\"]*)\"$")
	public void Verify_The_output_Result(String ExpectedMessage) {
		try {

			if (!app.outputResult(ExpectedMessage))
				Assert.fail(app.error_message.toString());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}


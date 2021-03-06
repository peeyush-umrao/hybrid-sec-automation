package com.hybridsec.telepath.testcases;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hybridsec.telepath.base.TestBase;
import com.hybridsec.telepath.utils.TestUtility;

public class Dashboard_TC2 extends TestBase {
	
	
	@BeforeTest
	public void isSkipped(){
		
		if(TestUtility.isSkip(this.getClass().getSimpleName()))
			throw new SkipException("Runmode set to No");
	}
	
	
	
	@Test
	public void testDashboard_TC2(){
		
		// Logging into the application
		Reporter.log("Logging into the application\n");
		TestUtility.login(config.getProperty("username"), config.getProperty("password"));
		
		// Waiting for the user to login into the application
		Reporter.log("Waiting for the user to login into the application\n");
		Assert.assertTrue(TestUtility.isObjPresent("BTN_Logout_LANDINGPAGE", 30), "Aplication is taking time for loading - Performace Issues");
		
		// User is successfully Login and Logout of the application
		Reporter.log("User is successfully Login and Logout of the application\n");
		Assert.assertTrue(TestUtility.click("BTN_Logout_LANDINGPAGE"), "Click on Logout button is not successful");
		
	}

}

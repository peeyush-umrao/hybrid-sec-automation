package com.hybridsec.telepath.testcases;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.hybridsec.telepath.base.TestBase;
import com.hybridsec.telepath.utils.TestUtility;

public class Dashboard_TC2 extends TestBase {
	
	
	@Test
	public void testDashboard_TC2(){
		
		// Logging into the application
		Reporter.log("Logging into the application\n");
		TestUtility.login(config.getProperty("username"), config.getProperty("password"));
	}

}

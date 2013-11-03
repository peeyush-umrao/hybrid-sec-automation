package com.hybridsec.telepath.testcases;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hybridsec.telepath.base.TestBase;
import com.hybridsec.telepath.utils.TestUtility;

public class LoginTestCase extends TestBase {
	
	
	@BeforeTest
	public void isSkipped(){
		
		if(TestUtility.isSkip(this.getClass().getSimpleName()))
			throw new SkipException("Runmode set to No");
	}
	
	
	@Test(dataProvider = "getData")
	public void testLoginTestCase(String strScenarioType, String strUsername, String strPassword, String strExpectedResult){
		
		// Logging into the application
		Reporter.log("Logging into the application\n");
		TestUtility.login(strUsername, strPassword);
		
		if(strScenarioType.equalsIgnoreCase("ValidLoginCredentials") || strScenarioType.equalsIgnoreCase("CopyAndPasteInPasswordField")){
			
			// Waiting for the user to login into the application
			Reporter.log("Waiting for the user to login into the application\n");
			Assert.assertTrue(TestUtility.isObjPresent("BTN_Logout_LANDINGPAGE", 30), "Aplication is taking time for loading - Performace Issues");
			
			// User is successfully Login and Logout of the application
			Reporter.log("User is successfully Login and Logout of the application\n");
			Assert.assertTrue(TestUtility.click("BTN_Logout_LANDINGPAGE"), "Click on Logout button is not successful");
			
			
		}else if(strScenarioType.equalsIgnoreCase("InvalidLoginCredentials") || strScenarioType.equalsIgnoreCase("CorrectUsernameWrongPassword") 
				|| strScenarioType.equalsIgnoreCase("CorrrectPasswodWrongUsernames") || strScenarioType.equalsIgnoreCase("ValidationOnUsernameLength") 
				|| strScenarioType.equalsIgnoreCase("ValidationOnPasswordLength") || strScenarioType.equalsIgnoreCase("ValidCredentialsWithSpace") 
				|| strScenarioType.equalsIgnoreCase("ValidCredentialsWithStringDataType")){
			
			
			// Waiting for the 'Login Failed' message
			Reporter.log("Waiting for the 'Login Failed' message");
			Assert.assertTrue(TestUtility.isObjPresent("TXT_LoginFailed_HOMEPAGE", 30), "'Login Failed' message has not appeared on the screen - Time out after 30 sec");
			
			
			// Validating the 'Login Failed' error message
			Reporter.log("Validating the 'Login Failed' error message");
			Assert.assertTrue(getObject("TXT_LoginFailed_HOMEPAGE").getText().equalsIgnoreCase(strExpectedResult), "Login Failed message is not displayed on the screen");
			
			
		}else if(strScenarioType.equalsIgnoreCase("NoCredentials") || strScenarioType.equalsIgnoreCase("OnlyUsername") 
				|| strScenarioType.equalsIgnoreCase("OnlyPassword")){
			
			
			// Waiting for 'Missing username or password' error message
			Reporter.log("Waiting for 'Missing username or password' error message");
			Assert.assertTrue(TestUtility.isObjPresent("TXT_MissingUsernamePassword_HOMEPAGE", 30), "'Missing username or password' message has not appeared on the screen - Time out after 30 sec");
			
			// Validating the 'Missing username or password' error message
			Reporter.log("Validating the 'Missing username or password' error message");
			Assert.assertTrue(getObject("TXT_MissingUsernamePassword_HOMEPAGE").getText().equalsIgnoreCase(strExpectedResult), "'Missing username or password' error message is not displayed on the screen");
		}
		
		
	}
	
	
	@DataProvider
	public Object[][] getData(){
		
		return TestUtility.getData(this.getClass().getSimpleName());
	}

}

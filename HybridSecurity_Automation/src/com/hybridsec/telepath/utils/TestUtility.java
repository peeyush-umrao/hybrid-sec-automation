package com.hybridsec.telepath.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.hybridsec.telepath.base.TestBase;

public class TestUtility extends TestBase {
	
	
	
	/**
	 * This method is used to login into the Hybrid Security Telepath application
	 * @param String userName
	 * @param String passWord
	 */
	public static void login(String userName, String passWord){
		
		try{
			driver.navigate().to(config.getProperty("applicationURL"));
			
			// Waiting for Login button to Present
			Assert.assertTrue(isObjPresent("BTN_Login_HOMEPAGE", 30), "Home Page took more than 30 seconds to load and Login button is not displayed");
			
			
			// Login into the application
			Assert.assertTrue(setText("WEDIT_Username_HOMEPAGE", userName), "User is not able to Enter Username in the edit box");
			Assert.assertTrue(setText("WEDIT_Password_HOMEPAGE", passWord), "User is not able to Enter Password in the edit box");
			Assert.assertTrue(click("BTN_Login_HOMEPAGE"), "User is not able to click on Login button");
			
		}catch(Exception e){
			
			e.printStackTrace();
			Assert.fail("Code is failing in the Login function");
		}
	}
	
	
	
	/**This method is used to check the Run mode of Test case in the Excel Sheet.
	 * 
	 * @param testCase It will compare the testCase name in the Excel Sheet and check its Run mode
	 * @return True/False (True: If the Run mode is No, False: If the Run mode is Yes) 				
	 */
	public static boolean isSkip(String testCase) {
		
		boolean flag = true;
		for(int i=2; i<=datatable.getRowCount("Test Cases"); i++ ){
			
			if(datatable.getCellData("Test Cases", "TCID", i).equalsIgnoreCase(testCase)){
				
				if(datatable.getCellData("Test Cases", "Runmode", i).equalsIgnoreCase("Y")){
					
					flag = false;
					break;
					
				}else{
					
					flag = true;
					break;
				}	
			}
		}
		
		return flag;
	}
	
	
	
	/**This method is used to getData from the Excel Sheet and store that in the 2D Object Array.
	 * 
	 * @param sheetName The name of the Sheet from where data is fetching - Controller.xlsx	
	 * @return 2D Object array
	 */
	public static Object[][] getData(String sheetName) {
		
		int rows = datatable.getRowCount(sheetName)-1;
		
		if(rows <= 0){
			
			Object[][] testData = new Object[1][0];
			return testData;
			
		}
		
		rows = datatable.getRowCount(sheetName);
		
		int cols = datatable.getColumnCount(sheetName);
		
		System.out.println("total rows -- "+ rows);
		System.out.println("total cols -- "+cols);
		Object data[][] = new Object[rows-1][cols];
		
		for(int rowNum=2; rowNum<=rows; rowNum++){
			
			for(int colNum=0; colNum<cols; colNum++){
				
				data[rowNum-2][colNum] = datatable.getCellData(sheetName, colNum, rowNum);
			}
		}
		
		return data;
	}
	
	
	
	/**
	 * This method is used to verify that the element is present/displayed on the screen or not
	 * @param String xpathKey
	 * @return boolean true/false
	 */
	public static boolean isElementPresent(String xpathKey){
		
		try{
			WebElement webElement = getObject(xpathKey);
			if(!(webElement == null)){
				
				return true;
				
			}else{
				
				return false;
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	/**
	 * This method is used to click on an object
	 * @param String xpathKey
	 * @return boolean true/false
	 */
	public static boolean click(String xpathKey){
		
		try{
			boolean elementPresent;
			
			// Check the element currently present or not
			elementPresent = isElementPresent(xpathKey);
			
			if(elementPresent == true){
				
				getObject(xpathKey).click();
				return true;
				
			}else{
				
				return false;
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	/**
	 * This method is used to set text/content inside the text box
	 * @param String xpathKey
	 * @param String value
	 * @return boolean true/false
	 */
	public static boolean setText(String xpathKey, String value){
		
		try{
			boolean elementPresent;
			
			// Check the element currently present or not
			elementPresent = isElementPresent(xpathKey);
			
			if(elementPresent == true){
				
				// If this element is a text entry element, this will clear the value
	            getObject(xpathKey).clear();
	            
	            // Typing into the text field, which will set its value
	            getObject(xpathKey).sendKeys(value);                          
	            return true;
	            
			}else{
				
				return false;
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	/**
	 * This method is used to wait for the object to load on the screen for the
	 * time period/seconds which is passed to this method as a argument
	 * @param String xpathKey
	 * @param int intSeconds
	 * @return boolean true/false
	 */
	public static boolean isObjPresent(String xpathKey, int intSeconds){
		
		boolean flag = false;
		for(int iCount=0; iCount<=intSeconds; iCount++){
			
			try{
				Thread.sleep(1000);
				if(isElementPresent(xpathKey)){
					
					flag = true;
					break;
					
				}else{
					
					flag = false;
				}
				
			}catch(Exception e){
				
				e.printStackTrace();
				flag = false;
			}
		}
		
		return flag;
	}
	
	
	
	
	/**
	 * This method is used to select value from the drop down
	 * @param String xpathKey
	 * @param String value
	 * @return boolean true/false
	 */
	public static boolean selectValueFromDropDown(String xpathKey, String value){
		
		try{
			Select element = new Select(getObject(xpathKey));
			element.selectByVisibleText(value);
			return true;
			
		}catch(Exception e){
			
			e.printStackTrace();
			return false;
		}
	}

}

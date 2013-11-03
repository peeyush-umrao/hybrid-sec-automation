package com.hybridsec.telepath.base;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.hybridsec.telepath.utils.MailService_API;
import com.hybridsec.telepath.utils.Xls_Reader;


public class TestBase {
	
	public static Properties config = null;
	public static Properties OR = null;
	public WebDriver wbDv = null;
	public static EventFiringWebDriver driver = null;
	public static boolean loggedIn = false;
	public static Xls_Reader datatable = null;
	
	
	/**
	 * This method used to initialize the configuration values such as config file,
	 * Object Repository and Launching the browser
	 * We have used the TestNG annotation for this method, hence this method will 
	 * execute once before Automation Suite
	 */
	@BeforeSuite
	public void initialize(){
		
		// loading all the configuration values
		try{
			config = new Properties();
			FileInputStream fp = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\hybridsec\\telepath\\config\\config.properties");
			config.load(fp);
			
			// loading Objects Repository
			OR = new Properties();
			fp = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\hybridsec\\telepath\\config\\OR.properties");
			OR.load(fp);
			
			datatable = new Xls_Reader(System.getProperty("user.dir")+"\\src\\com\\hybridsec\\telepath\\xls\\Controller.xlsx");
			
			// checking the type of browser and launching the browser
			if(config.getProperty("browserType").equalsIgnoreCase("Firefox")){
				
				wbDv = new FirefoxDriver();
				
			}else if(config.getProperty("browserType").equalsIgnoreCase("IE")){
				
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\OtherUtilities\\IEDriverServer.exe");
				wbDv = new InternetExplorerDriver();
				
			}else if(config.getProperty("browserType").equalsIgnoreCase("Chrome")){
				
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\OtherUtilities\\chromedriver.exe");
				wbDv = new ChromeDriver();
			}
			
			driver = new EventFiringWebDriver(wbDv);
			
			// Putting an implicit wait after every Action or Event
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			// Maximize the browser window
			driver.manage().window().maximize();
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * This method reads the element from the screen based on its Xpath
	 * @param WebDriver driver
	 * @param String xpathKey
	 * @return WebElement
	 */
	public static WebElement getObject(String xpathKey){
		
		WebElement obj = null;
		obj = driver.findElement(By.xpath(OR.getProperty(xpathKey)));
		return obj;
	}
	
	
	
	
	/**
	 * This method executes once in the end after the completion of Automation Suite
	 * We are using the TestNG annotation here, so that it will call once in the end
	 */
	@AfterSuite
	public void closeBrowser(){
		
		try{
			driver.close();
			Thread.sleep(5000);
			//MailService_API.zip(System.getProperty("user.dir")+"\\test-output\\Hybrid Security Telepath Test Suite");
			//MailService_API.Email(config.getProperty("sendTo"), config.getProperty("sendCC"), config.getProperty("sendBCC"), config.getProperty("sendMailFrom"), config.getProperty("sendMailPassword"));
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		
		
	}
	

}

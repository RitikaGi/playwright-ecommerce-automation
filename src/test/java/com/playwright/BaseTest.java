package com.playwright;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.ConfigReader;

public class BaseTest {
	
	private Playwright playwright;
	private Browser browser;
	private BrowserContext context;
	protected Page page;
	
	protected LoginPage loginPage;
	protected InventoryPage inventoryPage;
	protected CartPage cartPage;
	
	
	
		@BeforeMethod
		public void setUp() {
			playwright = Playwright.create();
			try {
				browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
			}
			catch(Exception e) {
				browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			}
			
			context = browser.newContext();
			
			context.tracing().start(new Tracing.StartOptions()
					.setScreenshots(true)
					.setSnapshots(true)
					.setSources(true));
			
			page = context.newPage();
			
			loginPage = new LoginPage(page);
			inventoryPage= new InventoryPage(page);
			cartPage = new CartPage(page);
		}	
		
		protected void login() {
			String url = ConfigReader.getProperty("baseUrl");
	        String username = ConfigReader.getProperty("username");
	        String password = ConfigReader.getProperty("password");
			//Login
			loginPage.login(url, username, password);
		}
		
		
		@AfterMethod
		public void tearDown(ITestResult result) {
			if(result.getStatus()==ITestResult.FAILURE) {
			String testName = result.getMethod().getMethodName();
			//add date and time in the tracefile
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			//create screenshot directory
			String screenshotDir = "test-screenshots";
			new File(screenshotDir).mkdirs();
			
			String screenshotName = screenshotDir+"/"+testName+"-"+timeStamp+".png";
			//take screenshot
			page.screenshot(new Page.ScreenshotOptions()
					.setPath(Paths.get(screenshotName)));
			
			//create trace directory
			String traceDir = "test-traces";
			new File(traceDir).mkdirs();
			
			String traceFileName = traceDir+"/trace-"+testName+timeStamp+".zip";
			
			context.tracing().stop(new Tracing.StopOptions()
					.setPath(Paths.get(traceFileName)));
			}
			else {
				context.tracing().stop();
			}
			if(browser!=null) browser.close();
			if(playwright!=null) playwright.close();
		}
		
		
	

}

package com.playwright;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BaseTest {
	private Playwright playwright;
	private Browser browser;
	protected Page page;
	protected String url = "https://www.saucedemo.com/";
	protected String username = "standard_user";
	protected String password = "secret_sauce";
	
	
		@BeforeMethod
		public void setUp() {
			playwright = Playwright.create();
			try {
				browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
			}
			catch(Exception e) {
				browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			}
			page = browser.newPage();
			
		}	
		
		protected void login() {
			//Login
			page.navigate(url);
			page.locator("#user-name").fill(username);
			page.locator("#password").fill(password);
			page.locator(".submit-button").click();
		}
		
		
		@AfterMethod
		public void tearDown() {
			if(browser!=null) browser.close();
			if(playwright!=null) playwright.close();
		}
		
		
	

}

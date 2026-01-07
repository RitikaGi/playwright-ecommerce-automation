package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import io.qameta.allure.Allure;
import pages.CartPage;
import pages.CheckoutPage;
import pages.FooterPage;
import pages.HeaderPage;
import pages.InventoryPage;
import pages.LoginPage;
import pages.NavigationPage;
import pages.ProductPage;
import utils.TestData.CheckoutData;
import utils.TestData.Products;
import utils.TestData.Users;
import utils.ConfigReader;


public class BaseTest {
	
	private Playwright playwright;
	private Browser browser;
	private BrowserContext context;
	protected Page page;
	protected ProductPage productPage;
	protected LoginPage loginPage;
	protected InventoryPage inventoryPage;
	protected CartPage cartPage;
	protected static final String BASE_URL = ConfigReader.getProperty("baseUrl");
	protected boolean skipLogin = false;
	protected HeaderPage headerPage;
	protected CheckoutPage checkoutPage;
	protected NavigationPage navigationPage;
	protected FooterPage footerPage;
	
	
		@BeforeMethod
		public void setUp() {
			boolean headless = ConfigReader.isHeadless();
			String browserChannel = ConfigReader.getProperty("browser");
			
			playwright = Playwright.create();
			try {
				browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
						.setChannel(browserChannel).setHeadless(headless));
			}
			catch(Exception e) {
				browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
						.setHeadless(headless));
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
			productPage = new ProductPage(page);
			headerPage = new HeaderPage(page);
			checkoutPage = new CheckoutPage(page);
			navigationPage = new NavigationPage(page);
			footerPage = new FooterPage(page);
			
			if(!skipLogin) {
				performLogin();
			}
		}	
		
		protected void performLogin() {
	        loginPage.login(BASE_URL, Users.STANDARD_USER.getUsername(), Users.STANDARD_USER.getPassword());
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
			byte[] screenshotBytes=page.screenshot(new Page.ScreenshotOptions()
					.setPath(Paths.get(screenshotName)));
			//Attach screenshot to allure report
			Allure.addAttachment(testName+"-Screenshot","image/png", 
					new java.io.ByteArrayInputStream(screenshotBytes), ".png");
			
			//create trace directory
			String traceDir = "test-traces";
			new File(traceDir).mkdirs();
			
			String traceFileName = traceDir+"/trace-"+testName+timeStamp+".zip";
			
			context.tracing().stop(new Tracing.StopOptions()
					.setPath(Paths.get(traceFileName)));
			//Attach trace to allure reports
			try {
				Allure.addAttachment(testName+"-Playwright Trace", "application/zip",
						new FileInputStream(traceFileName),".zip");
			}
			catch(IOException e){
				e.printStackTrace();
			}
			// NEW: Add failure reason to Allure
	        if(result.getThrowable() != null) {
	            Allure.addAttachment("Failure Reason", "text/plain", 
	                result.getThrowable().getMessage());
	        }
	    }
	    else {
	        context.tracing().stop();
	    }
	    
	    // NEW: Add test execution time
	    long duration = result.getEndMillis() - result.getStartMillis();
	    Allure.addAttachment("Execution Time", "text/plain", 
	        duration + " ms (" + (duration/1000.0) + " seconds)");
			
			if(browser!=null) browser.close();
			if(playwright!=null) playwright.close();
		}
		
		protected void addProductsToCart(Products... products) {
			for(Products product: products) {
				inventoryPage.addToCartByProductName(product.getName());
			}
		}
		
		protected Map<String, Map<String,String>> addProductsToCartandDetails(Products...products){
			Map<String, Map<String,String>> allDetails = new HashMap<>();
			for(Products product: products) {
				 Map<String,String> productDetails = inventoryPage.getProductDetailsFromList(product.getName());
				 allDetails.put(product.getName(), productDetails);
				 inventoryPage.addToCartByProductName(product.getName());
			}
			return allDetails;
		}
	
       protected void navigateToCart() {
    	   headerPage.clickCartIcon();
       }
       
       protected void navigateToCheckout() {
    	   headerPage.clickCartIcon();
    	   cartPage.clickOnCheckoutButton();
       }
       
       protected void addToCartAndNavigateToCheckout(Products...products) {
    	   addProductsToCart(products);
    	   navigateToCheckout();
       }
       
       protected void fillCheckoutFormAndContinue(CheckoutData checkoutData) {
    	   checkoutPage.fillFirstName(checkoutData.getFirstName());
    	   checkoutPage.fillLastName(checkoutData.getLastName());
    	   checkoutPage.fillPostalCode(checkoutData.getPostalCode());
    	   checkoutPage.clickContinue();
       }
       
       protected void fillCheckoutFormAndContinueWithCustomPostalCode(String firstName, String lastName, String postalCode) {
    	   checkoutPage.fillFirstName(firstName);
    	   checkoutPage.fillLastName(lastName);
    	   checkoutPage.fillPostalCode(postalCode);
    	   checkoutPage.clickContinue();
       }
}

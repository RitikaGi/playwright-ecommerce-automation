package tests;

import java.io.File;
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

import pages.CartPage;
import pages.CheckoutPage;
import pages.FooterPage;
import pages.HeaderPage;
import pages.InventoryPage;
import pages.LoginPage;
import pages.NavigationPage;
import pages.ProductPage;
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
}

package com.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import org.testng.Assert;
import org.testng.annotations.*;

public class EcommerceTest {
	
	private Playwright playwright;
	private Browser browser;
	private Page page;
	
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
	
	@Test(priority=1)
	public void openWebsite() {
		page.navigate("https://www.saucedemo.com/");
		Assert.assertEquals("Swag Labs", page.title());
	}
	
	@Test(priority=2)
	public void loginWebsite() {
		page.navigate("https://www.saucedemo.com/");
		page.locator("#user-name").fill("standard_user");
		page.locator("#password").fill("secret_sauce");
		page.locator(".submit-button").click();
		Assert.assertTrue(page.url().contains("inventory.html"));
		
	}
	
	@Test(priority=3)
	public void addProductToCart(){
		page.navigate("https://www.saucedemo.com/");
		page.locator("#user-name").fill("standard_user");
		page.locator("#password").fill("secret_sauce");
		page.locator(".submit-button").click();
		Locator addToCart=page.locator("button#add-to-cart-sauce-labs-onesie");
		addToCart.click();
		Locator badge = page.locator(".shopping_cart_badge");
		Assert.assertTrue(badge.isVisible());
		Assert.assertEquals("1", badge.textContent());
	}
	
	@AfterMethod
	public void tearDown() {
		if(browser!=null) browser.close();
		if(playwright!=null) playwright.close();
	}

}

package com.playwright;

import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.*;

public class EcommerceTest extends BaseTest{
	
	
//	@Test 
//	public void loginWebsite()  {
//	    login();
//	    System.out.println(page.url());
//		Assert.assertTrue(page.url().contains("inventory.html"));
//		 
//	}
	
	@Test 
	public void addProductToCart() {
		login();
		
		Locator addToCart=page.locator("button#add-to-cart-sauce-labs-onesie");
		addToCart.click();
		
		Locator badge = page.locator(".shopping_cart_badge");
		Assert.assertTrue(badge.isVisible());
		Assert.assertEquals("1", badge.textContent());
		
		Locator cartIcon = page.locator(".shopping_cart_link");
		cartIcon.click();
		
		Locator removeFromCart=page.locator("button#remove-sauce-labs-onesie");
		Assert.assertTrue(removeFromCart.isVisible());
		
		Locator itemName= page.locator(".inventory_item_name");
        Assert.assertEquals("Sauce Labs Onesie", itemName.textContent());
        
        Locator itemPrice = page.locator(".inventory_item_price");
        Assert.assertEquals("$7.99", itemPrice.textContent()); 
        
		
		
	}
	
	@Test 
	public void removeProductFromCart()  {
		login();
		Locator addToCart=page.locator("button#add-to-cart-sauce-labs-onesie");
		addToCart.click();
		Locator badge = page.locator(".shopping_cart_badge");
		badge.click();
		Assert.assertTrue(page.url().contains("/cart.html"));
		Locator removeFromCart=page.locator("button#remove-sauce-labs-onesie");
		removeFromCart.click();
		
		System.out.println("Item is removed from cart");
		
	}
	
//	@Test
//	public void checkOut() {
//		login();
//		Locator addToCart=page.locator("button#add-to-cart-sauce-labs-onesie");
//		addToCart.click();
//		Locator badge = page.locator(".shopping_cart_badge");
//		badge.click();
//	}
	
	

}

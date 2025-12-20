package com.playwright;
import org.testng.Assert;
import org.testng.annotations.*;

public class EcommerceTest extends BaseTest{
	
	
	@Test 
	public void addProductToCart() {
	//	login();
		Assert.assertTrue(page.url().contains("/inventory.html"));
		inventoryPage.addOnesieToCart();
		Assert.assertTrue(inventoryPage.isCartBadgeVisible());
		Assert.assertEquals("1", inventoryPage.getCartBadgeCount());
		
		inventoryPage.clickCartIcon();
		Assert.assertTrue(cartPage.isRemoveButtonVisible());
		Assert.assertEquals("Sauce Labs Onesie", cartPage.getItemName());
		Assert.assertEquals("$7.99", cartPage.getItemPrice());
	
	}
	
	@Test 
	public void removeProductFromCart()  {
	//	login();
		//add Product to cart
		inventoryPage.addOnesieToCart();
		inventoryPage.clickCartIcon();
		//verify on cart page
		Assert.assertTrue(page.url().contains("/cart.html"));
		//remove item from cart
		cartPage.removeProductFromCart();
		//verify remove button is not visible
		Assert.assertFalse(cartPage.isRemoveButtonVisible());
		System.out.println("Item is removed from cart");
		
	}

}

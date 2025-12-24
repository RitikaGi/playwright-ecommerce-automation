package tests;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.HeaderPage;
import pages.InventoryPage;

public class CartOperations extends BaseTest{
	
	
	 @Test
     //TC_CART_001: View Cart with Single Item
	 public void TC_CART_001_View_Cart_with_Single_Item() {
		 Map<String,String> listDetails = inventoryPage.getProductDetailsFromList("Sauce Labs Onesie");
		 inventoryPage.addToCartByProductName("Sauce Labs Onesie");
		 headerPage.clickCartIcon();		
		Map<String,String> cartDetails = cartPage.getProductDetailsFromCart("Sauce Labs Onesie");
		 Assert.assertEquals(cartDetails, listDetails, 
	                "Product details mismatch between list and details page"); 
		 Assert.assertEquals(cartPage.getItemQuantityOnCart("Sauce Labs Onesie"),"1");
	 }
	 
	 @Test
	 //TC_CART_002: View Cart with Multiple Items
	 public void TC_CART_002_View_Cart_with_Multiple_Items() {
		 String[] products = {"Sauce Labs Onesie", "Sauce Labs Backpack", "Sauce Labs Bike Light"};
		 Map<String,Map<String,String>> expectedDetails = new HashMap<>();
		 for(String product: products) {
			 expectedDetails.put(product, inventoryPage.getProductDetailsFromList(product));
			 inventoryPage.addToCartByProductName(product);
		 }
		 headerPage.clickCartIcon();
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "3");
		 for(String product: products) {
			 Assert.assertEquals(cartPage.getProductDetailsFromCart(product), expectedDetails.get(product));
			 Assert.assertEquals(cartPage.getItemQuantityOnCart(product),"1");
		 }
	 	
	 }
	 
	 @Test
	//TC_CART_003: Remove Item from Cart
	public void TC_CART_003_Remove_Item_from_Cart() {
		 String[] products = {"Sauce Labs Onesie", "Sauce Labs Bike Light"};
	 for(String product: products) {
		 inventoryPage.addToCartByProductName(product);
	 }
	 headerPage.clickCartIcon();
	 cartPage.removeItemFromCart("Sauce Labs Onesie");
	 Assert.assertEquals(headerPage.getCartBadgeCount(), "1");
	 Assert.assertEquals(cartPage.getItemQuantityOnCart("Sauce Labs Bike Light"),"1");

	 }
	
	 
	 @Test
	//TC_CART_004: Remove All Items
	public void TC_CART_004_Remove_All_Items() {
		 String[] products = {"Sauce Labs Onesie", "Sauce Labs Bike Light"};
		 for(String product: products) {
			 inventoryPage.addToCartByProductName(product);
		 }
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "2");
		 headerPage.clickCartIcon();
		 Assert.assertEquals(2,cartPage.getCartItemCount());
		 for(String product: products) {
			 cartPage.removeItemFromCart(product);
		 }
		 Assert.assertFalse(headerPage.isCartBadgeVisible());
		 Assert.assertTrue(cartPage.getCartItemCount()<1);
	 }
	 
	 @Test
	//TC_CART_005: Continue Shopping from Cart
	 public void TC_CART_005_Continue_Shopping_from_Cart() {
		 inventoryPage.addToCartByProductName("Sauce Labs Onesie");
		 headerPage.clickCartIcon();
		 cartPage.continueShopping();
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "1");
	 }
	
	 
 	 @Test
	//TC_CART_006: View Empty Cart
	public void TC_CART_006_View_Empty_Cart() {
		 headerPage.clickCartIcon();
	    Assert.assertTrue(cartPage.getCartItemCount()<1);
	 }
	 
	 @Test
	//TC_CART_007: Cart Persistence Across Pages
	public void TC_CART_007_Cart_Persistence_Across_Pages() {
		 String[] products = {"Sauce Labs Onesie", "Sauce Labs Bike Light"};
		 for(String product: products) {
			 inventoryPage.addToCartByProductName(product);
		 }
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "2");
		 headerPage.clickCartIcon();
		 cartPage.continueShopping();
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "2");
	 }
	 
	 @Test
	//TC_CART_008: Proceed to Checkout from Cart
	public void TC_CART_008_Proceed_to_Checkout_from_Cart() { //Bug Checkout is available without any product in cart
		 inventoryPage.addToCartByProductName("Sauce Labs Onesie");
		 headerPage.clickCartIcon();
		 cartPage.clickOnCheckoutButton();
		 Assert.assertTrue(page.url().contains("checkout-step-one"));
	 }
	 
	 @Test
	//TC_CART_009: Cart Badge Accuracy
	public void TC_CART_009_Cart_Badge_Accuracy() {
		 inventoryPage.addToCartByProductName("Sauce Labs Onesie");
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "1");
		 inventoryPage.addToCartByProductName("Sauce Labs Bike Light");
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "2");
		 headerPage.clickCartIcon();
		 cartPage.removeItemFromCart("Sauce Labs Onesie");
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "1");
		 
	 }
	
}

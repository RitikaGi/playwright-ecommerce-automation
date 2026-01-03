package tests;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;


import utils.TestData.Products;

public class CartOperationsTest extends BaseTest{
	
	
	 @Test
     //TC_CART_001: View Cart with Single Item
	 public void TC_CART_001_View_Cart_with_Single_Item() {
		 Map<String,String> listDetails = inventoryPage.getProductDetailsFromList(Products.ONESIE.getName());
		 inventoryPage.addToCartByProductName(Products.ONESIE.getName());
		 headerPage.clickCartIcon();		
		Map<String,String> cartDetails = cartPage.getProductDetailsFromCart(Products.ONESIE.getName());
		 Assert.assertEquals(cartDetails, listDetails, 
	                "Product details mismatch between list and details page"); 
		 Assert.assertEquals(cartPage.getItemQuantityOnCart(Products.ONESIE.getName()),"1");
	 }
	 
	 @Test
	 //TC_CART_002: View Cart with Multiple Items
	 public void TC_CART_002_View_Cart_with_Multiple_Items() {
		Products[] products = {Products.ONESIE, Products.BIKE_LIGHT, Products.BACKPACK};
		 Map<String,Map<String,String>> expectedDetails = addProductsToCartandDetails(products);
	     navigateToCart();
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "3");
		 for(Products product: products) {
			 Assert.assertEquals(cartPage.getProductDetailsFromCart(product.getName()), expectedDetails.get(product.getName()));
			 Assert.assertEquals(cartPage.getItemQuantityOnCart(product.getName()),"1");
		 }
	 	
	 }
	 
	 @Test
	//TC_CART_003: Remove Item from Cart
	public void TC_CART_003_Remove_Item_from_Cart() {
		 Products[] products = {Products.ONESIE, Products.BIKE_LIGHT};
	 addProductsToCart(products);
	 navigateToCart();
	 cartPage.removeItemFromCart(Products.ONESIE.getName());
	 Assert.assertEquals(headerPage.getCartBadgeCount(), "1");
	 Assert.assertEquals(cartPage.getItemQuantityOnCart(Products.BIKE_LIGHT.getName()),"1");

	 }
	
	 
	 @Test
	//TC_CART_004: Remove All Items
	public void TC_CART_004_Remove_All_Items() {
		 Products[] products = {Products.ONESIE, Products.BIKE_LIGHT};
		 addProductsToCart(products);
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "2");
		 headerPage.clickCartIcon();
		 Assert.assertEquals(2,cartPage.getCartItemCount());
		 for(Products product: products) {
			 cartPage.removeItemFromCart(product.getName());
		 }
		 Assert.assertFalse(headerPage.isCartBadgeVisible());
		 Assert.assertTrue(cartPage.getCartItemCount()<1);
	 }
	 
	 @Test
	//TC_CART_005: Continue Shopping from Cart
	 public void TC_CART_005_Continue_Shopping_from_Cart() {
		 inventoryPage.addToCartByProductName(Products.ONESIE.getName());
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
		 Products[] products = {Products.ONESIE, Products.BACKPACK};
		 addProductsToCart(products);
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "2");
		 headerPage.clickCartIcon();
		 cartPage.continueShopping();
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "2");
	 }
	 
	 @Test
	//TC_CART_008: Proceed to Checkout from Cart
	public void TC_CART_008_Proceed_to_Checkout_from_Cart_With_Item() { 
		 addProductsToCart(Products.ONESIE);
		 navigateToCheckout();
		 Assert.assertTrue(page.url().contains("checkout-step-one"));
	 }
	 
	 @Test
	//TC_CART_009: Cart Badge Accuracy
	public void TC_CART_009_Cart_Badge_Accuracy() {
		 inventoryPage.addToCartByProductName(Products.ONESIE.getName());
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "1");
		 inventoryPage.addToCartByProductName(Products.BIKE_LIGHT.getName());
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "2");
		 headerPage.clickCartIcon();
		 cartPage.removeItemFromCart(Products.ONESIE.getName());
		 Assert.assertEquals(headerPage.getCartBadgeCount(), "1");
		 
	 }
	
	 @Test(enabled=false, description="Known Bugs BUG003: Moving to checkout with the blank cart")
		//TC_CART_010: Proceed to Checkout from Cart Without Item
	   
		public void TC_CART_008_Proceed_to_Checkout_from_Cart_Without_Item() { 
		     navigateToCheckout();
			 Assert.assertFalse(page.url().contains("checkout-step-one"));
		 }
		 
}

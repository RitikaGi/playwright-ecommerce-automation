package tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.TestData.CheckoutData;
import utils.TestData.Products;


public class CheckoutFlowTest extends BaseTest{
	
    @Test
	//TC_CHECKOUT_001: Checkout Step One - Valid Info
    public void TC_CHECKOUT_001_Checkout_Step_One_Valid_Info() {
    	addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
    	fillCheckoutFormAndContinue(CheckoutData.VALID_USER);
    	Assert.assertTrue(page.url().contains("checkout-step-two"));
    }
    
	@Test
	//TC_CHECKOUT_002: Checkout - Empty First Name
	public void TC_CHECKOUT_002_Checkout_Empty_First_Name() {
		addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
		fillCheckoutFormAndContinue(CheckoutData.NO_FIRST_NAME);
    	Assert.assertEquals(checkoutPage.getErrorMessage(),"Error: First Name is required");
	}
	
	@Test
	//TC_CHECKOUT_003: Checkout - Empty Last Name
	public void TC_CHECKOUT_003_Checkout_Empty_Last_Name() {
		addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
		fillCheckoutFormAndContinue(CheckoutData.NO_LAST_NAME);
    	Assert.assertEquals(checkoutPage.getErrorMessage(),"Error: Last Name is required");
	}
	
	@Test
	//TC_CHECKOUT_004: Checkout - Empty Postal Code
	public void TC_CHECKOUT_004_Checkout_Empty_Postal_Code() {
		addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
		fillCheckoutFormAndContinue(CheckoutData.NO_POSTAL_CODE);
    	Assert.assertEquals(checkoutPage.getErrorMessage(),"Error: Postal Code is required");
	}
	
	@Test
	//TC_CHECKOUT_005: Checkout - All Fields Empty
	public void TC_CHECKOUT_005_Checkout_All_Fields_Empty() {
		addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
		fillCheckoutFormAndContinue(CheckoutData.INVALID_USER);
    	Assert.assertEquals(checkoutPage.getErrorMessage(),"Error: First Name is required");
	}
	
	@Test
	//TC_CHECKOUT_006: Cancel from Checkout Step One
	public void TC_CHECKOUT_006_Cancel_from_Checkout_Step_One() {
		addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
    	checkoutPage.clickCancel();
    	Assert.assertEquals(headerPage.getCartBadgeCount(),"1");
    	Assert.assertEquals(cartPage.getCartItemCount(),1);
	}
	
	@Test
	//TC_CHECKOUT_007: Verify Checkout Overview Details
	public void TC_CHECKOUT_007_Verify_Checkout_Overview_Details() {
		Map<String,String> list_details= inventoryPage.getProductDetailsFromList(Products.FLEECE_JACKET.getName());
		addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
    	fillCheckoutFormAndContinue(CheckoutData.VALID_USER_2);
    	Map<String,String> checkout_details=checkoutPage.getProductDetailsFromCheckout(Products.FLEECE_JACKET.getName());
    	Assert.assertEquals(checkout_details, list_details );
	}
	
	
	@Test
	//TC_CHECKOUT_008: Verify Multiple Items in Overview
	public void TC_CHECKOUT_008_Verify_Multiple_Items_in_Overview() {
		Products[] products = {Products.ONESIE, Products.BIKE_LIGHT};
		
		Map<String,Map<String,String>> inventoryDetails = addProductsToCartandDetails(products);
        navigateToCheckout();
    	fillCheckoutFormAndContinue(CheckoutData.VALID_USER_2);
	    for(Products product: products) {
	    	Map<String,String> checkoutDetails = checkoutPage.getProductDetailsFromCheckout(product.getName());
	    	Map<String,String> expectedDetails = inventoryDetails.get(product.getName());
	    	
	    	Assert.assertEquals(expectedDetails, checkoutDetails);
	    	Assert.assertEquals(checkoutPage.getPaymentInfo(),"SauceCard #31337");
	    	Assert.assertEquals(checkoutPage.getShippingAddress(), "Free Pony Express Delivery!");
	    	Assert.assertEquals(cartPage.getCartItemCount(), 2);
	    	Assert.assertEquals(checkoutPage.ActualItemTotal(),checkoutPage.ExpectedItemTotal(),
	    			"item total does not match");
	    	Assert.assertEquals(checkoutPage.ActualTaxAmount(), checkoutPage.ExpectedTaxAmount(),
	    			"tax amount does not match");
	    	Assert.assertEquals(checkoutPage.ActualTotalAmount(), checkoutPage.ExpectedTotalAmount(),
	    			"total amount does not match");
	    	
	    }
	}
	
	
	@Test
	//TC_CHECKOUT_009: Verify Tax Calculation
	public void TC_CHECKOUT_009_Verify_Tax_Calculation() {
		addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
    	fillCheckoutFormAndContinue(CheckoutData.VALID_USER);
    	Assert.assertEquals(checkoutPage.ActualTaxAmount(), checkoutPage.ExpectedTaxAmount(),
    			"tax amount should be 8% of item total");
    	Assert.assertEquals(checkoutPage.ActualTotalAmount(),
    			checkoutPage.ActualItemTotal()+checkoutPage.ExpectedTaxAmount());
	}
	
	
	
	@Test
	//TC_CHECKOUT_010: Cancel from Overview Page
	public void TC_CHECKOUT_010_Cancel_from_Overview_Page() {
		addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
    	fillCheckoutFormAndContinue(CheckoutData.VALID_USER);
    	checkoutPage.clickCancel();
    	Assert.assertEquals(headerPage.getCartBadgeCount(),"1","badge count is not retained");
    	headerPage.clickCartIcon();
    	Assert.assertEquals(cartPage.getCartItemCount(),1, "cart item count is not retained");
		
	}
	
	
	@Test
	//TC_CHECKOUT_011: Complete Purchase
	public void TC_CHECKOUT_011_Complete_Purchase() {
		addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
    	fillCheckoutFormAndContinue(CheckoutData.VALID_USER_2);
    	checkoutPage.clickFinishButton();
    	Assert.assertTrue(page.url().contains("checkout-complete"));
    	Assert.assertTrue(checkoutPage.isGreenCheckMarkVisible());
    	Assert.assertEquals(checkoutPage.successMessage(),"Thank you for your order!");
    	Assert.assertTrue(checkoutPage.isBackToHomeButtonVisible());
    	
	}
	
	
	@Test
	//TC_CHECKOUT_012: Verify Order Confirmation Message
	public void TC_CHECKOUT_012_Verify_Order_Confirmation_Message() {
		addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
    	fillCheckoutFormAndContinue(CheckoutData.VALID_USER);
    	checkoutPage.clickFinishButton();
    	Assert.assertEquals(checkoutPage.confirmationMessage(), 
    			"Your order has been dispatched, and will arrive just as fast as the pony can get there!");
	}
	
	
	@Test
	//TC_CHECKOUT_013: Return Home After Purchase
	public void TC_CHECKOUT_013_Return_Home_After_Purchase() {
		addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
    	fillCheckoutFormAndContinue(CheckoutData.VALID_USER);
    	checkoutPage.clickFinishButton();
    	checkoutPage.clickBackHomeButton();
    	Assert.assertTrue(page.url().contains("inventory"));
    	Assert.assertFalse(headerPage.isCartBadgeVisible());
    	inventoryPage.addToCartByProductName(Products.FLEECE_JACKET.getName());
    	headerPage.clickCartIcon();
    	Assert.assertEquals(headerPage.getCartBadgeCount(),"1","badge count is not retained");
    	Assert.assertEquals(cartPage.getCartItemCount(),1);
    		
	}
	
	@Test
	//TC_CHECKOUT_014: Cart Clears After Purchase
	public void TC_CHECKOUT_014_Cart_Clears_After_Purchase() {
		addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
    	fillCheckoutFormAndContinue(CheckoutData.VALID_USER_2);
    	checkoutPage.clickFinishButton();
    	checkoutPage.clickBackHomeButton();
    	Assert.assertFalse(headerPage.isCartBadgeVisible());
    	Assert.assertEquals(cartPage.getCartItemCount(),0);
	}
	
	
	@Test
	//TC_CHECKOUT_015: End-to-End Happy Path
	public void TC_CHECKOUT_015_End_to_End_Happy_Path() {
		Products[] products = {Products.ONESIE, Products.BIKE_LIGHT};
		Map<String,Map<String,String>> inventoryDetails = addProductsToCartandDetails(products);
		navigateToCheckout();
    	fillCheckoutFormAndContinue(CheckoutData.VALID_USER_2);
	    for(Products product: products) {
	    	Map<String,String> checkoutDetails = checkoutPage.getProductDetailsFromCheckout(product.getName());
	    	Map<String,String> expectedDetails = inventoryDetails.get(product.getName());
	    	Assert.assertEquals(expectedDetails, checkoutDetails);
	}
	    checkoutPage.clickFinishButton();
	    Assert.assertEquals(checkoutPage.confirmationMessage(), 
    			"Your order has been dispatched, and will arrive just as fast as the pony can get there!");
	}
	
	
	@Test(dataProvider="PostalCodeFormats")
	//TC_CHECKOUT_016: Postal Code Format Validation
	public void TC_CHECKOUT_016_Postal_Code_Format_Validation(String format,String postalCode) {
		// Add product and navigate to checkout
		addToCartAndNavigateToCheckout(Products.FLEECE_JACKET);
	    
	    // Fill form with the postal code format
		fillCheckoutFormAndContinueWithCustomPostalCode(
				CheckoutData.VALID_USER.getFirstName(),
				CheckoutData.VALID_USER.getLastName(), 
				postalCode);
	    
	    // Verify navigation to overview page (format accepted)
	    Assert.assertTrue(page.url().contains("checkout-step-two"));
		
	}
	
	@DataProvider(name="PostalCodeFormats")
	public Object[][] postalCodeFormatData(){
		return new Object[][] {
			{"Numeric", "121003"},
	        {"Alphanumeric", "A1B2C3"},
	        {"Special chars", "12-10"},
	        {"Mixed case", "a1B2c3"},
	        {"With spaces", "12 10 03"},
	        {"Only letters", "ABCDEF"},
	        {"Long format", "123456789"},
	        {"Single digit", "1"},
	        {"Empty with spaces", "   "}
		};
		
	}
}

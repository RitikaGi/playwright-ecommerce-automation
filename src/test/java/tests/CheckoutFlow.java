package tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class CheckoutFlow extends BaseTest{
	
    @Test
	//TC_CHECKOUT_001: Checkout Step One - Valid Info
    public void TC_CHECKOUT_001_Checkout_Step_One_Valid_Info() {
    	inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	cartPage.clickOnCheckoutButton();
    	checkoutPage.fillCheckoutForm("kishor", "kumar", "121304");
    	checkoutPage.clickContinue();
    	Assert.assertTrue(page.url().contains("checkout-step-two"));
    }
    
	@Test
	//TC_CHECKOUT_002: Checkout - Empty First Name
	public void TC_CHECKOUT_002_Checkout_Empty_First_Name() {
		inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	cartPage.clickOnCheckoutButton();
    	checkoutPage.fillCheckoutForm("", "kumari", "121334");
    	checkoutPage.clickContinue();
    	Assert.assertEquals(checkoutPage.getErrorMessage(),"Error: First Name is required");
	}
	
	@Test
	//TC_CHECKOUT_003: Checkout - Empty Last Name
	public void TC_CHECKOUT_003_Checkout_Empty_Last_Name() {
		inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	cartPage.clickOnCheckoutButton();
    	checkoutPage.fillCheckoutForm("kishori", "", "121334");
    	checkoutPage.clickContinue();
    	Assert.assertEquals(checkoutPage.getErrorMessage(),"Error: Last Name is required");
	}
	
	@Test
	//TC_CHECKOUT_004: Checkout - Empty Postal Code
	public void TC_CHECKOUT_004_Checkout_Empty_Postal_Code() {
		inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	cartPage.clickOnCheckoutButton();
    	checkoutPage.fillCheckoutForm("kishori", "singh", "");
    	checkoutPage.clickContinue();
    	Assert.assertEquals(checkoutPage.getErrorMessage(),"Error: Postal Code is required");
	}
	
	@Test
	//TC_CHECKOUT_005: Checkout - All Fields Empty
	public void TC_CHECKOUT_005_Checkout_All_Fields_Empty() {
		inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	cartPage.clickOnCheckoutButton();
    	checkoutPage.fillCheckoutForm("", "", "");
    	checkoutPage.clickContinue();
    	Assert.assertEquals(checkoutPage.getErrorMessage(),"Error: First Name is required");
	}
	
	@Test
	//TC_CHECKOUT_006: Cancel from Checkout Step One
	public void TC_CHECKOUT_006_Cancel_from_Checkout_Step_One() {
		inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	cartPage.clickOnCheckoutButton();
    	checkoutPage.clickCancel();
    	Assert.assertEquals(headerPage.getCartBadgeCount(),"1");
    	Assert.assertEquals(cartPage.getCartItemCount(),1);
	}
	
	@Test
	//TC_CHECKOUT_007: Verify Checkout Overview Details
	public void TC_CHECKOUT_007_Verify_Checkout_Overview_Details() {
		Map<String,String> list_details= inventoryPage.getProductDetailsFromList("Sauce Labs Fleece Jacket");
		inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	cartPage.clickOnCheckoutButton();
    	checkoutPage.fillCheckoutForm("kishor", "kumar", "121304");
    	checkoutPage.clickContinue();
    	Map<String,String> checkout_details=checkoutPage.getProductDetailsFromCheckout("Sauce Labs Fleece Jacket");
    	Assert.assertEquals(checkout_details, list_details );
	}
	
	
	@Test
	//TC_CHECKOUT_008: Verify Multiple Items in Overview
	public void TC_CHECKOUT_008_Verify_Multiple_Items_in_Overview() {
		String[] products = {"Sauce Labs Onesie", "Sauce Labs Bike Light"};
		Map<String,Map<String,String>> inventoryDetails = new HashMap<>();
		for(String product:products) {
			Map<String,String> productDetails = inventoryPage.getProductDetailsFromList(product);
			inventoryDetails.put(product, productDetails);
			inventoryPage.addToCartByProductName(product);
		}
		headerPage.clickCartIcon();
	    cartPage.clickOnCheckoutButton();
	    checkoutPage.fillCheckoutForm("kishor", "kumar", "121304");
	    checkoutPage.clickContinue();
	    for(String product: products) {
	    	Map<String,String> checkoutDetails = checkoutPage.getProductDetailsFromCheckout(product);
	    	Map<String,String> expectedDetails = inventoryDetails.get(product);
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
		inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	cartPage.clickOnCheckoutButton();
    	checkoutPage.fillCheckoutForm("kishori", "kumari", "121334");
    	checkoutPage.clickContinue();
    	Assert.assertEquals(checkoutPage.ActualTaxAmount(), checkoutPage.ExpectedTaxAmount(),
    			"tax amount should be 8% of item total");
    	Assert.assertEquals(checkoutPage.ActualTotalAmount(),
    			checkoutPage.ActualItemTotal()+checkoutPage.ExpectedTaxAmount());
	}
	
	
	
	@Test
	//TC_CHECKOUT_010: Cancel from Overview Page
	public void TC_CHECKOUT_010_Cancel_from_Overview_Page() {
		inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	cartPage.clickOnCheckoutButton();
    	checkoutPage.fillCheckoutForm("kishori", "kumari", "121334");
    	checkoutPage.clickContinue();
    	checkoutPage.clickCancel();
    	Assert.assertEquals(headerPage.getCartBadgeCount(),"1","badge count is not retained");
    	headerPage.clickCartIcon();
    	Assert.assertEquals(cartPage.getCartItemCount(),1, "cart item count is not retained");
		
	}
	
	
	@Test
	//TC_CHECKOUT_011: Complete Purchase
	public void TC_CHECKOUT_011_Complete_Purchase() {
		inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	cartPage.clickOnCheckoutButton();
    	checkoutPage.fillCheckoutForm("kishori", "kumari", "121334");
    	checkoutPage.clickContinue();
    	checkoutPage.clickFinishButton();
    	Assert.assertTrue(page.url().contains("checkout-complete"));
    	Assert.assertTrue(checkoutPage.isGreenCheckMarkVisible());
    	Assert.assertEquals(checkoutPage.successMessage(),"Thank you for your order!");
    	Assert.assertTrue(checkoutPage.isBackToHomeButtonVisible());
    	
	}
	
	
	@Test
	//TC_CHECKOUT_012: Verify Order Confirmation Message
	public void TC_CHECKOUT_012_Verify_Order_Confirmation_Message() {
		inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	cartPage.clickOnCheckoutButton();
    	checkoutPage.fillCheckoutForm("kishori", "kumari", "121334");
    	checkoutPage.clickContinue();
    	checkoutPage.clickFinishButton();
    	Assert.assertEquals(checkoutPage.confirmationMessage(), 
    			"Your order has been dispatched, and will arrive just as fast as the pony can get there!");
	}
	
	
	@Test
	//TC_CHECKOUT_013: Return Home After Purchase
	public void TC_CHECKOUT_013_Return_Home_After_Purchase() {
		inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	cartPage.clickOnCheckoutButton();
    	checkoutPage.fillCheckoutForm("kishori", "kumari", "121334");
    	checkoutPage.clickContinue();
    	checkoutPage.clickFinishButton();
    	checkoutPage.clickBackHomeButton();
    	Assert.assertTrue(page.url().contains("inventory"));
    	Assert.assertFalse(headerPage.isCartBadgeVisible());
    	inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	Assert.assertEquals(headerPage.getCartBadgeCount(),"1","badge count is not retained");
    	Assert.assertEquals(cartPage.getCartItemCount(),1);
    		
	}
	
	@Test
	//TC_CHECKOUT_014: Cart Clears After Purchase
	public void TC_CHECKOUT_014_Cart_Clears_After_Purchase() {
		inventoryPage.addToCartByProductName("Sauce Labs Fleece Jacket");
    	headerPage.clickCartIcon();
    	cartPage.clickOnCheckoutButton();
    	checkoutPage.fillCheckoutForm("kishori", "kumari", "121334");
    	checkoutPage.clickContinue();
    	checkoutPage.clickFinishButton();
    	checkoutPage.clickBackHomeButton();
    	Assert.assertFalse(headerPage.isCartBadgeVisible());
    	Assert.assertEquals(cartPage.getCartItemCount(),0);
	}
	
	
	@Test
	//TC_CHECKOUT_015: End-to-End Happy Path
	public void TC_CHECKOUT_015_End_to_End_Happy_Path() {
		String[] products = {"Sauce Labs Onesie", "Sauce Labs Bike Light"};
		Map<String,Map<String,String>> inventoryDetails = new HashMap<>();
		for(String product:products) {
			Map<String,String> productDetails = inventoryPage.getProductDetailsFromList(product);
			inventoryDetails.put(product, productDetails);
			inventoryPage.addToCartByProductName(product);
		}
		headerPage.clickCartIcon();
	    cartPage.clickOnCheckoutButton();
	    checkoutPage.fillCheckoutForm("kishor", "kumar", "121304");
	    checkoutPage.clickContinue();
	    for(String product: products) {
	    	Map<String,String> checkoutDetails = checkoutPage.getProductDetailsFromCheckout(product);
	    	Map<String,String> expectedDetails = inventoryDetails.get(product);
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
	    inventoryPage.addToCartByProductName("Sauce Labs Backpack");
	    headerPage.clickCartIcon();
	    cartPage.clickOnCheckoutButton();
	    
	    // Fill form with the postal code format
	    checkoutPage.fillCheckoutForm("John", "Doe", postalCode);
	    checkoutPage.clickContinue();
	    
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

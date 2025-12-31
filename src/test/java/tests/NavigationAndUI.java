package tests;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import utils.TestData.Products;

public class NavigationAndUI extends BaseTest{
	
	
	  @Test
      //TC_NAV_001: Hamburger Menu - All Options
	  public void TC_NAV_001_Hamburger_Menu_All_Options() {
		  headerPage.openMenu();
		
		  List<String> expectedMenuItems = Arrays.asList("All Items", "About", "Logout", "Reset App State");
		  Assert.assertEquals(headerPage.getMenuItems(),expectedMenuItems);
	  }
	  
	  @Test
	  //TC_NAV_002: Logout Functionality
	  public void TC_NAV_002_Logout_Functionality() {
		  headerPage.openMenu();
		  headerPage.clickLogout();
		  Assert.assertTrue(loginPage.isOnLoginPage());
		  navigationPage.navigateToTheUrl("https://www.saucedemo.com/inventory.html");
		  Assert.assertEquals(loginPage.getErrorMessage(),
				  "Epic sadface: You can only access '/inventory.html' when you are logged in.");
		  //Unable to do the cookies task as no api is called
	  }
	  
	  	 @Test
	 //TC_NAV_003: Reset App State
	 public void TC_NAV_003_Reset_App_State() {
	  	 inventoryPage.addToCartByProductName(Products.FLEECE_JACKET.getName());
		 headerPage.openMenu();
		 headerPage.clickResetAppState();
		 Assert.assertFalse(headerPage.isCartBadgeVisible());
	 }
	
	 
	 @Test
	//TC_NAV_004: About Link Navigation
	 public void TC_NAV_004_About_Link_Navigation() {
		 headerPage.openMenu();
		 headerPage.clickAbout();
		 Assert.assertTrue(page.url().contains("saucelabs.com/"));
	 }
	 
	 	
	 @Test
	//TC_NAV_005: All Items Navigation
	 public void TC_NAV_005_All_Items_Navigation() {
		 inventoryPage.clickOnItem(Products.FLEECE_JACKET.getName());
		 headerPage.openMenu();
		 headerPage.clickAllItems();
		 Assert.assertTrue(page.url().contains("inventory.html"));
	 }
	 
	
	 @Test
	//TC_NAV_006: Footer Social Links
	 public void TC_NAV_006_Footer_Social_Links() {
		 Assert.assertTrue(footerPage.allSocialIconsVisible(), "All social icons are not visible");
		 Assert.assertTrue(footerPage.allSocialIconsClickable(),"All social links are not clickable");
	 }
	 
	
	 @Test
	//TC_NAV_007: Copyright Text Verification
	 public void TC_NAV_007_Copyright_Text_Verification() {
		 Assert.assertEquals(footerPage.getCopyrightText(), 
				 "© 2025 Sauce Labs. All Rights Reserved. Terms of Service | Privacy Policy");
	 }
	 
	 
	 @Test(enabled=false, description="Known Bugs BUG-002: Reset doesn't clear cart from cart page")
	 //TC_NAV_008: Reset App State From Cart 
	 public void TC_NAV_008_Reset_App_State_From_Cart() {   
	  	 inventoryPage.addToCartByProductName(Products.BACKPACK.getName());
	  	 headerPage.clickCartIcon();
		 headerPage.openMenu();
		 headerPage.clickResetAppState();
		 Assert.assertTrue(cartPage.getCartItemCount()==0);
		 Assert.assertFalse(headerPage.isCartBadgeVisible());
	 }
	
}

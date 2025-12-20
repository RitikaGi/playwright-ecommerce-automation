package com.playwright;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

 
import org.testng.annotations.Test;


public class LoginTest extends BaseTest{
	   
	 @Test    
	 //TC_LOGIN_001: Valid Login - Standard User
	 public void TC_LOGIN_001_ValidLogin_StandardUser() {
		 loginPage.login(BASE_URL, "standard_user", "secret_sauce");
		 assertFalse(loginPage.isErrorMessageVisible());
		 assertTrue(page.url().contains("/inventory.html"));
		 assertTrue(inventoryPage.isProductsHeaderVisible());
		 assertFalse(inventoryPage.areAllProductsImageSame());
	 }
	 
	 @Test
	//TC_LOGIN_002: Invalid Username
	 public void TC_LOGIN_002_Invalid_Username() {
		 loginPage.login(BASE_URL, "invalid_user", "secret_sauce");
		 assertTrue(loginPage.isErrorMessageVisible());
		 assertTrue(loginPage.getErrorMessage().contains("Epic sadface"));
		 
	 }
	 
	 @Test
	//TC_LOGIN_003: Invalid Password
	 public void TC_LOGIN_003_Invalid_Password() {
		 loginPage.login(BASE_URL, "standard_user", "secret_saucey");
		 assertTrue(loginPage.isErrorMessageVisible());
		 assertTrue(loginPage.getErrorMessage().contains("Epic sadface"));
	 }
	 
	 @Test
	//TC_LOGIN_004: Empty Username
	 public void TC_LOGIN_004_Empty_Username() {
		 loginPage.login(BASE_URL, "", "secret_sauce");
		 assertTrue(loginPage.isErrorMessageVisible());
		 assertTrue(loginPage.getErrorMessage().contains("Username is required"));
	 }
	 
	 @Test
	//TC_LOGIN_005: Empty Password
	 public void TC_LOGIN_005_Empty_Password() {
		 loginPage.login(BASE_URL, "standard_user", "");
		 assertTrue(loginPage.isErrorMessageVisible());
		 assertTrue(loginPage.getErrorMessage().contains("Password is required"));
	 }
	 
	 @Test
	//TC_LOGIN_006: Locked Out User
	 public void TC_LOGIN_006_Locked_Out_User() {
		 loginPage.login(BASE_URL, "locked_out_user", "secret_sauce");
		 assertTrue(loginPage.isErrorMessageVisible());
		 assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out."));	
	 }
	 
	 @Test
	//TC_LOGIN_007: Problem User Login
	 public void TC_LOGIN_007_Problem_User_Login() {
		 loginPage.login(BASE_URL, "problem_user", "secret_sauce");
		 assertTrue(inventoryPage.isProductsHeaderVisible());
		 assertTrue(inventoryPage.areAllProductsImageSame());
	 }
	 
	 @Test
	//TC_LOGIN_008: Performance Glitch User
	 public void TC_LOGIN_008_Performance_Glitch_User() {
		 long startTime = System.currentTimeMillis();
		 loginPage.login(BASE_URL, "performance_glitch_user", "secret_sauce");
		 long endTime=System.currentTimeMillis();
		 long duration=endTime-startTime;
		 assertTrue(inventoryPage.isProductsHeaderVisible());
		 assertTrue(duration>2000);
		 System.out.println(duration);
		 
	 }
	
}

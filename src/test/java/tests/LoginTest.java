package tests;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.TestData;
import utils.TestData.Users;


public class LoginTest extends BaseTest{
	  @BeforeMethod
	  public void disableAutoLogin() {
		  skipLogin = true;
	  }
	
	 @Test    
	 //TC_LOGIN_001: Valid Login - Standard User
	 public void TC_LOGIN_001_ValidLogin_StandardUser() {
		 loginPage.login(BASE_URL, Users.STANDARD_USER.getUsername(), Users.STANDARD_USER.getPassword());
		 assertFalse(loginPage.isErrorMessageVisible());
		 assertTrue(page.url().contains("/inventory.html"));
		 assertTrue(inventoryPage.isProductsHeaderVisible());
		 assertFalse(inventoryPage.areAllProductsImageSame());
	 }
	 
	 @Test
	//TC_LOGIN_002: Invalid Username
	 public void TC_LOGIN_002_Invalid_Username() {
		 loginPage.login(BASE_URL, Users.INVALID_USER.getUsername(), Users.INVALID_USER.getPassword());
		 assertTrue(loginPage.isErrorMessageVisible());
		 assertTrue(loginPage.getErrorMessage().contains("Epic sadface"));
		 
	 }
	 
	 @Test
	//TC_LOGIN_003: Invalid Password
	 public void TC_LOGIN_003_Invalid_Password() {
		 loginPage.login(BASE_URL, Users.STANDARD_USER.getUsername(), TestData.INVALID_PASSWORD );
		 assertTrue(loginPage.isErrorMessageVisible());
		 assertTrue(loginPage.getErrorMessage().contains("Epic sadface"));
	 }
	 
	 @Test
	//TC_LOGIN_004: Empty Username
	 public void TC_LOGIN_004_Empty_Username() {
		 loginPage.login(BASE_URL, "", Users.STANDARD_USER.getPassword());
		 assertTrue(loginPage.isErrorMessageVisible());
		 assertTrue(loginPage.getErrorMessage().contains("Username is required"));
	 }
	 
	 @Test
	//TC_LOGIN_005: Empty Password
	 public void TC_LOGIN_005_Empty_Password() {
		 loginPage.login(BASE_URL, Users.STANDARD_USER.getUsername(), "");
		 assertTrue(loginPage.isErrorMessageVisible());
		 assertTrue(loginPage.getErrorMessage().contains("Password is required"));
	 }
	 
	 @Test
	//TC_LOGIN_006: Locked Out User
	 public void TC_LOGIN_006_Locked_Out_User() {
		 loginPage.login(BASE_URL, Users.LOCKED_OUT_USER.getUsername(), Users.LOCKED_OUT_USER.getPassword());
		 assertTrue(loginPage.isErrorMessageVisible());
		 assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out."));	
	 }
	 
	 @Test(enabled=false, description="Known Bugs BUG-001: All products images should not be same")
	//TC_LOGIN_007: Problem User Login
	 public void TC_LOGIN_007_Problem_User_Login() {
		 loginPage.login(BASE_URL, Users.PROBLEM_USER.getUsername(), Users.PROBLEM_USER.getPassword());
		 assertTrue(inventoryPage.isProductsHeaderVisible());
		 assertFalse(inventoryPage.areAllProductsImageSame());
	 }
	 
	 @Test
	//TC_LOGIN_008: Performance Glitch User
	 public void TC_LOGIN_008_Performance_Glitch_User() {
		 long startTime = System.currentTimeMillis();
		 loginPage.login(BASE_URL, Users.PERFORMANCE_GLITCH_USER.getUsername(), Users.PERFORMANCE_GLITCH_USER.getPassword());
		 long endTime=System.currentTimeMillis();
		 long duration=endTime-startTime;
		 assertTrue(inventoryPage.isProductsHeaderVisible());
		 assertTrue(duration>2000);
		 System.out.println(duration);
		 
	 }
	
}

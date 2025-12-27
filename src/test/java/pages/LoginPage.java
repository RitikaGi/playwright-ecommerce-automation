package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
	private Page page;
	private Locator usernameField;
	private Locator passwordField;
	private Locator loginButton;
	private Locator errorMessageOnLogin;

	public LoginPage(Page page){
		this.page = page;
		this.usernameField = page.locator("#user-name");
		this.passwordField = page.locator("#password");
		this.loginButton = page.locator(".submit-button");
		this.errorMessageOnLogin = page.locator("[data-test='error']");
	}
	
	public void navigateToUrl(String url) {
		page.navigate(url);
	}
	
	public void enterUsername(String username) {
		usernameField.fill(username);
	}
	
	public void enterPassword(String password) {
		passwordField.fill(password);
	}
	
	public void clickLoginButton() {
		loginButton.click();
	}
	
	public String getErrorMessage() {
		return errorMessageOnLogin.textContent();
	}
	
	public boolean isErrorMessageVisible() {
		return errorMessageOnLogin.isVisible();
	}
	
	public boolean isOnLoginPage() {
		boolean urlCorrect = page.url().contains("saucedemo.com");
		boolean elementsVisible = usernameField.isVisible()
			                	&& passwordField.isVisible()
			                	&& loginButton.isVisible();
		return urlCorrect && elementsVisible;
	}
	
	
	public void login(String url,String username,String password) {
		//Login
		navigateToUrl(url);
		enterUsername(username);
		enterPassword(password);
		clickLoginButton();
		
	}
}

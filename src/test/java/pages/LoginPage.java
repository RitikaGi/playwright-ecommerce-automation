package pages;

import com.microsoft.playwright.Page;

public class LoginPage {
	private Page page;

	public LoginPage(Page page){
		this.page = page;
	}
	
	public void navigateToUrl(String url) {
		page.navigate(url);
	}
	
	public void enterUsername(String username) {
		page.locator("#user-name").fill(username);
	}
	
	public void enterPassword(String password) {
		page.locator("#password").fill(password);
	}
	
	public void clickLoginButton() {
		page.locator(".submit-button").click();
	}
	
	
	public void login(String url,String username,String password) {
		//Login
		navigateToUrl(url);
		enterUsername(username);
		enterPassword(password);
		clickLoginButton();
		
	}
}

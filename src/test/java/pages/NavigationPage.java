package pages;

import com.microsoft.playwright.Page;

public class NavigationPage {
	private Page page;
	
	public NavigationPage(Page page) {
		this.page = page;
	}
	
	public void navigateToTheUrl(String url) {
		page.navigate(url);
	}

}

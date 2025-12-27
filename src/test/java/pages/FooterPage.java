package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FooterPage {
	private Page page;
	private Locator copyrightText;
	private Locator footerElement;
	private Locator twitter;
	private Locator linkedin;
	private Locator facebook;
	
	public FooterPage(Page page) {
		this.page = page;
		this.copyrightText = page.locator(".footer_copy");
		this.footerElement = page.locator(".footer");
		this.twitter = page.locator("[data-test='social-twitter']");
	    this.facebook = page.locator("[data-test='social-facebook']");
	    this.linkedin = page.locator("[data-test='social-linkedin']");
	}
	
	public String getCopyrightText() {
		return copyrightText.textContent();
	}
	
	public void scrollToFooter() {
		footerElement.scrollIntoViewIfNeeded();;
	}
	
	public boolean allSocialIconsVisible() {
		scrollToFooter();
		return facebook.isVisible() && linkedin.isVisible() && twitter.isVisible();
	}
	
	public boolean allSocialIconsClickable() {
		return facebook.getAttribute("href").contains("facebook.com/saucelabs")
			   && linkedin.getAttribute("href").contains("linkedin.com/company/sauce-labs")
			   && twitter.getAttribute("href").contains("twitter.com/saucelabs");
	}

}

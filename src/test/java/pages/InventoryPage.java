package pages;

import com.microsoft.playwright.Page;

public class InventoryPage {
	private Page page;
	
	private String addToCartOnesie = "button#add-to-cart-sauce-labs-onesie";
	private String cartBadge= ".shopping_cart_badge";
	private String cartIcon = ".shopping_cart_link";

	public InventoryPage(Page page) {
		this.page=page;
	}
	
	public void addOnesieToCart() {
		page.locator(addToCartOnesie).click();
	}
	
	public boolean isCartBadgeVisible() {
		return page.locator(cartBadge).isVisible();
	}
	
	public String getCartBadgeCount() {
		return page.locator(cartBadge).textContent();
	}
	
	public void clickCartIcon() {
		page.locator(cartIcon).click();
	}
	
	public String getCurrentUrl() {
		return page.url();
	}
	
	
}

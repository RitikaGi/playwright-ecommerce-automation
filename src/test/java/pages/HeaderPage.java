package pages;

import java.util.List;

import com.microsoft.playwright.Page;

public class HeaderPage {
    private Page page;
    
    // Header locators
    private String cartIcon = ".shopping_cart_link";
    private String cartBadge = ".shopping_cart_badge";
    private String hamburgerMenu = "#react-burger-menu-btn";
    private String pageTitle = ".title";
    private String logoutMenuItem= "#logout_sidebar_link";
    private String resetAppState = "#reset_sidebar_link";
    private String about = "#about_sidebar_link";
    private String allItems = "#inventory_sidebar_link";
    
    
    public HeaderPage(Page page) {
        this.page = page;
    }
    
    public void clickCartIcon() {
        page.locator(cartIcon).click();
    }
    
    public boolean isCartBadgeVisible() {
        return page.locator(cartBadge).isVisible();
    }
    
    public String getCartBadgeCount() {
        return page.locator(cartBadge).textContent();
    }
    
    public void openMenu() {
        page.locator(hamburgerMenu).click();
    }
    
    public String getPageTitle() {
        return page.locator(pageTitle).textContent();
    }
    
    public List<String> getMenuItems() {
    	return page.locator(".bm-item.menu-item").allTextContents();
    }
    
    public void clickLogout() {
    	page.locator(logoutMenuItem).click();
    }
    
    public void clickResetAppState() {
    	page.locator(resetAppState).click();
    }
    
    public void clickAbout() {
    	page.locator(about).click();
    }
    
    public void clickAllItems() {
    	page.locator(allItems).click();
    }
    
}
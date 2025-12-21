package pages;

import com.microsoft.playwright.Page;

public class HeaderPage {
    private Page page;
    
    // Header locators
    private String cartIcon = ".shopping_cart_link";
    private String cartBadge = ".shopping_cart_badge";
    private String hamburgerMenu = "#react-burger-menu-btn";
    private String pageTitle = ".title";
    
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
}
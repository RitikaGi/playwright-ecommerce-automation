package pages;

import com.microsoft.playwright.Page;

public class CartPage {
    private Page page;
    
    private String removeOnesieFromCart= "button#remove-sauce-labs-onesie";
    private String itemPrice = ".inventory_item_price";
    private String itemName = ".inventory_item_name";
    
    public CartPage(Page page) {
    	this.page=page;
    }
    
    public boolean isRemoveButtonVisible() {
    	return page.locator(removeOnesieFromCart).isVisible();
    }
    
    public void removeProductFromCart() {
    	page.locator(removeOnesieFromCart).click();
    }
    
    public String getItemName() {
		return page.locator(itemName).textContent();
    }
    
    public String getItemPrice() {
		return page.locator(itemPrice).textContent();
    }
    
    
    
}

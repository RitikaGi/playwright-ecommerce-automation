package pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartPage {
    private Page page;
    
    public CartPage(Page page) {
    	this.page=page;
    }
     
    
    // For getting details of a SPECIFIC product in cart (by product name)
    public Map<String, String> getProductDetailsFromCart(String productName) {
        Locator productContainer = page.locator(".cart_item")
                .filter(new Locator.FilterOptions()
                    .setHas(page.locator(".inventory_item_name").getByText(productName)))
                .first();
	    // Don't look for .inventory_item first - directly get the elements
        Map<String, String> details = new HashMap<>();
	    details.put("name", productContainer.locator(".inventory_item_name").textContent().trim());
	    details.put("description", productContainer.locator(".inventory_item_desc").textContent().trim());
	    details.put("price", productContainer.locator(".inventory_item_price").textContent().replace("$", "").trim());
	    
	    return details;
	}
    
    public String getItemQuantityOnCart(String product) {
    	 Locator productContainer =  page.locator(".cart_item")
     			.filter(new Locator.FilterOptions()
     					.setHas(page.locator(".inventory_item_name").getByText(product))).first();
    	 return productContainer.locator(".cart_quantity").textContent().trim();
     					
    }
    
    public void removeItemFromCart(String product) {
    	Locator productContainer =  page.locator(".cart_item")
     			.filter(new Locator.FilterOptions()
     					.setHas(page.locator(".inventory_item_name").getByText(product))).first();
    	productContainer.locator("button").click();
    }
    
    
}

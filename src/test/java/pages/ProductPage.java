package pages;

import java.util.HashMap;
import java.util.Map;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductPage {

	private Page page;
	
	public ProductPage(Page page) {
		this.page = page;
	}
	
	public void addToCart() {
		page.locator("#add-to-cart").click();
	}
	
	public Map<String, String> getProductDetailsFromPage() {
	    Map<String, String> details = new HashMap<>();
	    
	    // Don't look for .inventory_item first - directly get the elements
	    details.put("name", page.locator(".inventory_details_name").textContent().trim());
	    details.put("description", page.locator(".inventory_details_desc").textContent().trim());
	    details.put("price", page.locator(".inventory_details_price").textContent().replace("$", "").trim());
	    
	    return details;
	}
	
	public void navigateBackToInventory() {
		page.locator("button#back-to-products").click();
	}
}

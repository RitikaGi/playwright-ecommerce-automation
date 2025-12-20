package pages;

import java.util.ArrayList;
import java.util.List;

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
	
	public void getProductsHeader() {
		page.locator(".title");
	}
	
	public boolean isProductsHeaderVisible() {
		return page.locator(".title").isVisible();
	}
	
	public List<String> getAllProductsImageSources() {
		List<String> imageSources = new ArrayList<>();
		var images = page.locator(".inventory_item_img").all();
		for(var img: images) {
			String src = img.getAttribute("src");
			if(src!=null && !src.isEmpty()) {
			imageSources.add(src);
			 
			}
		}
		return imageSources;
	}
	
	public boolean areAllProductsImageSame() {
		List<String> imageSources = getAllProductsImageSources();
		if(imageSources.isEmpty())
		return false;
		String firstImage = imageSources.get(0);
		if(firstImage==null) {
			return false;
		}
		return imageSources.stream().allMatch(src->src.equals(firstImage));
	}
	
	
}

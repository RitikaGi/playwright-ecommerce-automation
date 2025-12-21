package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class InventoryPage {
	private Page page;
	Locator productContainer;
	

	public InventoryPage(Page page) {
		this.page=page;
	}
	
	public void clickOnItem(String itemName) {
		page.getByText(itemName).click();
	}
	
	public String getCurrentUrl() {
		return page.url();
	}
	
	public void getProductsHeader() {
		page.locator(".title");
	}
	
	public int itemCount() {
		return page.locator(".inventory_item").count();
	}
	
	public boolean isProductsHeaderVisible() {
		return page.locator(".title").isVisible();
	}
	
	public Locator getProductName(String productName) {
		productContainer = page.locator(".inventory_item")
		        .filter(new Locator.FilterOptions()
		            .setHas(page.locator(".inventory_item_name").getByText(productName)))
		        .first();
		return productContainer;
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
	
	public List<String> getAllProductsNames() {
		List<String> names = new ArrayList<>();
		var itemNames = page.locator(".inventory_item_name").all();
		for(var name: itemNames) {
			String text = name.textContent();
			if(text!=null && !text.isEmpty()) {
				names.add(text);
			}
		}
		return names;
	}
	
	public Map<String,String> getProductDetailsFromList(String productName){
		productContainer = getProductName(productName);
		Map<String,String> details = new HashMap<>();
		details.put("name", productContainer.locator(".inventory_item_name").textContent().trim());
		details.put("description", productContainer.locator(".inventory_item_desc").textContent().trim());
		details.put("price", productContainer.locator(".inventory_item_price").textContent().replace("$", "").trim());
		
		return details;
	}
	
	public List<Double> getAllProductsPrice() {
		List<Double> prices = new ArrayList<>();
		var itemPrices = page.locator(".inventory_item_price").all();
		for(var price: itemPrices) {
			String priceText = price.textContent().replace("$", "").trim();
            prices.add(Double.parseDouble(priceText));
			}
		
		return prices;
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
	
	
	public void sortProductsBy(String sortOption) {
		page.selectOption(".product_sort_container", sortOption);
	}
	
	public boolean areProductsSortedByNamesAscending() {
		List<String> actualNames = getAllProductsNames();
		List<String> sortedNames = new ArrayList<>(actualNames);
		Collections.sort(sortedNames);
		return actualNames.equals(sortedNames);
	}
	
	public boolean areProductsSortedByNamesDescending() {
		List<String> actualNames = getAllProductsNames();
		List<String> sortedNames = new ArrayList<>(actualNames);
		Collections.sort(sortedNames, Collections.reverseOrder());
		return actualNames.equals(sortedNames);
	}
	
	public boolean areProductsSortedByPriceAscending() {
		List<Double> actualPrices = getAllProductsPrice();
		List<Double> sortedPrices = new ArrayList<>(actualPrices);
		Collections.sort(sortedPrices);
		return actualPrices.equals(sortedPrices);
	}
	
	public boolean areProductsSortedByPriceDescending() {
		List<Double> actualPrices = getAllProductsPrice();
        List<Double> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices, Collections.reverseOrder());
        return actualPrices.equals(sortedPrices);
	}
	
	public void addToCartByProductName(String productName) {
		productContainer = getProductName(productName);
		productContainer.locator("button").click();
	}
	
	public Locator addToCartOrRemoveButton() {
		return productContainer.locator("button");
	}
	
	
	
}

package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductCatalog extends BaseTest{
	  
	@Test
	//TC_PRODUCT_001: Verify All Products Load
	public void TC_PRODUCT_001_Verify_All_Products_Load(){
		
		assertTrue(page.url().contains("/inventory.html"));
		assertEquals(6,inventoryPage.itemCount());
		assertTrue(inventoryPage.getAllProductsImageSources().size()==6);
		assertTrue(inventoryPage.getAllProductsNames().size()==6);
		assertTrue(inventoryPage.getAllProductsPrice().size()==6);
	}
	
	@Test
	//TC_PRODUCT_002: Sort Products A to Z
	public void TC_PRODUCT_002_Sort_Products_A_to_Z() {
		
		inventoryPage.sortProductsBy("az");
		assert inventoryPage.areProductsSortedByNamesAscending(): "Products are not sorted A-Z";
	}
	
	@Test
	//TC_PRODUCT_003: Sort Products Z to A
	public void TC_PRODUCT_003_Sort_Products_Z_to_A() {
		
		inventoryPage.sortProductsBy("za");
		assert inventoryPage.areProductsSortedByNamesDescending(): "Products are not sorted Z-A";
	}
	
    @Test
	//TC_PRODUCT_004: Sort Price Low to High
    public void TC_PRODUCT_004_Sort_Price_Low_to_High() {
    	
    	inventoryPage.sortProductsBy("lohi");
    	assert inventoryPage.areProductsSortedByPriceAscending(): "Products are not sorted Low to High";
    }
	
	@Test
	//TC_PRODUCT_005: Sort Price High to Low
	public void TC_PRODUCT_005_Sort_Price_High_to_Low() {
		
    	inventoryPage.sortProductsBy("hilo");
    	assert inventoryPage.areProductsSortedByPriceDescending(): "Products are not sorted High to Low";
    }
	
	@Test
	//TC_PRODUCT_006: Verify Product Details
	public void TC_PRODUCT_006_Verify_Product_Details() {
		
		Map<String,String> listDetails = inventoryPage.getProductDetailsFromList("Sauce Labs Backpack");
		inventoryPage.clickOnItem("Sauce Labs Backpack");
		Map<String,String> pageDetails = productPage.getProductDetailsFromPage();
		Assert.assertEquals(pageDetails, listDetails, 
                "Product details mismatch between list and details page"); //one line assertion
		Assert.assertEquals(pageDetails.get("name"), listDetails.get("name"), "Product name mismatch");
		Assert.assertEquals(pageDetails.get("description"), listDetails.get("description"), "Product description mismatch");
		Assert.assertEquals(pageDetails.get("price"), listDetails.get("price"), "Product price mismatch");
	}
	
	@Test
	//TC_PRODUCT_007: Navigate Back from Product Details
	public void TC_PRODUCT_007_Navigate_Back_from_Product_Details() {
		
		inventoryPage.clickOnItem("Sauce Labs Backpack");
		productPage.navigateBackToInventory();
		assertTrue(page.url().contains("inventory.html"));
	}
	
	@Test
	//TC_PRODUCT_008: Add to Cart from Listing Page
	public void TC_PRODUCT_008_Add_to_Cart_from_Listing_Page() {
		
		inventoryPage.addToCartByProductName("Sauce Labs Backpack");
		assert inventoryPage.addToCartOrRemoveButton().textContent().contains("Remove");
		assertEquals("1",headerPage.getCartBadgeCount());
	}
	
	@Test
	//TC_PRODUCT_009: Add Multiple Products
	public void TC_PRODUCT_009_Add_Multiple_Products() {
		String[] products = {"Sauce Labs Onesie", "Sauce Labs Bike Light"};
		 for(String product: products) {
			 inventoryPage.addToCartByProductName(product);
		 }
		assertEquals("3", headerPage.getCartBadgeCount());
	}
	
	@Test
	//TC_PRODUCT_010: Add from Product Detail Page
	public void TC_PRODUCT_010_Add_from_Product_Detail_Page() {
		
		inventoryPage.clickOnItem("Sauce Labs Backpack");
		productPage.addToCart();
		assertEquals("1",headerPage.getCartBadgeCount());
	}
}


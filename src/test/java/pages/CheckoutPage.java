package pages;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckoutPage {
	private Page page;
	private Locator firstNameInput;
	private Locator lastNameInput;
	private Locator postalCodeInput;
	private Locator continueButton;
	private Locator cancelButton;
	private Locator getErrorMessage;
	private Locator productContainer;
	private Locator getPaymentInfo;
	private Locator getShippingInfo;
	private Locator cartItem;
	private Locator itemPrice;
	private Locator itemTotalAmount;
	private Locator totalTax;
	private Locator finishButton;
	private Locator greenCheckMark;
	private Locator successMessage;
	private Locator backToHomeButton;
	private Locator confirmationText;

	public CheckoutPage(Page page) {
		this.page = page;
		//Locators 
		this.firstNameInput=page.getByPlaceholder("First Name");
		this.lastNameInput= page.getByPlaceholder("Last Name");
		this.postalCodeInput = page.locator("#postal-code");
		this.continueButton = page.getByText("Continue");
		this.cancelButton = page.getByText("Cancel");
		this.getErrorMessage = page.locator(".error-message-container.error");
		this.getPaymentInfo= page.locator("div[data-test='payment-info-value']");
		this.getShippingInfo= page.locator("div[data-test='shipping-info-value']");
		this.cartItem= page.locator(".cart_item");
		this.itemPrice = page.locator(".inventory_item_price");
		this.itemTotalAmount=page.locator("div[data-test='subtotal-label']");
		this.totalTax=page.locator("div[data-test='tax-label']");
		this.finishButton= page.locator("button[data-test='finish']");
		this.greenCheckMark = page.locator(".pony_express");
		this.successMessage= page.locator(".complete-header");
		this.backToHomeButton=page.locator("button[data-test='back-to-products']");
		this.confirmationText=page.locator(".complete-text");
		
	}
	
	public void fillFirstName(String firstName) {
		firstNameInput.fill(firstName);
	}
	
	public void fillLastName(String lastName) {
		lastNameInput.fill(lastName);
	}
	
	public void fillPostalCode(String postalCode) {
		postalCodeInput.fill(postalCode);
	}
	
	public void clickContinue() {
		continueButton.click();
	}
	
	public void clickCancel() {
		cancelButton.click();
	}
	
	public String getErrorMessage() {
		return getErrorMessage.textContent();
	}
	
	public String getPaymentInfo() {
		return getPaymentInfo.textContent();
	}
	
	public String getShippingAddress() {
		return getShippingInfo.textContent();
	}
	
	public void clickFinishButton() {
		finishButton.click();
	}
	
	public boolean isGreenCheckMarkVisible() {
		return greenCheckMark.isVisible();
	}
	
	public String successMessage() {
		return successMessage.textContent();
	}
	
	public boolean isBackToHomeButtonVisible() {
		return backToHomeButton.isVisible();
	}
	
	public String confirmationMessage() {
		return confirmationText.textContent();
	}
	
	public void clickBackHomeButton() {
		backToHomeButton.click();
	}
	
	
	
	public int getCartItemCount() {
		return cartItem.count();
	}
	
	public double ActualTotalAmount() {
		double actualAmount = ActualItemTotal()+ActualTaxAmount();
		return actualAmount;
	}
	
    public double ExpectedTotalAmount() {
    	double expectedAmount = ExpectedItemTotal()+ExpectedTaxAmount();
		return expectedAmount;
	}
	
	public double ActualItemTotal() {
		String totalText=itemTotalAmount.textContent().trim();
		String totalValue=totalText.replaceAll("[^0-9.]", "");
		double itemTotal = Double.parseDouble(totalValue);
		return itemTotal;
	}
	
	public double ActualTaxAmount() {
		String text=totalTax.textContent().trim();
		String tax_total= text.replaceAll("[^0-9.]", "");
		double tax=Double.parseDouble(tax_total);
		return tax;
	}
	
	public double ExpectedTaxAmount() {
		BigDecimal amount= BigDecimal.valueOf(ExpectedItemTotal());
		BigDecimal tax_value = BigDecimal.valueOf(0.08);;
		BigDecimal taxAmount = amount.multiply(tax_value).setScale(2,RoundingMode.HALF_UP);
		return taxAmount.doubleValue();
	}
	
	public Map<String,String> getProductDetailsFromCheckout(String product){
		productContainer = cartItem
				.filter(new Locator.FilterOptions()
						.setHas(page.locator(".inventory_item_name").getByText(product)));
		Map<String,String> details = new HashMap<>();
		details.put("name", productContainer.locator(".inventory_item_name").textContent().trim());
		details.put("description", productContainer.locator(".inventory_item_desc").textContent().trim());
		details.put("price", productContainer.locator(".inventory_item_price").textContent().replace("$", "").trim());
		return details;
	}
	
	public double ExpectedItemTotal() {
		double totalAmount = 0.0;
		int count = getCartItemCount();
		for(int i=0;i<count;i++) {
			String priceText = cartItem.nth(i).locator(".inventory_item_price").textContent().replace("$", "").trim();
			double price = Double.parseDouble(priceText);
			totalAmount+=price;
			
		}
		return totalAmount;
	}
	
	
}

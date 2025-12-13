package InterviewPrep;

import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class LocatorConcepts {
	
	Playwright playwright;
	static Browser browser;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
      Playwright playwright = Playwright.create();
      try {
    	  browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
      }
      catch(Exception e) {
    	  browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
      }
      
      BrowserContext context = browser.newContext();
      Page page = context.newPage();
      
        page.navigate("https://orangehrm.com/en/30-day-free-trial");  
//      Locator contactSales = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Contact Sales")); 
//      contactSales.click();
        Locator selectCountry = page.locator("select#Form_getForm_Country option");
        System.out.println(selectCountry.count());
        
//        for(int i=0;i<selectCountry.count();i++) {
//           String text=selectCountry.nth(i).textContent();
//           System.out.println(text);
//        }
        
        List<String> countryOptionList = selectCountry.allTextContents();
        for(String e: countryOptionList) {
        	System.out.println(e);
        }
        
//      page.navigate("https://academy.naveenautomationlabs.com/");
//      Locator loginButton = page.locator("text = Login");
//      System.out.println(loginButton.count());;
//      loginButton.click();
      
      
      }

}

package InterviewPrep;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitForSelectorState;

public class WaitMechanisms {
	
	static Playwright playwright;
	static Browser browser;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       playwright = Playwright.create();
       browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
       Page page = browser.newPage();
       
       
//       page.navigate("https://freelance-learn-automation.vercel.app/login", new Page.NavigateOptions().setTimeout(100));
//  //     page.setDefaultNavigationTimeout(500);
//       
//       page.setDefaultTimeout(100);
//       page.locator("#ritika").click();
       
       page.navigate("https://seleniumpractise.blogspot.com/2016/08/how-to-use-explicit-wait-in-selenium.html");
       
       page.locator("//button[text()='Click me to start timer']").click();
       page.locator("//p[text()='WebDriver']").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(18000));
       
       page.close();
       browser.close();
	}

}

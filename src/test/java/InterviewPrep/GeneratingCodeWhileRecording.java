package InterviewPrep;

import java.nio.file.Paths;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;

public class GeneratingCodeWhileRecording {
  public static void main(String[] args) {
    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
        .setHeadless(false));
      BrowserContext context = browser.newContext();
      
      context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(true));
      
      Page page = context.newPage();
      page.navigate("https://academy.naveenautomationlabs.com/");
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login")).click();
      page.frameLocator("#microfe-popup-login").getByPlaceholder("Name").click();
      page.frameLocator("#microfe-popup-login").getByPlaceholder("Name").fill("Keshu");
      page.frameLocator("#microfe-popup-login").getByPlaceholder("Name").press("Tab");
      page.frameLocator("#microfe-popup-login").getByPlaceholder("Email address").fill("keshavi@gmail.com");
      page.frameLocator("#microfe-popup-login").getByPlaceholder("Email address").press("Tab");
      page.frameLocator("#microfe-popup-login").getByPlaceholder("Password").fill("Keshui@123");
      page.frameLocator("#microfe-popup-login").getByPlaceholder("Password").press("Tab");
      page.frameLocator("#microfe-popup-login").getByPlaceholder("Enter your number").click();
      page.frameLocator("#microfe-popup-login").getByPlaceholder("Enter your number").fill("+91 23364-365376");
      page.frameLocator("#microfe-popup-login").getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName("Next")).click();
      
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("")).click();
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Log Out")).click();
      
      context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("trace.zip")));
    }
  }
}
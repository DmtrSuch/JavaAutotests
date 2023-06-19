package Browser;

import Browser.Driver.BaseDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Browser extends BaseDriver {
    public static RemoteWebDriver SetUpBrowser(String url){
        RemoteWebDriver browser = setUp();
        browser.get(url);
        browser.manage().window().maximize();
        return browser;
    }
}

package Browser;

import Browser.Driver.Driver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Browser {
    public static RemoteWebDriver SetUpBrowserDefault(String url){
        RemoteWebDriver browser = Driver.setUpDriverDefault();
        browser.get(url);
        browser.manage().window().maximize();
        return browser;
    }
}

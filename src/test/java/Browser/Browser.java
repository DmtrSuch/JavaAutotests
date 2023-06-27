package Browser;

import Browser.Driver.Driver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Browser {

    public static final String BrowserDefaultOne = "DefaultOne";
    public static final String BrowserDefaultTwo = "DefaultTwo";

    public static RemoteWebDriver GetBrowser(String url, String browser){
        switch (browser){
            case BrowserDefaultOne: return SetUpBrowserDefault(url);
            case BrowserDefaultTwo: return SetUpBrowserDefaultTwo(url);
        }
        return SetUpBrowserDefault(url);
    }
    public static RemoteWebDriver GetBrowser(String url){
        return GetBrowser(url, "DefaultOne");
    }

    private static RemoteWebDriver SetUpBrowserDefault(String url){
        RemoteWebDriver browser = Driver.setUpDriverDefault();
        browser.get(url);
        browser.manage().window().maximize();
        return browser;
    }

    private static RemoteWebDriver SetUpBrowserDefaultTwo(String url){
        RemoteWebDriver browser = Driver.setUpDriverDefault();
        browser.get(url);
        return browser;
    }
}

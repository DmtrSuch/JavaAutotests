package Pages;

import Config.Config;
import org.openqa.selenium.remote.RemoteWebDriver;

public class GetPages {
    public static LoginPage GetLoginPage(RemoteWebDriver browser) throws InterruptedException {
        String url = Config.Base_Url;
        return new LoginPage(browser, url);
    }
}

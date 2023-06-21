package Tests;

import Browser.*;
import Config.Config;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

import static Pages.GetPages.GetLoginPage;

public class GoogleTest {

    @Test
    public void checkgoogle() throws InterruptedException {
        RemoteWebDriver browser = Browser.SetUpBrowserDefault(Config.Base_Url);
        GetLoginPage(browser);
        Thread.sleep(19999);
    }
}

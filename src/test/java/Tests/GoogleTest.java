package Tests;

import Browser.Browser;
import Config.ConfigProvider;
import Pages.LoginPage;
import Pages.MainPage;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

import static Pages.GetPages.GetLoginPage;

public class GoogleTest extends BaseTest {
    @Override
    protected RemoteWebDriver setUpBrowser(String url) {
        this.authorize = false;
        return Browser.GetBrowser(this.Base_Url, Browser.BrowserDefaultOne);
    }

    @Test
    public void checkgoogle() throws InterruptedException {
        LoginPage login_page = GetLoginPage(this.browser);
        MainPage main_page = login_page.LogIn(ConfigProvider.Base_Login, ConfigProvider.Base_Password);
        Thread.sleep(1000);
    }

}

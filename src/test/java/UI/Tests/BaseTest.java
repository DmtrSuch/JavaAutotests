package UI.Tests;

import UI.Browser.Browser;
import Config.ConfigProvider;
import UI.Pages.GetPages;
import UI.Pages.LoginPage;
import UI.Pages.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.IOException;
import java.util.Set;
import static io.qameta.allure.Allure.step;


abstract public class BaseTest {
    protected RemoteWebDriver browser;
    protected boolean authorize = true;
    protected String Base_Url;
    static protected Set<Cookie> cookies;

    abstract protected RemoteWebDriver setUpBrowser(String url);
    protected MainPage mainpage;

    @BeforeAll
    public static void Cookies() throws InterruptedException {
        step("Before All UI tests/Get cookie");
        RemoteWebDriver browser = Browser.GetBrowser(ConfigProvider.Base_Url, Browser.BrowserDefaultOne);
        LoginPage loginPage = GetPages.GetLoginPage(browser);
        MainPage mainPage = loginPage.LogIn(ConfigProvider.Base_Login, ConfigProvider.Base_Password);
        cookies = browser.manage().getCookies();
        browser.quit();
    }

    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        step("UI Test Setup On Url" + this.Base_Url + ": " + this.getClass().getSimpleName());
        this.Base_Url = ConfigProvider.Base_Url;
        this.browser = setUpBrowser(this.Base_Url);
        if (this.authorize) {
            step("Authorize is True, Get MainPage");
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sameSite")) {
                    if (cookie.getValue().equals("None")) {
                        cookie = new Cookie(cookie.getName(), "Strict");
                    }
                }
                this.browser.manage().addCookie(cookie);
            }
            this.browser.navigate().refresh();
            this.mainpage = GetPages.GetMainPage(browser);
        }
    }

    @AfterEach
    public void tearDown(){
        step("Close UI.Browser");
        this.browser.close();
        this.browser.quit();
    }
}

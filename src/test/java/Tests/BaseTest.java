package Tests;

import Browser.Browser;
import Config.ConfigProvider;
import Pages.BasePage;
import Pages.GetPages;
import Pages.LoginPage;
import Pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.sql.rowset.BaseRowSet;
import java.io.IOException;
import java.util.Set;


/**
 * Базовый класс для инициализации селениума
 */
abstract public class BaseTest {
    protected RemoteWebDriver browser;
    protected boolean authorize = true;
    protected String Base_Url;
    static protected Set<Cookie> cookies;

    abstract protected RemoteWebDriver setUpBrowser(String url);
    protected MainPage mainpage;

    @BeforeClass
    public static void Cookies() throws InterruptedException {
        RemoteWebDriver browser = Browser.GetBrowser(ConfigProvider.Base_Url, Browser.BrowserDefaultOne);
        LoginPage loginPage = GetPages.GetLoginPage(browser);
        MainPage mainPage = loginPage.LogIn(ConfigProvider.Base_Login, ConfigProvider.Base_Password);
        cookies = browser.manage().getCookies();
        browser.quit();
    }

    @Before
    public void setUp() throws IOException, InterruptedException {
        this.Base_Url = ConfigProvider.Base_Url;
        this.browser = setUpBrowser(this.Base_Url);
        if (this.authorize) {
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

    @After
    public void tearDown(){
        this.browser.close();
        this.browser.quit();
    }
}

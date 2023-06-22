package Tests;

import Browser.Browser;
import Config.Config;
import com.codeborne.selenide.Selenide;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;

/**
 * Базовый класс для инициализации селениума
 */
abstract public class BaseTest {
    protected RemoteWebDriver browser;

    @Before
    public void setUp(){
        this.browser = Browser.SetUpBrowserDefault(Config.Base_Url);
    }

    @After
    public void tearDown(){
        this.browser.close();
        this.browser.quit();
    }
}

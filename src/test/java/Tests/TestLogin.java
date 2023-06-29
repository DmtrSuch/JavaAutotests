package Tests;

import Browser.Browser;
import Config.ConfigProvider;
import Pages.LoginPage;
import Utils.UserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.RemoteWebDriver;

import static Pages.GetPages.GetLoginPage;

public class TestLogin extends BaseTest {
    @Override
    protected RemoteWebDriver setUpBrowser(String url) {
        this.authorize = false;
        return Browser.GetBrowser(this.Base_Url, Browser.BrowserDefaultOne);
    }

    @Test
    void CorrectLogin() throws InterruptedException {
        LoginPage login_page = GetLoginPage(this.browser);
        login_page.LogIn(ConfigProvider.Base_Login, ConfigProvider.Base_Password);
    }

    @ParameterizedTest(name = "{0} value")
    @ValueSource(ints = { 5, 8 })
    void UnCorrectLogin(int lenght) throws InterruptedException {
        LoginPage login_page = GetLoginPage(this.browser);
        Assertions.assertThrows(
                TimeoutException.class,
                () -> {login_page.LogIn(
                        UserBuilder.GenerateUserMail(lenght),
                        UserBuilder.GenerateUserPassword(lenght));});
    }

}

package UI.Tests;

import UI.Browser.Browser;
import Config.ConfigProvider;
import UI.Pages.LoginPage;
import Utils.UserBuilder;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.RemoteWebDriver;

import static UI.Pages.GetPages.GetLoginPage;
import static io.qameta.allure.Allure.step;

@Tag("LoginTests")
public class LoginTest extends BaseTest {
    @Override
    protected RemoteWebDriver setUpBrowser(String url) {
        this.authorize = false;
        Allure.step("Set Up UI.Browser with URL" + this.Base_Url);
        return Browser.GetBrowser(this.Base_Url, Browser.BrowserDefaultOne);
    }

    @Test
    @DisplayName("Correct Login Test")
    @Tag("CorrectLogin")
    void CorrectLogin() throws InterruptedException {
        Allure.step("CorrectLogin with login: " +
                ConfigProvider.Base_Login +
                " Password: " +
                ConfigProvider.Base_Password);
        LoginPage login_page = GetLoginPage(this.browser);
        login_page.LogIn(ConfigProvider.Base_Login, ConfigProvider.Base_Password);
    }

    @ParameterizedTest(name = "UnCorrect Login Test With Length Login And Pass: {0}")
    @Tag("IncorrectLogin")
    @ValueSource(ints = { 5, 8 })
    void UnCorrectLogin(int lenght) throws InterruptedException {
        LoginPage login_page = GetLoginPage(this.browser);
        String InCorrectLogin = UserBuilder.GenerateUserMail(lenght);
        String InCorrectPassword = UserBuilder.GenerateUserPassword(lenght);
        Allure.step("InCorrectLogin with login: " +
                InCorrectLogin +
                " Password: " +
                InCorrectPassword);
        TimeoutException exception = Assertions.assertThrows(
                TimeoutException.class,
                () -> {login_page.LogIn(
                        InCorrectLogin,
                        InCorrectPassword);});
        Allure.step("Get UI.Exception: " + exception.getLocalizedMessage());

    }

    @ParameterizedTest(name = "UnCorrect Login Test With Length Pass: {0}")
    @Tag("IncorrectLogin")
    @ValueSource(ints = {5})
    void UnCorrectLoginPassword(int lenght) throws InterruptedException {
        LoginPage login_page = GetLoginPage(this.browser);
        String InCorrectPassword = UserBuilder.GenerateUserPassword(lenght);
        Allure.step("InCorrectLogin with login: " +
                ConfigProvider.Base_Login +
                " Password: " +
                InCorrectPassword);
        TimeoutException exception = Assertions.assertThrows(
                TimeoutException.class,
                () -> {
                    login_page.LogIn(
                            ConfigProvider.Base_Login,
                            InCorrectPassword);
                });
        Allure.step("Get UI.Exception: " + exception.getLocalizedMessage());
    }
}

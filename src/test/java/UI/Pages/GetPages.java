package UI.Pages;

import Config.ConfigProvider;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface GetPages {
     static LoginPage GetLoginPage(RemoteWebDriver browser) throws InterruptedException {
        return new LoginPage(browser, ConfigProvider.Base_Url);
    }

    static MainPage GetMainPage(RemoteWebDriver browser) throws InterruptedException {
        return new MainPage(browser, ConfigProvider.Base_Url + "dashboard");
    }

    static ProfilePage GetProfilePage(RemoteWebDriver browser) throws InterruptedException {
        return new ProfilePage(browser, ConfigProvider.Base_Url + "profile/contacts");
    }
}

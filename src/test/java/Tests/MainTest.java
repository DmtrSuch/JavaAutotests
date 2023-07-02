package Tests;

import Browser.Browser;
import Pages.LoginPage;
import Pages.MainPage;
import Pages.ProfilePage;
import Utils.UserBuilder;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.remote.RemoteWebDriver;

@Tag("MainTests")
public class MainTest extends BaseTest {
    @Override
    protected RemoteWebDriver setUpBrowser(String url) {
        Allure.step("Set Up Browser with URL" + this.Base_Url);
        return Browser.GetBrowser(this.Base_Url, Browser.BrowserDefaultOne);
    }

    @Test
    @DisplayName("LogOut")
    @Tag("Logout")
    void CorrectLogOut() throws InterruptedException {
        Allure.step("LogOut");
        LoginPage loginpage = mainpage.LogOut();
    }

    @ParameterizedTest(name = "Correct Change name with lenght: {0}")
    @Tag("CorrectChangeName")
    @ValueSource(ints = { 5, 8 })
    void CorrectChangeName(int lenght) throws InterruptedException {
        String name = UserBuilder.GenerateUserCompany(lenght);
        Allure.step("Correct Change Name: " + name);
        ProfilePage profilepage = mainpage.GoProfile();
        profilepage.ChangeName(name);
        MainPage MP = profilepage.ReturnMainPage();
        Assertions.assertTrue(MP.CheckName(name));
    }
}

package UI.Tests;

import UI.Browser.Browser;
import UI.Pages.LoginPage;
import UI.Pages.MainPage;
import UI.Pages.ProfilePage;
import Utils.UserBuilder;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.remote.RemoteWebDriver;

@Tag("MainTests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTest extends BaseTest {
    @Override
    protected RemoteWebDriver setUpBrowser(String url) {
        Allure.step("Set Up UI.Browser with URL" + this.Base_Url);
        return Browser.GetBrowser(this.Base_Url, Browser.BrowserDefaultOne);
    }


    @ParameterizedTest(name = "Correct Change name with lenght: {0}")
    @Tag("CorrectChangeName")
    @Order(1)
    @ValueSource(ints = { 5, 8 })
    void CorrectChangeName(int length) throws Exception {
        String name = UserBuilder.GenerateUserCompany(length);
        Allure.step("Correct Change Name: " + name);
        ProfilePage profilepage = mainpage.GoProfile();
        profilepage.ChangeName(name);
        MainPage MP = profilepage.ReturnMainPage();
        Assertions.assertTrue(MP.CheckName(name));
    }

    @Test
    @DisplayName("LogOut")
    @Order(2)
    void CorrectLogOut() throws Exception {
        Allure.step("LogOut");
        LoginPage loginpage = mainpage.LogOut();
    }
}

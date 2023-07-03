package UI.Pages;

import UI.Exception.PageNotOpened;
import UI.Locators.MainPageLocators;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MainPage extends BasePage {
    public MainPage(WebDriver driver, String url) throws InterruptedException {
        super(driver, url);
    }

    int count_try = 4;
    MainPageLocators locators  = new MainPageLocators();

    @Step("LogUot")
    public LoginPage LogOut() throws Exception {
        Allure.step("Logout from mainpage");
        while (count_try != 0) {
            try {
                this.click(locators.button_menu_logout_locator);
                this.click(locators.button_logout_locator);
                return GetPages.GetLoginPage((RemoteWebDriver) this.driver);
            } catch (Exception e) {
                count_try--;
            }
        }
        throw new Exception("Can't LogOut");
    }

    @Step("Go To Profile page")
    public ProfilePage GoProfile() throws InterruptedException {
        Allure.step("Go To Profile page");
        this.click(locators.button_profile_locator);
        return GetPages.GetProfilePage((RemoteWebDriver) this.driver);
    }

    @Step("Check Name Of Profile")
    public boolean CheckName(String name) throws InterruptedException {
        Allure.step("Check name profile");
        return this.element_present(new By.ByXPath("//div[contains(text(), \"" + name + "\")]"));
    }

}

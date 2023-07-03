package UI.Pages;

import UI.Locators.ProfilePageLocators;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ProfilePage extends BasePage {
    public ProfilePage(WebDriver driver, String url) throws InterruptedException {
        super(driver, url);
    }

    ProfilePageLocators locators = new ProfilePageLocators();

    @Step("Change name")
    public void ChangeName(String newname) throws InterruptedException {
        Allure.step("Change name on:" + newname);
        this.write(locators.name_field_locator, newname);
        this.click(locators.save_button_locator);
    }

    @Step("Return Main Page")
    public MainPage ReturnMainPage() throws InterruptedException {
        Allure.step("Return To Main Page");
        this.click(locators.main_page_button_locator);
        return GetPages.GetMainPage((RemoteWebDriver) this.driver);
    }
}

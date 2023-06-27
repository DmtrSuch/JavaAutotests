package Pages;

import Locators.LoginPageLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class LoginPage extends BasePage {

    LoginPageLocators locators  = new LoginPageLocators();

    public LoginPage(WebDriver driver, String url) throws InterruptedException {
        super(driver, url);
    }

    public MainPage LogIn(String Login, String Password) throws InterruptedException {
        this.click(this.locators.button_login_locator);
        this.write(this.locators.login_input_locator, Login);
        this.write(this.locators.password_input_locator, Password);
        this.click(this.locators.enter_button_locator);
        return GetPages.GetMainPage((RemoteWebDriver) this.driver);
    }
}

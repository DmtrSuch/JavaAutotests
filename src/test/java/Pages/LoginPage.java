package Pages;

import Locators.LoginPageLocators;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver, String url) throws InterruptedException {
        super(driver, url);
        this.locators = new LoginPageLocators();
    }
}

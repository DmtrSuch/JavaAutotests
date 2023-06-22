package Pages;

import Locators.LoginPageLocators;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    LoginPageLocators locators  = new LoginPageLocators();

    public LoginPage(WebDriver driver, String url) throws InterruptedException {
        super(driver, url);
    }

    public void TryAll() throws InterruptedException {
        assert this.click(this.locators.button_login_locator);
        assert this.write(this.locators.login_input_locator, "amogus");
        assert this.text_present(this.locators.check_text_locator, "Или войдите с помощью соцсетей");
        assert this.element_present(this.locators.check_element_present_locator);
    }
}

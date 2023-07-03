package UI.Locators;

import org.openqa.selenium.By;

public class LoginPageLocators {

    public final By.ByXPath button_login_locator =
            new By.ByXPath("//a[@href=\"/pro/\"]/../div/div[1]");
    public final By.ByXPath login_input_locator =
            new By.ByXPath("//input[@name=\"email\"]");
    public final By.ByXPath password_input_locator =
            new By.ByXPath("//input[@name=\"password\"]");

    public final By.ByXPath enter_button_locator =
            new By.ByXPath("//div[3]/../div[contains(@class, \"button\")]");

}

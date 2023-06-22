package Locators;

import org.openqa.selenium.By;

public class LoginPageLocators {

    public final By.ByXPath button_login_locator = new By.ByXPath("//a[@href=\"/pro/\"]/../div/div[1]");
    public final By.ByXPath login_input_locator = new By.ByXPath("//input[@name=\"email\"]");

    public final By.ByXPath check_text_locator =
            new By.ByXPath("//div[contains(@class, \"authForm\")]" +
                    "//div[contains(@class, \"socialList\")]/../div[2]");

    public final By.ByXPath check_element_present_locator = new By.ByXPath("//a[contains(@href, \"password\")]");

    public LoginPageLocators(){};
}

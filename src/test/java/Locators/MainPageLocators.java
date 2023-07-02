package Locators;

import org.openqa.selenium.By;

public class MainPageLocators {
    public final By.ByXPath button_menu_logout_locator =
            new By.ByXPath("//div[contains(@class, \"userName\")]");
    public final By.ByXPath button_logout_locator =
            new By.ByXPath("//a[@href=\"/logout\"]");
    public final By.ByXPath button_profile_locator =
            new By.ByXPath("//a[@href=\"/profile\"]");
}

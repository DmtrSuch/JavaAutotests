package UI.Locators;

import org.openqa.selenium.By;

public class ProfilePageLocators {
    public final By.ByXPath name_field_locator =
            new By.ByXPath("//div[@data-name=\"fio\"]//input");
    public final By.ByXPath save_button_locator =
            new By.ByXPath("//button");
    public final By.ByXPath main_page_button_locator =
            new By.ByXPath("//a[@href=\"/dashboard\"]");
}

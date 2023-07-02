package Pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;

import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import Exception.PageNotOpened;

import static Utils.Utils.waiter;

public abstract class BasePage {
    protected WebDriver driver;
    protected String url;
    int time_to_wait = 8;
    JavascriptExecutor js;

    public BasePage(WebDriver driver, String url) throws InterruptedException {
        Allure.step("Init page with url: " + url + "In class" + this.getClass().getSimpleName());
        this.driver = driver;
        this.url = url;
        Assertions.assertTrue(is_opened());
        js = (JavascriptExecutor)this.driver;
    }

    @Step("Check page is open")
    private boolean check_url() throws PageNotOpened {
        Allure.step("Check page is open");
        if (!this.driver.getCurrentUrl().equals(this.url)){
            throw new PageNotOpened(String.format(this.url + " did not opened in " + this.getClass().getName() +
                " Current_url: " + this.driver.getCurrentUrl()));
        }
        return true;
    }

    private boolean is_opened() throws InterruptedException {
        return waiter(() -> {
            try {
                return check_url();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, 80, 1);
    }

    @Step("Wait element")
    private WebDriverWait waits(){
        Allure.step("Wait element");
        return this.waits(time_to_wait);
    }

    @Step("Wait element")
    private WebDriverWait waits(int timeout){
        Allure.step("Wait element");
        return new WebDriverWait(this.driver, Duration.ofSeconds(timeout));
    }

    @Step("Find element By locator: [{locator}]")
    protected WebElement find(By.ByXPath locator, int timeout) {
        Allure.step("Find element By locator: " + locator);
        return this.waits(timeout).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @Step("Find element By locator: [{locator}]")
    protected WebElement find(By.ByXPath locator) {
        Allure.step("Find element By locator: " + locator);
        return this.find(locator, this.time_to_wait);
    }
    private void scroll_to(WebElement element){
          this.js.executeScript("arguments[0].scrollIntoView(true)", element);
    }

    @Step("Click element By locator: [{locator}]")
    protected boolean click(By.ByXPath locator, int timeout) throws InterruptedException {
        Allure.step("Click element By locator: " + locator);
        return waiter(() -> {
            boolean result = false;
            try {
                WebElement element = this.move_to_element_by_locator(locator, timeout);
                element.click();
                result = true;
            } catch (StaleElementReferenceException e){
                Allure.step("get SERE");
            }
            return result;
        }, timeout, 1);
    }

    @Step("Click element By locator: [{locator}]")
    protected boolean click(By.ByXPath locator) throws InterruptedException {
        Allure.step("Click element By locator: " + locator);
        return this.click(locator, this.time_to_wait);
    }

    @Step("Write in element By locator: [{locator}] Text: [{words}]")
    protected boolean write(By.ByXPath locator, String words, int timeout) throws InterruptedException {
        Allure.step("Write in element By locator: " + locator + " Text: " + words);
        return waiter(() -> {
            boolean result = false;
            try {
                WebElement element = this.move_to_element_by_locator(locator, timeout);
                element.clear();
                element.sendKeys(words);
                result = true;
            } catch (StaleElementReferenceException e){
                Allure.step("get SERE");
            }
            return result;
        }, timeout, 1);
    }

    @Step("Write in element By locator: [{locator}] Text: [{words}]")
    protected boolean write(By.ByXPath locator, String words) throws InterruptedException {
        Allure.step("Write in element By locator: " + locator + " Text: " + words);
        return this.write(locator, words, this.time_to_wait);
    }

    @Step("Scroll To Element By Locator: [{locator}]")
    protected WebElement move_to_element_by_locator(By.ByXPath locator, int timeout){
        Allure.step("Scroll To Element By Locator: " + locator);
        WebElement element = this.find(locator, timeout);
        this.scroll_to(element);
        return element;
    }

    @Step("Scroll To Element By Locator: [{locator}]")
    protected WebElement move_to_element_by_locator(By.ByXPath locator){
        Allure.step("Scroll To Element By Locator: " + locator);
        return this.move_to_element_by_locator(locator, this.time_to_wait);
    }

    @Step("Check Element Not Present By Locator: [{locator}]")
    protected boolean not_element_present(By.ByXPath locator, int timeout){
        Allure.step("Check Element Not Present By Locator: " + locator);
        try {
            this.find(locator, timeout);
        } catch (TimeoutException e) {
            return true;
        }
        return false;
    }

    @Step("Check Element Not Present By Locator: [{locator}]")
    protected boolean not_element_present(By.ByXPath locator){
        Allure.step("Check Element Not Present By Locator: " + locator);
        return this.not_element_present(locator, this.time_to_wait);
    }

    @Step("Check Text Present In Element By Locator: [{locator}] text: [{text}]")
    protected boolean text_present(By.ByXPath locator, String text, int timeout){
        Allure.step("Check Text Present In Element By Locator: " + locator + " text: " + text);
        try{
            // this.waits(timeout).until(ExpectedConditions.textToBePresentInElement(this.find(locator, timeout), text));
            this.waits(timeout).until(ExpectedConditions.textToBePresentInElementValue(locator, text));
            // this.waits(timeout).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));


        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    @Step("Check Text Present In Element By Locator: [{locator}] text: [{text}]")
    protected boolean text_present(By.ByXPath locator, String text) throws InterruptedException {
        Allure.step("Check Text Present In Element By Locator: " + locator + " text: " + text);
        return this.text_present(locator, text, time_to_wait);
    }

    @Step("Check Element Present By Locator: [{locator}]")
    protected boolean element_present(By.ByXPath locator, int timeout){
        Allure.step("Check Element Present By Locator: " + locator);
        return !not_element_present(locator, timeout);
    }

    @Step("Check Element Present By Locator: [{locator}]")
    protected boolean element_present(By.ByXPath locator){
        Allure.step("Check Element Present By Locator: " + locator);
        return this.element_present(locator, this.time_to_wait);
    }
}


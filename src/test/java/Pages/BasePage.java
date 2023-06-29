package Pages;

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
    int time_to_wait = 15;
    JavascriptExecutor js;

    public BasePage(WebDriver driver, String url) throws InterruptedException {
        this.driver = driver;
        this.url = url;
        Assertions.assertTrue(is_opened());
        js = (JavascriptExecutor)this.driver;
    }


    private boolean check_url() throws PageNotOpened {
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
        }, 10, 1);
    }

    private WebDriverWait waits(){
        return this.waits(time_to_wait);
    }

    private WebDriverWait waits(int timeout){
        return new WebDriverWait(this.driver, Duration.ofSeconds(timeout));
    }

    protected WebElement find(By.ByXPath locator, int timeout) {
        return this.waits(timeout).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement find(By.ByXPath locator) {
        return this.find(locator, this.time_to_wait);
    }
    private void scroll_to(WebElement element){
          this.js.executeScript("arguments[0].scrollIntoView(true)", element);
    }

    protected boolean click(By.ByXPath locator, int timeout) throws InterruptedException {
        return waiter(() -> {
            WebElement element = this.move_to_element_by_locator(locator, timeout);
            element.click();
            return true;
        }, timeout, 1);
    }

    protected boolean click(By.ByXPath locator) throws InterruptedException {
        return this.click(locator, this.time_to_wait);
    }

    protected boolean write(By.ByXPath locator, String words, int timeout) throws InterruptedException {
        return waiter(() -> {
            WebElement element = this.move_to_element_by_locator(locator, timeout);
            element.clear();
            element.sendKeys(words);
            return true;
        }, timeout, 1);
    }

    protected boolean write(By.ByXPath locator, String words) throws InterruptedException{
        return this.write(locator, words, this.time_to_wait);
    }

    protected WebElement move_to_element_by_locator(By.ByXPath locator, int timeout){
        WebElement element = this.find(locator, timeout);
        this.scroll_to(element);
        return element;
    }
    protected WebElement move_to_element_by_locator(By.ByXPath locator){
        return this.move_to_element_by_locator(locator, this.time_to_wait);
    }

    protected boolean not_element_present(By.ByXPath locator, int timeout){
        try {
            this.find(locator, timeout);
        } catch (TimeoutException e) {
            return true;
        }
        return false;
    }

    protected boolean not_element_present(By.ByXPath locator){
        return this.not_element_present(locator, this.time_to_wait);
    }

    protected boolean text_present(By.ByXPath locator, String text, int timeout){
        try{
            this.waits(timeout).until(ExpectedConditions.textToBePresentInElement(this.find(locator, timeout), text));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    protected boolean text_present(By.ByXPath locator, String text){
        return this.text_present(locator, text, time_to_wait);
    }

    protected boolean element_present(By.ByXPath locator, int timeout){
        return !not_element_present(locator, timeout);
    }

    protected boolean element_present(By.ByXPath locator){
        return this.element_present(locator, this.time_to_wait);
    }
}


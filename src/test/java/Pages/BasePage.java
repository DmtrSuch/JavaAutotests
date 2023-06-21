package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TimeoutException;


import java.util.function.Supplier;

import Locators.BasePageLocators;


public abstract class BasePage {
    WebDriver driver;
    String url;
    BasePageLocators locators;

    public BasePage(WebDriver driver, String url) throws InterruptedException {
        this.driver = driver;
        this.url = url;
        assert is_opened();
    }


    private boolean check_url() throws Exception {
        if (!this.driver.getCurrentUrl().equals(this.url)){
            throw new Exception(String.format(this.url + " did not opened in " + this.getClass().getName() +
                " Current_url: " + this.driver.getCurrentUrl()));
        }
        return true;
    }

    protected  <T> T waiter(Supplier<T> method, int timeout, long interval) throws InterruptedException {
        long started = System.currentTimeMillis();
        Exception lastException = null;
        while (System.currentTimeMillis() - started < timeout){
            try {
                return method.get();
            } catch (Exception e) {
                lastException = e;
            }
            Thread.sleep(interval*1000);
        }
        throw new TimeoutException("Method " + method.getClass().getName() +
                " timed out in " + timeout + "sec with exception: " + lastException);
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
}

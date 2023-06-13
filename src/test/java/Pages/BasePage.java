//package Pages;
//
//import org.junit.Assert;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.support.ui.ExpectedCondition;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.support.ui.ExpectedCondition;
//import org.openqa.selenium.StaleElementReferenceException;
//import org.openqa.selenium.ElementNotInteractableException;
//import org.openqa.selenium.TimeoutException;
//import org.openqa.selenium.WebDriverException;
//
//
//import java.util.Date;
//import Locators.BasePageLocators;
//
//import static org.junit.Assert.fail;
//
//
//    public abstract class BasePage {
//        WebDriver driver;
//        String url;
//        BasePageLocators locators;
//
//        public BasePage(WebDriver driver, String url) {
//            this.driver = driver;
//            this.url = url;
//            check(driver);
//        }
//
//        public void sendKeys(By element, String keys) {
//            driver.findElement(element).sendKeys(keys);
//        }
//
//        public void click(By element) {
//
//            if(isElementPresent(element)){
//                driver.findElement(element).click();
//            }
//            else Assert.fail("Не удалось кликнуть на элемент");
//
//        }
//
//        public boolean isElementPresent(By element) {
//            try {
//                driver.findElement(element).isDisplayed();
//                return true;
//            } catch (NoSuchElementException e) {
//                return false;
//            }
//        }
//
//        public void assertTrue(WebDriver driver, int time, By xpath, String badMessage, String goodMessage){
//            Assert.assertTrue( badMessage,
//                    new WebDriverWait(driver, time).
//                            until((ExpectedCondition<Boolean>) d -> isElementPresent(xpath)));
//            System.out.println(new Date()+" - " + goodMessage);
//        }
//
//        abstract protected void check(WebDriver driver);
//    }

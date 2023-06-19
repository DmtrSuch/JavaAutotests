package Tests;

import Browser.Browser;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

public class GoogleTest {
    private final static String BASE_URL = "https://www.google.com/";

    @Test
    public void checkgoogle() throws MalformedURLException, InterruptedException {
        RemoteWebDriver browser = Browser.SetUpBrowser(BASE_URL);
        Thread.sleep(19999);
    }
}

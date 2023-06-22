package Tests;

import Pages.LoginPage;
import org.junit.Test;


import static Pages.GetPages.GetLoginPage;

public class GoogleTest extends BaseTest {

    @Test
    public void checkgoogle() throws InterruptedException {
        LoginPage login_page = GetLoginPage(browser);
        login_page.TryAll();
        Thread.sleep(19999);
    }

    @Test
    public void checkgoogle2() throws InterruptedException {
        LoginPage login_page = GetLoginPage(browser);
        login_page.TryAll();
        Thread.sleep(19999);
    }
}

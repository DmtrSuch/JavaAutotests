package UI.Browser.Driver;

import Config.ConfigProvider;
import io.qameta.allure.Allure;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;

public interface Driver {
    /**fgv
     * Инициализация selenide с настройками
     */
    static RemoteWebDriver setUpDriverDefault() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setCapability("enableVNC", true);
        try {
            if (System.getenv("SELENOID_HUB_HOST") != null){
                Allure.step("SetUpDefaultDriver With GitHubDriver");
                return new RemoteWebDriver(
                        URI.create("http://localhost:4444/wd/hub").toURL(),
                        capabilities
                );
            } else {
                Allure.step("SetUpDefaultDriver With 100 Chrome");
                capabilities.setVersion("100.0");
                return new RemoteWebDriver(
                        URI.create("http://" + ConfigProvider.Selenoid_Cont_Url + "/wd/hub").toURL(),
                        capabilities
                );
            }
        } catch (Exception e) {
            System.out.println("ERROR!!!!");
            throw new RuntimeException(e + "URL:" + "http://" + System.getenv("SELENOID_HUB_HOST") + ":4444/wd/hub");
        }
    }

}

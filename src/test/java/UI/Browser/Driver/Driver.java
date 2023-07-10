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
        capabilities.setVersion("100.0");
        Allure.step("SetUpDefaultDriver With 100 Chrome");
        capabilities.setCapability("enableVNC", true);
        try {
            String gh_sd = System.getenv("SELENOID_HUB_HOST");
            if (gh_sd != null){
                return new RemoteWebDriver(
                        URI.create(gh_sd).toURL(),
                        capabilities
                );
            } else {
                return new RemoteWebDriver(
                        URI.create("http://" + ConfigProvider.Selenoid_Cont_Url + "/wd/hub").toURL(),
                        capabilities
                );
            }
        } catch (MalformedURLException e) {
            System.out.println("ERROR!!!!");
            throw new RuntimeException(e);
        }
    }

}

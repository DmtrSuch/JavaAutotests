package Browser.Driver;

import Config.ConfigProvider;
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
        capabilities.setCapability("enableVNC", true);
        try {
            return new RemoteWebDriver(
                    URI.create("http://"+ ConfigProvider.Selenoid_Url + "/wd/hub").toURL(),
                    capabilities
            );
        } catch (MalformedURLException e) {
            System.out.println("ERROR!!!!");
            throw new RuntimeException(e);
        }
    }

}

package API.Test;

import Config.ConfigProvider;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("MainApiTests")
public class MainApiTest extends BaseTest{

    @Test
    @DisplayName("API_test_login")
    @Tag("Login_API")
    void LoginAPI() throws Exception {
        this.authorize = false;
        Allure.step("LoginPage");
        this.Login(ConfigProvider.Base_Login, ConfigProvider.Base_Password, 200);
    }

}

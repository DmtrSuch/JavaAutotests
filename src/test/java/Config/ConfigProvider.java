package Config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public interface ConfigProvider {
    static Config readConfig(){
        return ConfigFactory.systemProperties().hasPath("testProfile")
                ? ConfigFactory.load(ConfigFactory.systemProperties().getString("testProfile"))
                : ConfigFactory.load("application.conf");
    }
    String Base_Url = readConfig().getString("url");
    String Selenoid_Url = readConfig().getString("selenoid_url");
    String Selenoid_Cont_Url = readConfig().getString("selenoid_url_compose");

    String Base_Login = readConfig().getString("userParams.default_user.login");
    String Base_Password = readConfig().getString("userParams.default_user.password");

}

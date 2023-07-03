package API.Test;
import API.Client.ApiClient;
import Config.ConfigProvider;
import okhttp3.Response;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

abstract public class BaseTest {
    protected boolean authorize = true;
    protected ApiClient apiClient;

    @BeforeEach
    public void setup() throws Exception {
        if (authorize) {
            this.Login(ConfigProvider.Base_Login, ConfigProvider.Base_Password, 200);
        }
    }

    public String createSegment(String name, String passCondition, String objectType) throws IOException, ParseException {
        Response response = apiClient.postCreateSegment(name, passCondition, objectType);
        Assertions.assertEquals(response.code(), 200);
        JSONObject jsonResponse = new JSONObject(response.body().string());
        Assertions.assertEquals(jsonResponse.getString("name"), name);
        return jsonResponse.getString("id");
    }

    public void Login(String login, String password, int expectedStatus) throws Exception {
        this.apiClient =
                new ApiClient(ConfigProvider.Base_Url, login, password);
        Response response = this.apiClient.postLogin(login, password);
        Assertions.assertEquals(response.code(), expectedStatus);
    }

    public void checkSegment(String segmentId, int expectedStatus) throws IOException {
        Response response = apiClient.getCheckSegment(segmentId);
        Assertions.assertEquals(response.code(), expectedStatus);
    }

    public String deleteSegment(String segmentId) throws IOException, ParseException {
        Response response = apiClient.postDeleteSegment(segmentId);
        Assertions.assertEquals(response.code(), 200);
        JSONObject jsonResponse = new JSONObject(response.body().string());
        JSONArray successes = jsonResponse.getJSONArray("successes");
        Assertions.assertTrue(successes.length() > 0);
        return successes.getJSONObject(0).getString("source_id");
    }

}

package API.Test;
import API.Client.ApiClient;
import Config.ConfigProvider;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

abstract public class BaseTest {
    protected boolean authorize = true;
    protected ApiClient apiClient;

    @BeforeEach
    public void setup() throws IOException {
        if (authorize) {
            this.apiClient =
                    new ApiClient(ConfigProvider.Base_Url, ConfigProvider.Base_Login, ConfigProvider.Base_Password);
            this.apiClient.postLogin();
        }
    }

    public String createSegment(String name, String passCondition, String objectType) throws IOException, ParseException {
        HttpResponse response = apiClient.postCreateSegment(name, passCondition, objectType);
        Assertions.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject jsonResponse = new JSONObject(EntityUtils.toString((HttpEntity) response.getEntity()));
        Assertions.assertEquals(jsonResponse.getString("name"), name);
        return jsonResponse.getString("id");
    }

    public void Login(String login, String password, int expectedStatus) throws IOException {
        this.apiClient =
                new ApiClient(ConfigProvider.Base_Url, login, password);
        HttpResponse response = this.apiClient.postLogin();
        Assertions.assertEquals(response.getStatusLine().getStatusCode(), expectedStatus);
    }

    public void checkSegment(String segmentId, int expectedStatus) throws IOException {
        HttpResponse response = apiClient.getCheckSegment(segmentId);
        Assertions.assertEquals(response.getStatusLine().getStatusCode(), expectedStatus);
    }

    public String deleteSegment(String segmentId) throws IOException, ParseException {
        HttpResponse response = apiClient.postDeleteSegment(segmentId);
        Assertions.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject jsonResponse = new JSONObject(EntityUtils.toString((HttpEntity) response.getEntity()));
        JSONArray successes = jsonResponse.getJSONArray("successes");
        Assertions.assertTrue(successes.length() > 0);
        return successes.getJSONObject(0).getString("source_id");
    }

    public String uploadImage(String file) throws IOException, ParseException {
        HttpResponse response = apiClient.postUploadImage(file);
        Assertions.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject jsonResponse = new JSONObject(EntityUtils.toString((HttpEntity) response.getEntity()));
        Assertions.assertTrue(jsonResponse.has("id"));
        return jsonResponse.getString("id");
    }

    public String getUrlId(String targetUrl) throws IOException, ParseException {
        HttpResponse response = apiClient.getIdUrl(targetUrl);
        Assertions.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject jsonResponse = new JSONObject(EntityUtils.toString((HttpEntity) response.getEntity()));
        return jsonResponse.getString("id");
    }

    public String createCampaign(String name, String imageId, String urlId) throws IOException, ParseException {
        HttpResponse response = apiClient.postCreateCampaign(name, imageId, urlId);
        Assertions.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject jsonResponse = new JSONObject(EntityUtils.toString((HttpEntity) response.getEntity()));
        return jsonResponse.getString("id");
    }

    public void checkCampaignStatus(String campaignId, String status) throws IOException, ParseException {
        HttpResponse response = apiClient.getCampaignStatus(campaignId);
        JSONObject jsonResponse = new JSONObject(EntityUtils.toString((HttpEntity) response.getEntity()));
        Assertions.assertEquals(jsonResponse.getJSONArray("issues").getJSONObject(0).getString("code"), status);
    }

    public void deleteCampaign(String campaignId) throws IOException, IOException, ParseException {
        HttpResponse response = apiClient.deleteCampaign(campaignId);
        Assertions.assertEquals(response.getStatusLine().getStatusCode(), 204);
        checkCampaignStatus(campaignId, "ARCHIVED");
    }

}

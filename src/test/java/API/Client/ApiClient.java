package API.Client;

import io.qameta.allure.Allure;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class ApiClient {
    private final String base_url;
    private String csrf_token;

    private String access_token;
    private final String user;
    private final String password;
    private final OkHttpClient client;

    public ApiClient(String base_url, String user, String password) {
        Allure.step("Init ApiClient: " + user + "/" + password);
        this.base_url = base_url;
        this.csrf_token = null;
        this.access_token = null;
        this.user = user;
        this.password = password;
        this.client = new OkHttpClient.Builder().build();
    }

    public String getCsrfToken() throws IOException {
        Allure.step("Get CSRF token");
        String url = base_url + "/csrf/";
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String cookieHeader = response.header("set-cookie");
            List<String> cookies = response.headers("set-cookie");
            for (String cookie : cookies) {
                if (cookie.contains("csrftoken")) {
                    csrf_token = cookie.split("=")[1].split(";")[0];
                    break;
                }
            }
            return csrf_token;
        }
    }

    public String getAccessToken(String clientId, String clientSecret) throws Exception {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String requestBody = "grant_type=client_credentials" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret;

        Request request = new Request.Builder()
                .url(base_url + "api/v2/oauth2/token.json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(RequestBody.create(mediaType, requestBody))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Request failed with code: " + response.code());
            }

            String responseBody = response.body().string();
            return extractAccessToken(responseBody);
        }
    }

    private String extractAccessToken(String responseBody) throws Exception {
        JSONObject jsonResponse = new JSONObject(responseBody);
        String accessToken = jsonResponse.getString("access_token");
        return accessToken;
    }

    public Response postLogin(String user, String password) throws Exception {
        Allure.step("Post Login: " + user + "pass:" + password);
        String url = "https://auth-ac.my.com/auth?lang=ru&nosavelogin=0";
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String referer = "https://target.my.com/";
        String continueParam = "https://target.my.com/auth/mycom?state=target_login%3D1%26ignore_opener%3D1#email";
        String requestBody = "email=" + user + "&password=" + password + "&continue=" + continueParam;
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Referer", referer)
//                .header("access_token", access_token)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
//        access_token = getAccessToken(user, password);
        csrf_token = getCsrfToken();
        return response;

    }


    public Response postCreateSegment(String name, String pass_condition, String object_type, int left, int right, String seg_type) throws IOException {
        Allure.step("Create Segment: " + name + "pass:" + pass_condition);
        String url = base_url + "/api/v2/remarketing/segments.json?fields=id,name";
        MediaType mediaType = MediaType.parse("application/json");
        String json = "{\"name\":\"" + name + "\",\"pass_condition\":\"" + pass_condition +
                "\",\"relations\":[{\"object_type\":\"" + object_type + "\",\"params\":{\"left\":"
                + left + ",\"right\":" + right + ",\"type\":\"" + seg_type + "\"}}]}";
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .header("access_token", access_token)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

    public Response postCreateSegment(String name, String pass_condition, String object_type) throws IOException {
        return postCreateSegment(name, pass_condition, object_type, 365, 0, "positive");
    }

    public Response postDeleteSegment(String segmentId, String sourceType) throws IOException {
        Allure.step("Create Segment: " + segmentId + "source:" + sourceType);
        String url = base_url + "/api/v1/remarketing/mass_action/delete.json";
        MediaType mediaType = MediaType.parse("application/json");
        String json = "[{\"source_id\":\"" + segmentId + "\",\"source_type\":\"" + sourceType + "\"}]";
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .header("access_token", access_token)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

    public Response postDeleteSegment(String segmentId) throws IOException {
        return postDeleteSegment(segmentId, "segment");
    }

    public Response getCheckSegment(String segmentId) throws IOException {
        Allure.step("Check Segment: " + segmentId);
        String location = "/api/v2/remarketing/segments/" + segmentId + ".json";
        String url = base_url + location;
        Request request = new Request.Builder()
                .url(url)
                .header("access_token", access_token)
                .build();
        return client.newCall(request).execute();
    }
}

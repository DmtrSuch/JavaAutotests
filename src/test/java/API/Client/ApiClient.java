package API.Client;

import io.qameta.allure.Allure;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.protocol.HttpClientContext;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiClient {

    private static final String location_CSRF = "csrf/";
    private static final String location_LOGIN = "https://auth-ac.my.com/auth?lang=ru&nosavelogin=0";
    private static final String location_CREATE_SEGMENT = "api/v2/remarketing/segments.json?fields=id,name";
    private static final String location_DELETE_SEGMENT = "api/v1/remarketing/mass_action/delete.json";
    private static final String location_UPLOAD_IMAGE = "api/v2/content/static.json";
    private static final String location_CREATE_CAMPAIGN = "api/v2/campaigns.json";

    private String base_url;
    private String csrf_token;
    private String user;
    private String password;
    private CloseableHttpClient httpClient;

    public ApiClient(String base_url, String user, String password) {
        this.base_url = base_url;
        this.user = user;
        this.password = password;
        this.httpClient = HttpClients.createDefault();
    }

    private void addCsrfTokenHeader(HttpRequestBase request) {
        if (csrf_token != null) {
            request.addHeader("X-CSRFToken", csrf_token);
        }
    }

    private String getResponseBody(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity);
        }
        return null;
    }

    public HttpResponse postLogin() throws IOException {
        HttpPost request = new HttpPost(location_LOGIN);
        addCsrfTokenHeader(request);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("email", user));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("continue", "https://target.my.com/auth/mycom?state=target_login%3D1%26ignore_opener%3D1#email"));
        request.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = httpClient.execute(request);
        csrf_token = getCsrfToken();
        return httpClient.execute(request);
    }

    private String getCsrfToken() throws IOException {
        HttpGet request = new HttpGet(base_url + location_CSRF);
        HttpClientContext context = HttpClientContext.create();
        HttpResponse response = httpClient.execute(request, context);

        String csrfToken = null;
        for (org.apache.http.cookie.Cookie cookie : context.getCookieStore().getCookies()) {
            if ("csrftoken".equals(cookie.getName())) {
                csrfToken = cookie.getValue();
                break;
            }
        }
        return csrfToken;
    }

    public HttpResponse postCreateSegment(String name, String pass_condition, String object_type, int left,
                                          int right, String seg_type) throws IOException {
        HttpPost request = new HttpPost(base_url + location_CREATE_SEGMENT);
        addCsrfTokenHeader(request);

        String json = "{\"name\":\"" + name + "\",\"pass_condition\":\"" + pass_condition +
                "\",\"relations\":[{\"object_type\":\"" + object_type + "\",\"params\":{\"left\":" + left +
                ",\"right\":" + right + ",\"type\":\"" + seg_type + "\"}}]}";
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        return httpClient.execute(request);
    }

    public HttpResponse postCreateSegment(String name, String pass_condition, String object_type) throws IOException {
        return postCreateSegment(name, pass_condition, object_type, 365, 9, "positive");
    }
    public HttpResponse postDeleteSegment(String segment_id, String source_type) throws IOException {
        HttpPost request = new HttpPost(base_url + location_DELETE_SEGMENT);
        addCsrfTokenHeader(request);

        String json = "[{\"source_id\":\"" + segment_id + "\",\"source_type\":\"" + source_type + "\"}]";
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        return httpClient.execute(request);
    }

    public HttpResponse postDeleteSegment(String segment_id) throws IOException {
        return postDeleteSegment(segment_id, "segment");
    }

    public HttpResponse getCheckSegment(String segment_id) throws IOException {
        HttpGet request = new HttpGet(base_url + "api/v2/remarketing/segments/" + segment_id + ".json");
        addCsrfTokenHeader(request);

        return httpClient.execute(request);
    }

    public HttpResponse postUploadImage(String file) throws IOException {
        HttpPost request = new HttpPost(base_url + location_UPLOAD_IMAGE);
        addCsrfTokenHeader(request);

        FileEntity fileEntity = new FileEntity(new File(file), ContentType.APPLICATION_OCTET_STREAM);
        request.setEntity(fileEntity);

        return httpClient.execute(request);
    }

    public HttpResponse getIdUrl(String target_url) throws IOException {
        HttpGet request = new HttpGet(base_url + "api/v1/urls/?url=" + target_url);
        addCsrfTokenHeader(request);

        return httpClient.execute(request);
    }

    public HttpResponse getCampaignStatus(String campaign_id) throws IOException {
        HttpGet request = new HttpGet(base_url + "api/v2/campaigns/" + campaign_id + ".json?fields=issues");
        addCsrfTokenHeader(request);

        return httpClient.execute(request);
    }

    public HttpResponse postCreateCampaign(String name, String image_id, String url_id, String objective,
                                           int package_id) throws IOException {
        HttpPost request = new HttpPost(base_url + location_CREATE_CAMPAIGN);
        addCsrfTokenHeader(request);

        String json = "{\"banners\":[{\"content\":{\"image_240x400\":{\"id\":\"" + image_id +
                "\"}},\"urls\":{\"primary\":{\"id\":\"" + url_id + "\"}}}],\"name\":\"" + name +
                "\",\"objective\":\"" + objective + "\",\"package_id\":" + package_id + "}";
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        return httpClient.execute(request);
    }

    public HttpResponse postCreateCampaign(String name, String image_id, String url_id) throws IOException {
        return postCreateCampaign(name, image_id, url_id, "reach", 960);
    }

    public HttpResponse deleteCampaign(String campaign_id) throws IOException {
        HttpDelete request = new HttpDelete(base_url + "api/v2/campaigns/" + campaign_id + ".json");
        addCsrfTokenHeader(request);

        return httpClient.execute(request);
    }
}

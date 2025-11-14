package com.hatester.helpers;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JiraHelper {

    private static final String JIRA_URL = PropertiesHelper.getValue("JIRA_URL_ATLASS");
    private static final String EMAIL = PropertiesHelper.getValue("EMAIL");
    private static final String API_TOKEN = PropertiesHelper.getValue("API_TOKEN");

    public static void createJiraTicket(String summary, String description) {
        try {
            HttpClient client = org.apache.hc.client5.http.impl.classic.HttpClients.createDefault();
            HttpPost post = new HttpPost(JIRA_URL);

            String auth = EMAIL + ":" + API_TOKEN;
            String encodedAuth = Base64.getEncoder()
                    .encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            post.setHeader("Authorization", "Basic " + encodedAuth);
            post.setHeader("Content-Type", "application/json");

            JSONObject project = new JSONObject();
            project.put("key", "KAN"); // thay bằng key project của bạn

            JSONObject issueType = new JSONObject();
            issueType.put("name", "Task");

            // 🔹 Description theo Atlassian Document Format (ADF)
            JSONObject descriptionADF = new JSONObject()
                    .put("type", "doc")
                    .put("version", 1)
                    .put("content", new JSONArray()
                            .put(new JSONObject()
                                    .put("type", "paragraph")
                                    .put("content", new JSONArray()
                                            .put(new JSONObject()
                                                    .put("type", "text")
                                                    .put("text", description)
                                            )
                                    )
                            )
                    );

            JSONObject fields = new JSONObject();
            fields.put("project", project);
            fields.put("summary", summary);
            fields.put("issuetype", issueType);
            fields.put("description", descriptionADF);

            JSONObject main = new JSONObject();
            main.put("fields", fields);

            post.setEntity(new StringEntity(main.toString()));

            HttpClientResponseHandler<String> responseHandler = (ClassicHttpResponse response) ->
                    EntityUtils.toString(response.getEntity());

            String responseBody = client.execute(post, responseHandler);
            System.out.println("Jira response: " + responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

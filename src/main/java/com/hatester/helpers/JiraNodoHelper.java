package com.hatester.helpers;

import com.hatester.drivers.DriverManager;
import com.hatester.utils.LogUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class JiraNodoHelper {

    private static final String JIRA_URL = PropertiesHelper.getValue("JIRA_URL");
    private static final String JIRA_USERNAME = PropertiesHelper.getValue("JIRA_USERNAME");   // username Jira của bạn
    private static final String JIRA_PASSWORD = PropertiesHelper.getValue("JIRA_PASSWORD");   // mật khẩu Jira của bạn
    private static final String JIRA_TOKEN = PropertiesHelper.getValue("JIRA_TOKEN");

    public static void createJiraTicket(String testName, String[] testSteps) {
        try {
            //Lưu screenshot
            String screenshotPath = SystemHelper.getCurrentDir() + "/reports/screenshots/";
            new File(screenshotPath).mkdirs(); // tạo folder nếu chưa có

            String screenshotFileName = testName + ".png";
            File screenshotFile = new File(screenshotPath + screenshotFileName);
            byte[] imageBytes = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            Files.write(Paths.get(screenshotFile.getPath()), imageBytes);

            //Chuẩn bị description
            StringBuilder descriptionBuilder = new StringBuilder();
            String actualResult = TestStepHelper.getActualResult();
            descriptionBuilder.append("*Tên TCs:* ").append(testName).append("\n\n");
            descriptionBuilder.append("*Các bước thực hiện:*\n");
            for (int i = 0; i < testSteps.length; i++) {
                descriptionBuilder.append(i + 1).append(". ").append(testSteps[i]).append("\n");
            }

            // Thêm Actual Result nếu có
            if (actualResult != null && !actualResult.isEmpty()) {
                descriptionBuilder.append("\n*Kết quả thực tế:*\n").append(actualResult);
            }

            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(JIRA_URL);

            String auth = JIRA_USERNAME + ":" + JIRA_PASSWORD;
            String encodedAuth = Base64.getEncoder()
                    .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

//            post.setHeader("Authorization", AUTH_HEADER);
            post.setHeader("Authorization", "Bearer " + JIRA_TOKEN);
            post.setHeader("Content-Type", "application/json");
//            post.setHeader("Accept", "application/json");

            // Fields Jira
            JSONObject project = new JSONObject().put("key", PropertiesHelper.getValue("JIRA_PROJECT"));
            JSONObject issueType = new JSONObject().put("name", PropertiesHelper.getValue("JIRA_ISSUE"));
            JSONObject priority = new JSONObject().put("name", PropertiesHelper.getValue("JIRA_PRIORITY"));
            JSONObject assignee = new JSONObject().put("name", PropertiesHelper.getValue("JIRA_ASSIGNEE"));

            JSONObject fields = new JSONObject();
            fields.put("project", project);
            fields.put("summary", "[BugAutomation" + SystemHelper.getCurrentDatetime() + "] " + actualResult
                    .replaceAll("expected \\[.*?\\] but found \\[.*?\\]", "") // Xóa phần "expected [...] but found [...]"
                    .replaceAll("expected: <.*?> but was: <.*?>", "")  // Xóa phần "expected: <...> but was: <...>"
                    .replaceAll("AssertionError[:\\s]*", "")  // Xóa từ "AssertionError:" nếu có
                    .replaceAll("\\[|\\]", "")  // Xóa dấu ngoặc vuông, thừa khoảng trắng
                    .trim()
            );
            fields.put("description", descriptionBuilder.toString());
            fields.put("issuetype", issueType);
            fields.put("assignee", assignee);
            fields.put("priority", priority);

            JSONObject main = new JSONObject().put("fields", fields);
            post.setEntity(new StringEntity(main.toString(), StandardCharsets.UTF_8));

            // Gửi request tạo issue
            HttpClientResponseHandler<String> responseHandler = (ClassicHttpResponse response) ->
                    EntityUtils.toString(response.getEntity());
            String responseBody = client.execute(post, responseHandler);

            LogUtils.info("Jira create issue response: " + responseBody);

            // Parse key issue từ response
            JSONObject res = new JSONObject(responseBody);
            if (res.has("key")) {
                String issueKey = res.getString("key");
                LogUtils.info("Created Jira issue: " + issueKey);

                //Upload screenshot
                uploadScreenshot(issueKey, screenshotFile);
            } else {
                LogUtils.error("Cannot create issue, response: " + responseBody);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload screenshot file vào ticket Jira
     */
    private static void uploadScreenshot(String issueKey, File screenshotFile) {
        try {
            if (!screenshotFile.exists()) {
                LogUtils.error("Screenshot file not found: " + screenshotFile.getAbsolutePath());
                return;
            }

            String uploadUrl = JIRA_URL + "/" + issueKey + "/attachments";
            HttpUriRequestBase uploadPost = new HttpPost(uploadUrl);

            String auth = JIRA_USERNAME + ":" + JIRA_PASSWORD;
            String encodedAuth = Base64.getEncoder()
                    .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

            uploadPost.setHeader("Authorization", "Bearer " + JIRA_TOKEN);
            uploadPost.setHeader("X-Atlassian-Token", "no-check");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("file", new FileBody(screenshotFile));
            uploadPost.setEntity(builder.build());

            HttpClient client = HttpClients.createDefault();
            HttpClientResponseHandler<String> handler = (ClassicHttpResponse response) ->
                    EntityUtils.toString(response.getEntity());
            String uploadResponse = client.execute(uploadPost, handler);
            LogUtils.info("Upload screenshot response: " + uploadResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

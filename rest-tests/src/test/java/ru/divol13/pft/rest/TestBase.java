package ru.divol13.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import org.testng.SkipException;
import java.util.Set;


public class TestBase {
    private final String bugifyIssues = "https://bugify.stqa.ru/api/issues.json";
    private final String bugifyIssue = "https://bugify.stqa.ru/api/issues/%s.json";
    private final String bugifyUser = "288f44776e7bec4bf44fdfeb1e646490";
    private final String bugifyPassword = "";

    protected void connect(){
        RestAssured.authentication = RestAssured.basic(bugifyUser, bugifyPassword);
    }

    protected Set<Issue> getIssues() {
        String json = RestAssured.get(bugifyIssues).asString();
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() { }.getType());
    }

    protected int createIssue(Issue newIssue) {
        String json = RestAssured.given()
                .param("subject", newIssue.getSubject())
                .param("description", newIssue.getDescription())
                .post(bugifyIssues).asString();
        JsonElement parsed = JsonParser.parseString(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

    protected Issue getIssueById(int issueId){
        String issueAddress = String.format(bugifyIssue, issueId);
        String json = RestAssured.get(issueAddress).asString();
        JsonElement parsed = JsonParser.parseString(json);

        JsonElement jsonIssue = parsed.getAsJsonObject().get("issues");
        Set<Issue> issues = new Gson().fromJson(jsonIssue, new TypeToken<Set<Issue>>() { }.getType());

        Issue issue = issues.iterator().next();

        return issue;
    }

    public boolean isIssueOpen(int issueId) {
        Issue issue = getIssueById(issueId);

        if (issue.getState_name().equals("Resolved")) {
            return false;
        }
        return true;
    }

    public void skipIfNotFixed(int issueId) {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

}

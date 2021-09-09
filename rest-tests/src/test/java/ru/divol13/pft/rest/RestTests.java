package ru.divol13.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase{

    @BeforeMethod
    public void init() {
        connect();
    }

    @Test
    public void testCreateIssue() {
        Set<Issue> oldIssues = getIssues();

        Issue newIssue = new Issue().
                withSubject("My own new test1").
                withDescription("My test has description1");
        int issueId = createIssue(newIssue);

        Set<Issue> newIssues = getIssues();
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }

    @Test
    public void testGetIssue(){
        getIssueById(1396);
    }

    @Test
    public void testMantisSkipIssue() {
        try {
            skipIfNotFixed(1396);
            System.out.println("processed");
        } catch (SkipException ex) {
            System.out.println("skipped");
        }
    }
}

package ru.divol13.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.divol13.pft.mantis.model.Issue;
import ru.divol13.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SoapHelper {
    private ApplicationManager app;

    public SoapHelper(ApplicationManager app) {
        this.app = app;
    }

    public MantisConnectPortType getMantisConnect() throws MalformedURLException, ServiceException {
        return new MantisConnectLocator().getMantisConnectPort(
                new URL("http://localhost/mantisbt-2.25.2/api/soap/mantisconnect.php")
        );
    }

    public Set<Project> getProjects() throws MalformedURLException, RemoteException, ServiceException {
        MantisConnectPortType mc = getMantisConnect();
        ProjectData[] projects = mc.mc_projects_get_user_accessible(
                app.getProperty("web.adminLogin"),
                app.getProperty("web.adminPassword")
        );

        return Arrays.asList(projects).stream().map(
                (p) -> new Project().withId(p.getId().intValue()).withName(p.getName())
        ).collect(Collectors.toSet());
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        String[] categories = mc.mc_project_get_categories(
                app.getProperty("web.adminLogin"),
                app.getProperty("web.adminPassword"),
                BigInteger.valueOf(issue.getProject().getId()));

        IssueData newIssue = new IssueData();
        newIssue.setSummary(issue.getSummary());
        newIssue.setDescription(issue.getDescription());
        newIssue.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        newIssue.setCategory(categories[0]);

        BigInteger issueId = mc.mc_issue_add(
                app.getProperty("web.adminLogin"),
                app.getProperty("web.adminPassword"),
                newIssue);

        IssueData newIssueData = mc.mc_issue_get(
                app.getProperty("web.adminLogin"),
                app.getProperty("web.adminPassword"),
                issueId);

        return new Issue().
                withId(newIssueData.getId().intValue()).
                withSummary(newIssueData.getSummary()).
                withDescription(newIssueData.getDescription()).
                withProject(
                        new Project().withId( newIssueData.getProject().getId().intValue()).withName(newIssueData.getProject().getName())
                );
    }

    public IssueData getIssueById(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        return mc.mc_issue_get(
                app.getProperty("web.adminLogin"),
                app.getProperty("web.adminPassword"),
                BigInteger.valueOf(issueId));
    }
}

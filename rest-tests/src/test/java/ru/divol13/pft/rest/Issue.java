package ru.divol13.pft.rest;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Issue {
    @Expose
    private int id;
    @Expose
    private String subject;
    @Expose
    private String description;
    @Expose
    private String state_name;

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public int getId() {
        return id;
    }

    public Issue withId(int id) {
        this.id = id;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Issue withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Issue withDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return id == issue.id && Objects.equals(subject, issue.subject) && Objects.equals(description, issue.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, description);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", state_name='" + state_name + '\'' +
                '}';
    }
}

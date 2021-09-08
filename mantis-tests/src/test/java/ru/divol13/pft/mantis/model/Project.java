package ru.divol13.pft.mantis.model;

public class Project {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public Project withId(int id) {
        this.id = id;
        return this;
    }

    public Project withName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }
}

package ru.divol13.pft.addressbook.model;

public class ContactData {
    private final String firstname;
    private final String middlename;
    private final String lastname;
    private final String address;
    private final String home;
    private final String mobile;
    private final String group;

    public ContactData(String firstname, String middlename, String lastname, String address, String home, String mobile, String group) {
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.address = address;
        this.home = home;
        this.mobile = mobile;
        this.group = group;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getHome() {
        return home;
    }

    public String getMobile() {
        return mobile;
    }

    public String getGroup() {
        return group;
    }
}

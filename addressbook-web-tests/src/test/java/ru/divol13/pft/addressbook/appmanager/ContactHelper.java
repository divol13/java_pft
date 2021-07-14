package ru.divol13.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.divol13.pft.addressbook.model.ContactData;
import ru.divol13.pft.addressbook.tests.HelperBase;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHome());
        type(By.name("mobile"), contactData.getMobile());
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void returnToContractPage() {
        click(By.linkText("home page"));
    }
}

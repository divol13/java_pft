package ru.divol13.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.divol13.pft.addressbook.model.ContactData;
import ru.divol13.pft.addressbook.tests.HelperBase;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHome());
        type(By.name("mobile"), contactData.getMobile());

        if(creation){
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }

        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void returnToContactPage() {
        click(By.linkText("home page"));
    }

    public void initContactModification(int index) {
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    }

    public void submitContactModification() {
        click(By.xpath("//div[@id='content']/form/input[22]"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void confirmDeleteContact() {
        wd.switchTo().alert().accept();
        wd.findElement(By.cssSelector("div.msgbox"));
    }

    public void createContact(ContactData contact, boolean creation) {
        initContactCreation();
        fillContactForm(contact, creation);
        returnToContactPage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements ) {
            String lastName, firstName;

            List<WebElement> allInnerTd = element.findElements(By.tagName("td"));
            firstName = allInnerTd.get(2).getText();
            lastName = allInnerTd.get(1).getText();

            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));

            ContactData contact = new ContactData(id, firstName, null, lastName, null, null, null, null);
            contacts.add(contact);
        }
        return contacts;
    }
}

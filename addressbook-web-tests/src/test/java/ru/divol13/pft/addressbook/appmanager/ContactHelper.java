package ru.divol13.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.divol13.pft.addressbook.model.ContactData;
import ru.divol13.pft.addressbook.model.Contacts;
import ru.divol13.pft.addressbook.tests.HelperBase;
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

    public void create(ContactData contact, boolean creation) {
        initContactCreation();
        fillContactForm(contact, creation);
        returnToContactPage();
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            String lastName, firstName;

            List<WebElement> allInnerTd = element.findElements(By.tagName("td"));
            firstName = allInnerTd.get(2).getText();
            lastName = allInnerTd.get(1).getText();

            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));

            ContactData contact = new ContactData().withId(id).withFirstname(firstName).withLastname(lastName);
            contacts.add(contact);
        }
        return contacts;
    }

    public void delete(int index) throws InterruptedException {
        selectContact(index);
        deleteSelectedContact();
        confirmDeleteContact();
        Thread.sleep(500);
    }

    public void modify(int index, ContactData contact) {
        initContactModification(index);
        fillContactForm(contact, false);
        clickUpdateContact();
        returnToContactPage();
    }

    public void clickUpdateContact() {
        click(By.name("update"));
    }

    public void selectContactById(int id) {
        String selector = "input[value='" + id + "']";
        wd.findElement(By.cssSelector(selector)).click();
    }

    public void delete(ContactData contact) throws InterruptedException {
        selectContactById(contact.getId());
        deleteSelectedContact();
        confirmDeleteContact();
        Thread.sleep(500);
    }

    public void modify(ContactData contact) {
        modificationContact(contact.getId());
        fillContactForm(contact, false);
        clickUpdateContact();
        returnToContactPage();
    }

    public void modificationContact(int id) {
        String selector = "a[href='edit.php?id=" + id + "']";
        wd.findElement(By.cssSelector(selector)).click();
    }

    public void gotoHomePage() {
        if (isElementPresent(By.id("maintable"))) {
            return;
        }
        click(By.linkText("home"));
    }
}

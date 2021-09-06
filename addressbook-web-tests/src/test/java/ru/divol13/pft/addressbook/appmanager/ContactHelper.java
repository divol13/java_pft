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
        // fill ФИО
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());

        // fill address
        type(By.name("address"), contactData.getAddress());

        //fill phones
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());

        // fill emails
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());

        if (creation) {
            if (contactData.getGroups().size() > 0){
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).
                        selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
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
        contactCache = null;
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }

        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            String lastName, firstName;

            List<WebElement> allInnerTd = element.findElements(By.tagName("td"));
            firstName = allInnerTd.get(2).getText();
            lastName = allInnerTd.get(1).getText();
            String allAddresses = allInnerTd.get(3).getText();
            String allEmails = allInnerTd.get(4).getText();
            String allPhones = allInnerTd.get(5).getText();

            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));

            ContactData contact = new ContactData().
                withId(id).
                withFirstname(firstName).
                withLastname(lastName).
                withAllPhones(allPhones).
                withAddress(allAddresses).
                withAllEmails(allEmails);

            contactCache.add(contact);
        }
        return new Contacts(contactCache);
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
        contactCache = null;
    }

    public void modify(ContactData contact) {
        modificationContact(contact.getId());
        fillContactForm(contact, false);
        clickUpdateContact();
        contactCache = null;
        returnToContactPage();
    }

    public void modificationContact(int id) {
        //wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']",id))).click();
    }

    public void gotoHomePage() {
        if (isElementPresent(By.id("maintable"))) {
            return;
        }
        click(By.linkText("home"));
    }

    public ContactData infoFromEditForm(ContactData contact) {
        modificationContact(contact.getId());

        // user
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");

        // phones
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");

        // e-mails
        String allAddresses = wd.findElement(By.name("address")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");

        wd.navigate().back();
        return
                new ContactData().
                        withId(contact.getId()).
                        withFirstname(firstname).
                        withLastname(lastname).
                        withHomePhone(home).
                        withMobilePhone(mobile).
                        withWorkPhone(work).
                        withAddress(allAddresses).
                        withEmail(email).
                        withEmail2(email2).
                        withEmail3(email3);
    }

}

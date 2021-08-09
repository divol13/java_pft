package ru.divol13.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.ContactData;
import ru.divol13.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test
    public void testModificationContact(){
        if(!app.getContactHelper().isThereAContact()){
            ContactData newContact = new ContactData("Stepan", "Ostapovich", "Balakirev", "Lobnya", "no", "903*******", "test7");
            app.getContactHelper().createContact(newContact, true);
        }
        List<ContactData> before = app.getContactHelper().getContactList();

        int contactForModify = before.size() - 1;

        //app.getContactHelper().selectContact(contactForModify);
        app.getContactHelper().initContactModification(contactForModify);

        ContactData contact = new ContactData(before.get(contactForModify).getId(),"Ivan", "Ivanich", "Ivanov", "Moscow", "no", "916*******", null);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToContactPage();

        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(before.size(), after.size());

        // проверка что модифицировалася
        before.remove(contactForModify);
        before.add(contact);
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
    }
}

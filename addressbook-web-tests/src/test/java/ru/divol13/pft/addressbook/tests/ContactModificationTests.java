package ru.divol13.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testModificationContact(){
        if(!app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("Stepan", "Ostapovich", "Balakirev", "Lobnya", "no", "903*******", "test7"), true);
        }

        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Ivan", "Ivanich", "Ivanov", "Moscow", "no", "916*******", null), false);
        app.getContactHelper().submitContactModification();
    }
}

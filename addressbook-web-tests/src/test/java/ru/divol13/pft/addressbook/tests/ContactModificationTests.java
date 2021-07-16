package ru.divol13.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testModificationContact(){
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Ivan", "Ivanich", "Ivanov", "Moscow", "no", "916*******"));
        app.getContactHelper().submitContactModification();
    }
}

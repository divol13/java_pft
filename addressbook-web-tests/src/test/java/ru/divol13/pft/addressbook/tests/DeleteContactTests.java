package ru.divol13.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.ContactData;

public class DeleteContactTests extends TestBase{

    @Test
    public void testDeleteContact() {
        if(!app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("Stepan", "Ostapovich", "Balakirev", "Lobnya", "no", "903*******", "test7"), true);
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().confirmDeleteContact();
    }
}

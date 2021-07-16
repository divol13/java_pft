package ru.divol13.pft.addressbook.tests;

import org.testng.annotations.Test;

public class DeleteContactTests extends TestBase{

    @Test
    public void testDeleteContact() {
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().confirmDeleteContact();
    }
}

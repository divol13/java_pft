package ru.divol13.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.ContactData;

import java.util.List;

public class DeleteContactTests extends TestBase{

    @Test(enabled = false)
    public void testDeleteContact() throws InterruptedException {
        if(!app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("Stepan", "Ostapovich", "Balakirev", "Lobnya", "no", "903*******", "test7"), true);
        }
        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().confirmDeleteContact();

        // тут надо ждать появления списка снова... пока решил это увеличив имплиситное ожидание
        Thread.sleep(1000);
        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(before.size() - 1, after.size());

        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }
}

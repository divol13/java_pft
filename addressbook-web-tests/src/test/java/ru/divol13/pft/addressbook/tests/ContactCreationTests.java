package ru.divol13.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.divol13.pft.addressbook.model.ContactData;
import ru.divol13.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();

    app.getContactHelper().initContactCreation();
    ContactData contact = new ContactData("Dmitry", "Sergeevich", "Volokitin", "Moscow", "no", "916*******", "test7");
    app.getContactHelper().fillContactForm(contact, true );
    app.getContactHelper().returnToContactPage();
    List<ContactData> after = app.getContactHelper().getContactList();

    Assert.assertEquals(before.size() + 1, after.size());

    contact.setId(
            after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId()
    );

    before.add(contact);
    Assert.assertEquals(new HashSet<>(before),new HashSet<>(after));
  }

}

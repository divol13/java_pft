package ru.divol13.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.divol13.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("Dmitry", "Sergeevich", "Volokitin", "Moscow", "no", "916*******"));
    app.getContactHelper().returnToContactPage();
  }

}

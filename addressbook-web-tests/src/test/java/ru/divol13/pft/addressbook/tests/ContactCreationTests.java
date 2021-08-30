package ru.divol13.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.divol13.pft.addressbook.model.ContactData;
import ru.divol13.pft.addressbook.model.Contacts;
import ru.divol13.pft.addressbook.model.GroupData;

import java.util.Comparator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.contact().gotoHomePage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData().
            withFirstname("Dmitry").
            withMiddlename("Sergeevich").
            withLastname("Volokitin").
            withAddress("Moscow").
            withHome("no").
            withMobile("916*******").
            withGroup("test7");
    app.contact().create(contact, true);

    assertThat(app.contact().all().size(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after,
            equalTo( before.withAdded(
                    contact.withId(after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId())
            ))
    );
  }

}

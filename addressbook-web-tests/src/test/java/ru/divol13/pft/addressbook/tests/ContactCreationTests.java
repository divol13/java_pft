package ru.divol13.pft.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.testng.annotations.*;
import ru.divol13.pft.addressbook.model.ContactData;
import ru.divol13.pft.addressbook.model.Contacts;
import ru.divol13.pft.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try(BufferedReader reader = new BufferedReader(
            new FileReader(
                    new File("src/test/resources/contacts.json")
            )
    )) {

      String json = "";
      String line = reader.readLine();

      while (line != null){
        json += line;
        line = reader.readLine();
      }

      Gson gson = new Gson();
      List<ContactData> groups = gson. fromJson(json, new TypeToken<List<ContactData>>() {}.getType());
      return groups.stream().
              map((g) -> new Object[] {g}).
              collect(Collectors.toList()).
              iterator();
      }
  }

  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) {
    app.contact().gotoHomePage();
    Contacts before = app.db().contacts();

    app.contact().create(contact, true);

    assertThat(app.db().contacts().size(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    assertThat(after,
            equalTo( before.withAdded(
                    contact.withId(after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId())
            ))
    );

    verifyContactListInUI();
  }

}

package ru.divol13.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.ContactData;
import ru.divol13.pft.addressbook.model.Contacts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0 ) {
            ContactData contact = new ContactData().
                    withFirstname("Stepan").
                    withMiddlename("Ostapovich").
                    withLastname("Balakirev").
                    withAddress("Lobnya").
                    withHomePhone("1564").
                    withMobilePhone("903-905-64-82").
                    withGroup("test7");

            app.contact().create(contact, true);
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().
                withId(modifiedContact.getId()).
                withFirstname("Ivan").
                withMiddlename("Ivanovich").
                withLastname("Ivanov").
                withAddress("Moscow").
                withHomePhone("321-56").
                withMobilePhone("916-159-95-19");
        app.contact().modify(contact);
        assertThat(app.contact().all().size(), equalTo(before.size()));
        Contacts after = app.contact().all();
        assertThat(after, equalTo( before.without(modifiedContact).withAdded(contact)));
    }

}

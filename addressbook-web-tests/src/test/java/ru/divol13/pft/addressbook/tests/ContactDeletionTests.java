package ru.divol13.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.ContactData;
import ru.divol13.pft.addressbook.model.Contacts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactDeletionTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0 ) {
            ContactData contact = new ContactData().
                    withFirstname("Stepan").
                    withMiddlename("Ostapovich").
                    withLastname("Balakirev").
                    withAddress("Lobnya").
                    withHomePhone("123").
                    withMobilePhone("903-123-59-67").
                    withGroup("test7");

            app.contact().create(contact, true);
        }
    }

    @Test
    public void testContactDeletion() throws InterruptedException {
        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        assertThat(app.contact().all().size(), equalTo(before.size()-1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.without(deletedContact)));
    }

}

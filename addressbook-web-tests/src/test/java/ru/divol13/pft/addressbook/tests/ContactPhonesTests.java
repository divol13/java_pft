package ru.divol13.pft.addressbook.tests;

import ru.divol13.pft.addressbook.model.ContactData;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhonesTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.contact().gotoHomePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(
                new ContactData().
                    withFirstname("Dracula").
                    withLastname("Vampirovich").
                    withHomePhone("111").
                    withMobilePhone("(495)-123-78-45").
                    withWorkPhone("8-916-456-78-12").
                    withGroup("test1"), true
            );
            app.contact().gotoHomePage();
        }
    }

    @Test
    public void testContactPhones(){
        app.contact().gotoHomePage();
        ContactData contact = app.contact().all().iterator().next();
        System.out.println(contact);
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        System.out.println(contactInfoFromEditForm);
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));

    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(),contact.getMobilePhone(),contact.getWorkPhone())
                .stream().filter((s -> !s.equals("")))
                .map(ContactPhonesTests::cleaner)
                .collect(Collectors.joining("\n"));

    }

    public static String cleaner(String phone){
        return phone.replaceAll("\\s","").replaceAll("[-()]","");
    }
}

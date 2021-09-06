package ru.divol13.pft.addressbook.tests;

import ru.divol13.pft.addressbook.model.ContactData;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressesPhonesEmailsTests extends TestBase{
    @BeforeMethod
    public void ensurePreconditions() {
        app.contact().gotoHomePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(
                new ContactData().
                    withFirstname("Иван").
                    withMiddlename("Модестович").
                    withLastname("Рабинович").
                    withAddress("г.Долгопрудный, улица Сезам, дом 9").
                    withEmail("imr@mail.ru").
                    withEmail2("ivan.rab@mail.ru").
                    withEmail3("rab_ivan_mod@gmail.com").
                    withGroup("test1"),true
            );
            app.contact().gotoHomePage();
        }
    }

    @Test
    public void testContactEmails() {
        app.contact().gotoHomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
        verifyContactListInUI();
     }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s -> !s.equals("")))
                .map(ContactAddressesPhonesEmailsTests::cleaner)
                .collect(Collectors.joining("\n"));

    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactPhonesTests::cleaner)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaner(String email) {
        return email.replaceAll("\\s", "");
    }
}

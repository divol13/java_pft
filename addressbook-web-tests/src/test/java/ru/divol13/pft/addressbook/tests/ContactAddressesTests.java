package ru.divol13.pft.addressbook.tests;

import ru.divol13.pft.addressbook.model.ContactData;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressesTests extends TestBase{
    @BeforeMethod
    public void ensurePreconditions() {
        app.contact().gotoHomePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(
                    new ContactData().
                            withFirstname("Dracula").
                            withLastname("Vampirovich").
                            withAddress("г.Долгопрудный, улица Сезам, дом 9").
                            withHomePhone("495-59-61").
                            withMobilePhone("8(916)268426").
                            withGroup("test7"), true
            );
            app.contact().gotoHomePage();
        }
    }

    @Test
    public void testContactAddress(){
        app.contact().gotoHomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(cleaner(contact.getAddress()), equalTo(cleaner(contactInfoFromEditForm.getAddress())));
    }

    public static String cleaner(String address){
        return address.replaceAll("\\s","").replaceAll("[-()]","");
    }
}


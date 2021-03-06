package ru.divol13.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.ContactData;
import ru.divol13.pft.addressbook.model.Contacts;
import ru.divol13.pft.addressbook.model.GroupData;
import ru.divol13.pft.addressbook.model.Groups;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactAddToGroup extends TestBase {
    @BeforeTest
    private void ensurePreconditions() {
        if(app.db().groups().size() == 0){
            createNewGroup();
        }

        if(app.db().contacts().size() == 0){
            createNewContact();
        }
    }

    private ContactData createNewContact() {
        long now = System.currentTimeMillis();
        String userName = String.format("my_user_%s", now);

        app.contact().gotoHomePage();
        app.contact().create(
                new ContactData().
                        withFirstname(userName).
                        withLastname("Меерхольд").
                        withMiddlename("Алибабаевич"),true
        );

        return app.db().contacts().stream().filter(c -> c.getFirstname().equals(userName)).findFirst().get();
    }

    private GroupData createNewGroup() {
        long now = System.currentTimeMillis();
        String groupName = String.format("my_group_%s", now);

        app.goTo().groupPage();
        app.group().create(
                new GroupData().
                        withName(groupName).
                        withHeader("my_group_header").
                        withFooter("my_group_footer")
        );

        return app.db().groups().stream().filter(g -> g.getName().equals(groupName)).findFirst().get();
    }

    @Test
    public void testAddContactToGroup(){
        // берем любой контакт не привязанный к группе
        ContactData contact = app.db().contacts().stream().filter( c -> c.getGroups().size() == 0 ).findAny().orElse(null);

        // если контакт не нашли
        if(contact == null){

            // то создаем сами
            contact = createNewContact();
        }

        // берем любую группу, в которой нет контактов
        GroupData group = app.db().groups().stream().filter(g->g.getContacts().size() == 0).findAny().orElse(null);

        // если группу не нашли
        if(group == null){

            // то создаем сами
            group = createNewGroup();
        }

        // запоминаем в какую группу контакт был включен
        Groups before = new Groups(contact.getGroups());
        int contactId = contact.getId();

        // добавляем контакт в группу
        app.contact().addContactToGroup(contact,group);

        // checks
        Groups after = new Groups(app.db().contacts().stream().filter(c -> c.getId() == contactId).findFirst().get().getGroups());
        assertThat(after, equalTo(before.withAdded(group)));
    }
}


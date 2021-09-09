package ru.divol13.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.ContactData;
import ru.divol13.pft.addressbook.model.Contacts;
import ru.divol13.pft.addressbook.model.GroupData;
import ru.divol13.pft.addressbook.model.Groups;

import javax.transaction.Transactional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

    /*
        Задание №16: Реализовать тесты для добавления контакта в группу и удаления контакта из группы
        Реализовать тесты
         + для добавления контакта в группу и
         + удаления контакта из группы.

        Все действия выполнять через пользовательский интерфейс,
        а при проверках использовать информацию, загружаемую из базы данных напрямую.

        Не забудьте также реализовать проверку и обеспечение предусловий.

     */

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

    private void createNewContact() {
        app.contact().gotoHomePage();
        app.contact().create(
            new ContactData().
                    withFirstname("Василий").
                    withLastname("Меерхольд").
                    withMiddlename("Алибабаевич"),true
        );
    }

    private void createNewGroup() {
        app.goTo().groupPage();
        app.group().create(
            new GroupData().
                withName("my_group").
                withHeader("my_group_header").
                withFooter("my_group_footer")
        );
    }

    @Transactional
    @Test
    public void testDeleteContactFromGroup(){
        ContactData contact = app.db().contacts().stream().findAny().get();
        app.contact().gotoHomePage();

        Groups before = new Groups(contact.getGroups());
        int contactId = contact.getId();

        GroupData group = contact.getGroups().stream().findAny().get();
        app.contact().selectGroupFilterById(group.getId());
        app.contact().selectContactById(contact.getId());
        app.contact().submitDeleteFromGroup();

        app.contact().gotoHomePage();

        // checks
        Groups after = new Groups(app.db().contacts().stream().filter(c -> c.getId() == contactId).findFirst().get().getGroups());
        assertThat(after, equalTo(before.without(group)));
    }

    @Test
    public void testAddContact(){
        ContactData contact = app.db().contacts().stream().findAny().get();
        GroupData group = app.db().groups().stream().findAny().get();

        app.contact().gotoHomePage();

        Groups before = new Groups(contact.getGroups());
        int contactId = contact.getId();

        // без этого не работает добавление в группу - возможно баг в системе
        app.contact().selectGroupFilterToNone();

        app.contact().selectContactById(contact.getId());
        app.contact().selectGroupById(group.getId());
        app.contact().submitAddContactToGroup();

        app.contact().gotoHomePage();

        // checks
        Groups after = new Groups(app.db().contacts().stream().filter(c -> c.getId() == contactId).findFirst().get().getGroups());
        assertThat(after, equalTo(before.withAdded(group)));
    }

}


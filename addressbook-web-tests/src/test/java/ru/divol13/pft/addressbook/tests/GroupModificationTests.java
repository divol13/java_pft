package ru.divol13.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.GroupData;
import ru.divol13.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
            app.group().create(group);
        }
    }

    @Test
    public void testGroupModification() {
        Groups before = app.group().all();
        GroupData modifiedGroup = before.iterator().next();

        GroupData group = new GroupData().
                withId(modifiedGroup.getId()).
                withName("test7").
                withHeader("test8").
                withFooter("test9");
        assertThat(app.group().all().size(), equalTo(before.size()));

        app.group().modify(group);
        Groups after = app.group().all();
        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    }

}

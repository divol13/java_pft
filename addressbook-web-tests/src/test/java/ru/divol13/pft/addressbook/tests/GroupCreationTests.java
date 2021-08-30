package ru.divol13.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.divol13.pft.addressbook.model.GroupData;
import ru.divol13.pft.addressbook.model.Groups;

import java.util.Comparator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();

    Groups before = app.group().all();
    GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");

    app.group().create(group);
    Groups after = app.group().all();

    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(
            before.withAdded( group.withId( after.stream().max(Comparator.comparingInt(GroupData::getId)).get().getId()) )
    ));
  }

}
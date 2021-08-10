package ru.divol13.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.divol13.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();

    List<GroupData> before = app.group().all();
    GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
    app.group().create(group);
    List<GroupData> after = app.group().all();

    Assert.assertEquals(before.size() + 1, after.size());

    group.withId(
            after.stream().max(Comparator.comparingInt(GroupData::getId)).get().getId()
    );

    before.add(group);
    Assert.assertEquals(new HashSet<>(before),new HashSet<>(after));

    /*
    before.add(group);
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
    */
  }

}
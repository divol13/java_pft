package ru.divol13.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {
  @BeforeTest
  private void ensurePreconditions() {
    app.goTo().groupPage();

    if(!app.group().isThereAGroup()){
      GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
      app.group().create(group);
    }
  }

  @Test
  public void testDeleteGroup() throws Exception {
    List<GroupData> before = app.group().all();

    int index = before.size() - 1;
    app.group().delete(index);

    List<GroupData> after = app.group().all();
    Assert.assertEquals(before.size() - 1, after.size());

    before.remove(index);
    Assert.assertEquals(before, after);
  }

}

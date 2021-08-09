package ru.divol13.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.GroupData;

import java.util.List;

public class DeleteGroupTests extends TestBase {
  @BeforeTest
  private void ensurePreconditions() {
    app.getNavigationHelper().gotoGroupPage();

    if(!app.getGroupHelper().isThereAGroup()){
      app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
    }
  }

  @Test
  public void testDeleteGroup() throws Exception {
    List<GroupData> before = app.getGroupHelper().getGroupList();

    app.getGroupHelper().selectGroup(before.size() - 1);
    app.getGroupHelper().deleteSelectedGroup();
    app.getGroupHelper().returnToGroupPage();

    List<GroupData> after = app.getGroupHelper().getGroupList();

    Assert.assertEquals(before.size() - 1, after.size());

    before.remove(before.size() - 1);
    Assert.assertEquals(before, after);
  }
}

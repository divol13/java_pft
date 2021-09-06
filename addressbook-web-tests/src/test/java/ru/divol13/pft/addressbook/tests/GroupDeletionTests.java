package ru.divol13.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.GroupData;
import ru.divol13.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GroupDeletionTests extends TestBase {
  @BeforeTest
  private void ensurePreconditions() {
    app.goTo().groupPage();

    if(app.db().groups().size() == 0){
      GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
      app.group().create(group);
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {
    Groups before = app.db().groups();
    GroupData deleteGroup = before.iterator().next();

    app.group().delete(deleteGroup);
    assertThat(app.group().count(), equalTo(before.size() - 1));

    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(deleteGroup)));
  }

}

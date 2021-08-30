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

    if(app.group().all().size() == 0){
      GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
      app.group().create(group);
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {
    Groups before = app.group().all();
    GroupData deleteGroup = before.iterator().next();

    app.group().delete(deleteGroup);
    assertThat(app.group().all().size(), equalTo(before.size() - 1));

    Groups after = app.group().all();
    assertThat(after, equalTo(before.without(deleteGroup)));
  }

}

package ru.divol13.pft.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ru.divol13.pft.addressbook.model.GroupData;
import ru.divol13.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  Logger logger = LoggerFactory.getLogger(GroupCreationTests.class);

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    try(BufferedReader reader = new BufferedReader(
            new FileReader(
                    new File("src/test/resources/groups.json")
            )
    )) {

      String json = "";
      String line = reader.readLine();

      while (line != null) {
        json += line;
        line = reader.readLine();
      }

      Gson gson = new Gson();
      List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
      }.getType());
      return groups.stream().
              map((g) -> new Object[]{g}).
              collect(Collectors.toList()).
              iterator();
    }
  }

  @Test(dataProvider = "validGroupsFromJson")
  public void testGroupCreation(GroupData group) throws Exception {
    app.goTo().groupPage();

    Groups before = app.group().all();
    //GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");

    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size() + 1));

    Groups after = app.group().all();
    assertThat(after, equalTo(
            before.withAdded( group.withId( after.stream().max(Comparator.comparingInt(GroupData::getId)).get().getId()) )
    ));
  }

  @Test (enabled = false)
  public void testBadGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all();

    GroupData group = new GroupData().withName("test5'");
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size()));

    Groups after = app.group().all();
    assertThat(after, equalTo (before));
  }

}
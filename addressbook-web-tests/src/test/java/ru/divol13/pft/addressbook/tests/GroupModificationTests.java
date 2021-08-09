package ru.divol13.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupModificationTests extends TestBase {

    @BeforeTest
    private void ensurePreconditions() {
        app.getNavigationHelper().gotoGroupPage();

        if(!app.getGroupHelper().isThereAGroup()){
            app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
        }
    }

    @Test
    public void testGroupModification(){
        List<GroupData> before = app.getGroupHelper().getGroupList();
        int index = before.size() - 1;
        GroupData group = new GroupData(before.get(index).getId(), "test7", "test8", "test9");

        app.getGroupHelper().modify(index, group);

        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(before.size(), after.size());

        before.remove(index);
        before.add(group);

        // new variant
        Comparator<? super GroupData> ById = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(ById);
        after.sort(ById);
        Assert.assertEquals(before, after);

        // old variant
        // Assert.assertEquals(new HashSet<>(before),new HashSet<>(after));
    }
}

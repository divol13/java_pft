package ru.divol13.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.divol13.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {

    @BeforeTest
    private void ensurePreconditions() {
        app.goTo().groupPage();

        if(app.group().all().size() == 0){
            GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
            app.group().create(group);
        }
    }

    @Test
    public void testGroupModification(){
        List<GroupData> before = app.group().all();
        int index = before.size() - 1;
        GroupData group = new GroupData().
                withId(before.get(index).getId()).
                withName("test7").
                withHeader("test8").
                withFooter("test9");

        app.group().modify(index, group);

        List<GroupData> after = app.group().all();
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

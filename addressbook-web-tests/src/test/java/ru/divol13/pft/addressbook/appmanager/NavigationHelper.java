package ru.divol13.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.divol13.pft.addressbook.tests.HelperBase;

public class NavigationHelper extends HelperBase {

    private WebDriver wd;

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void groupPage() {
        click(By.linkText("groups"));
    }
}

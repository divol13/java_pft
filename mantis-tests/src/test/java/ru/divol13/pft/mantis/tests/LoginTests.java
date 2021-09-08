package ru.divol13.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.divol13.pft.mantis.appmanager.HttpSession;

import java.io.IOException;
import static org.testng.Assert.assertTrue;

public class LoginTests extends TestBase {
    @Test
    public void testLogin() throws IOException {
        HttpSession session = app.newSession();
        assertTrue(session.login(
                app.getProperty("web.adminLogin"),
                app.getProperty("web.adminPassword")));
        assertTrue(session.isLoggedInAs( app.getProperty("web.adminLogin")));
    }
}

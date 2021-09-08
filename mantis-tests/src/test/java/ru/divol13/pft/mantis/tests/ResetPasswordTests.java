package ru.divol13.pft.mantis.tests;

import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.*;
import org.hibernate.SessionFactory;
import ru.divol13.pft.mantis.appmanager.HttpSession;
import ru.divol13.pft.mantis.model.MailMessage;
import ru.divol13.pft.mantis.model.UserData;
import ru.divol13.pft.mantis.model.Users;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ResetPasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testResetPassword() throws Exception {
        // Step 0 Подобрать пользователя
        Users users = app.db().users();
        UserData user = users.stream().findAny().get();

        String userName = user.getUsername();
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();
        String newPassword = "654321";

        // Step 1 Залогиниться под админом и сбросить пароль для userName
        app.users().loginWithUI(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        app.users().resetPasswordForUser(userName);

        // Step 2 Подтвердить изменение пароля
        app.users().confirmNewPassword(newPassword);

        // Step 3 Проверить что userName может зайти с новым паролем
        HttpSession httpSession = app.newSession();
        assertTrue(httpSession.login(userName, newPassword));
        assertTrue(httpSession.isLoggedInAs(userName));
    }

    @Test
    public void testHBConnectionUserData(){
        final Users users = app.db().users();

        for (UserData user : users) {
            System.out.println(user);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}

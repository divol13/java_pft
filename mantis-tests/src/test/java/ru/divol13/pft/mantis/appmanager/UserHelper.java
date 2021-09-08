package ru.divol13.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.divol13.pft.mantis.model.MailMessage;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class UserHelper extends HelperBase{
    public UserHelper(ApplicationManager app) {
        super(app);
    }

    private String findConfirmationLink(List<MailMessage> mailMessages) {
        MailMessage mailMessage = mailMessages.get(0);
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    public void loginWithUI(String username, String password) {
        // login page
        wd.get(app.getProperty("web.baseUrl"));
        type(By.id("username"), username);
        click(By.cssSelector("input[type='submit']"));

        // password page
        type(By.name("password"), password);
        click(By.cssSelector("input[type='submit']"));
    }

    public void openManageMenu(){
        click(By.xpath("//div[@id='sidebar']/ul/li[6]"));
    }

    public void resetPasswordForUser(String userName){
        openManageMenu();
        click(By.linkText("Manage Users"));
        click(By.linkText(String.format("%s",userName)));
        click(By.xpath("//input[@value='Reset Password']"));
    }

    public void confirmNewPassword(String newPassword) throws MessagingException, IOException {
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 15000);
        String confirmationLink = findConfirmationLink(mailMessages);
        wd.get(confirmationLink);
        type(By.name("password"), newPassword);
        type(By.name("password_confirm"), newPassword);
        click(By.xpath("//button[@type='submit']"));
    }
}

package Appl.WebSever;
import Manager.Control;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailManager {

    private static Session session;
    public static final String Action_EMAILVERIFICATION = "EMAILVERIFICATION", Action_PASSWORDVERIFICATION = "PASSWORDVERIFICATION", Action_PASSWORDRESET = "PASSWORDRESET", Action_PurchaseVerification = "PURCHASEVERIFICATION";

    public static void send(String to, String sub, String msg) {
        if(to != null && !to.equals("")) {
            new Thread(() -> {
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    message.setSubject(sub);
                    message.setText(msg + "\nSincerely,\nHyperSphereStudio\n\n\nDO NOT REPLY TO THIS EMAIL! THIS EMAIL WAS AUTOMATICALLY GENERATED!");
                    Transport.send(message);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    public static void initialize() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Control.serverSettings.emailUsername(), Control.serverSettings.emailPassword());
                    }
                });

        Control.urlManager.newLink("reset", "basegame");
        System.out.println("Reset Link:" + URLManager.getURL(URLManager.Type_Server, "basegame", "resetmanager", "reset"));
        JavaHTTPServer.start();
    }
}
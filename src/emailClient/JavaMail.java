package emailClient;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMail {

    public static void SendMail(String recievermail, String subject, String content) {

        final String username = "----------------"; //email
        final String password = "----------------"; //token password

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("--------------")); //email
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recievermail)
            );
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            System.out.println("Email sent");

        }catch(MessagingException e) {
            System.out.println("Email not send due to some errors.");
            e.printStackTrace();
        }
    }


}


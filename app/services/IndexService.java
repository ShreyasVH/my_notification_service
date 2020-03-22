package services;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import requests.AttachmentRequest;
import requests.MailRequest;

public class IndexService
{
    public boolean sendMail(MailRequest request)
    {
        boolean isSuccess = false;
        String to = request.getTo();

        String from = request.getFrom();

        String host = System.getenv("SMTP_SERVER_HOST");
        String port = System.getenv("SMTP_SERVER_PORT");


        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);

        Session session;
        boolean isAuthRequired = Boolean.parseBoolean(System.getenv("SMTP_AUTH_REQUIRED"));
        if(isAuthRequired)
        {
            properties.put("mail.smtp.auth", "true");

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    String user = System.getenv("SMTP_USER");
                    String password = System.getenv("SMTP_PASSWORD");
                    return new PasswordAuthentication(user, password);
                }
            };
            session = Session.getDefaultInstance(properties, auth);
        }
        else
        {
            session = Session.getDefaultInstance(properties);
        }

        try
        {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(request.getSubject());

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(request.getBody());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            for(AttachmentRequest attachment: request.getAttachments())
            {
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.setFileName(attachment.getFilename());
                messageBodyPart.setText(new String(Base64.getDecoder().decode(attachment.getContent())));
                multipart.addBodyPart(messageBodyPart);
            }

            message.setContent(multipart);

            Transport.send(message);
            isSuccess = true;
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
        }

        return isSuccess;
    }
}

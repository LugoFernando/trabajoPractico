package com.comic.servicios.implementacion;

import com.comic.servicios.EmailServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service("EmailServicio")
@Transactional
public abstract class EmailServicioImpl implements EmailServicio {

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Override
    public String enviarEmail(String emailDestino, String subject, String contenido) {
        String ok = "Email Sent Successfully";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true"); //TLS Port
        props.put("mail.smtp.port", "587"); //enable authentication
        //props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipients(
                    Message.RecipientType.TO,
                    emailDestino //destino
            );
            message.setSubject(subject);
            message.setText(contenido);
            System.out.println("sending...");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return(ok);
    }
}

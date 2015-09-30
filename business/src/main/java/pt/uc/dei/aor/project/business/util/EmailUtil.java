package pt.uc.dei.aor.project.business.util;

import java.nio.file.Path;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class EmailUtil {
	
	private static final Logger log = LoggerFactory.getLogger(EmailUtil.class);
	private static final String UPLOADS = System.getProperty("jboss.server.data.dir")+"/jobapplication/uploads/cv/";

	@Resource(mappedName="java:jboss/mail/Gmail")
	Session session;

	private static String from = "managrecruit@gmail.com";

	Email email = new SimpleEmail();
	
	
	@Asynchronous
	public void send(String to, String content, String subject) {
		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
 
			message.setFrom(new InternetAddress(from));
			message.setReplyTo(new Address[]{new InternetAddress("noreply@example.com")});
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
 			
			message.setText(content, "utf-8", "html");
       
	        Transport.send(message);
		} catch (MessagingException e) {
			log.error("email failed");
		}
        
	}
	
	@Asynchronous
	public void send(String to, String content, String subject, Path file) {
		try {
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
 
			message.setFrom(new InternetAddress(from));
			message.setReplyTo(new Address[]{new InternetAddress("noreply@example.com")});
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
 			
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(content);
			messageBodyPart.setHeader("Content-Type", "text/html");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			BodyPart attach = new MimeBodyPart();
			DataSource source = new FileDataSource(file.toAbsolutePath().toString());
			attach.setDataHandler(new DataHandler(source));
			attach.setFileName(file.getFileName().toString());
			multipart.addBodyPart(attach);
			
			message.setContent(multipart);
	        Transport.send(message);
		} catch (MessagingException e) {
			log.error("email failed");
		}
        
	}
	
}

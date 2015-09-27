package pt.uc.dei.aor.project.business.util;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
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
			message.setReplyTo(new Address[]{new InternetAddress("noreply@foo.bar.com")});
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
 
			message.setText(content, "utf-8", "html");
       
	        Transport.send(message);
		} catch (MessagingException e) {
			log.error("email failed");
		}
        
	}
	
	
	
	
	
//	@Asynchronous
//	public void send(String to, String subject, String content) {
//
//		log.info("Sending Email from " + from + " to " + to + " : " + subject);
//
//		try {
//
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(from));
//			message.setRecipients(Message.RecipientType.TO,
//					InternetAddress.parse(to));
//			
//			message.setSubject(subject);
//			message.setText(content);
//
//			Transport.send(message);
//
//			log.debug("Email was sent");
//
//		} catch (MessagingException e) {
//			log.error("Error while sending email : " + e.getMessage());
//		}
//	}
//
//	
//	@Asynchronous
//	public void send(String to, String subject, String content, String path, String filename) {
//
//		Message message = new MimeMessage(session);
//		Multipart multiPart = new MimeMultipart("alternative");
//
//		try {
//
//		    MimeBodyPart textPart = new MimeBodyPart();
//		    textPart.setText(content, "utf-8");
//
//		    MimeBodyPart htmlPart = new MimeBodyPart();
//		    htmlPart.setContent(content, "text/html; charset=utf-8");
//
//		    multiPart.addBodyPart(textPart);
//		    multiPart.addBodyPart(htmlPart);
//		    message.setContent(multiPart);
//
//		    if(from != null){
//		        message.setFrom(new InternetAddress(from));
//		    }else
//		        message.setFrom();
//
//		    if(to != null)
//		        message.setReplyTo(new InternetAddress[]{new InternetAddress(to)});
//		    else
//		        message.setReplyTo(new InternetAddress[]{new InternetAddress(from)});
//
//		    InternetAddress[] toAddresses = { new InternetAddress(to) };
//		    message.setRecipients(Message.RecipientType.TO, toAddresses);
//		    message.setSubject(subject);
//		    message.setSentDate(new Date());
//
//		    Transport.send(message);
//
//		} catch (MessagingException e) {
//		    e.printStackTrace();
//		    System.out.println("Error: "+e.getMessage());
//
//		} finally {     
//		    System.out.println("Email sent!");
//		}
//	}
	
}

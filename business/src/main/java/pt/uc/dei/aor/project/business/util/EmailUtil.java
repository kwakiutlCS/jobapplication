package pt.uc.dei.aor.project.business.util;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class EmailUtil {
	
	private static final Logger log = LoggerFactory.getLogger(EmailUtil.class);

	@Resource(mappedName="java:jboss/mail/Gmail")
	Session session;

	private static String from = "managrecruit@gmail.com";

	
	@Asynchronous
	public void send(String to, String subject, String content) {

		log.info("Sending Email from " + from + " to " + to + " : " + subject);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			
			message.setSubject(subject);
			message.setText(content);

			Transport.send(message);

			log.debug("Email was sent");

		} catch (MessagingException e) {
			log.error("Error while sending email : " + e.getMessage());
		}
	}

}

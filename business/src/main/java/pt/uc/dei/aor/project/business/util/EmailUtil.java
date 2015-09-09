package pt.uc.dei.aor.project.business.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtil {
	private static String hostname = "smtp.googlemail.com";
	private static int port = 587;
	private static String sendMail = "managrecruit@gmail.com";
	private static String password = "apjomaad";
	private static Email email;
	
	
	public static void send(String subject, String msg, String receiver) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						attemptSend(subject, msg, receiver);
						System.out.println("Email send sucess");
						break;
					}
					catch(Exception e) {
						System.out.println("Email send error. retrying...");
						System.out.println(e.getMessage());
					}
				}
			}
		}).start();
	}
	
	
	private static void attemptSend(String subject, String msg, String receiver) throws EmailException {
		email = new SimpleEmail();
		email.setSmtpPort(587);
		email.setDebug(true);
		email.setAuthenticator(new DefaultAuthenticator(sendMail, password));
		email.setHostName(hostname);
		
//		email.getMailSession().getProperties().put("mail.smtps.auth", "true");
//		email.getMailSession().getProperties().put("mail.debug", "true");
//		email.getMailSession().getProperties().put("mail.smtps.port", "587");
//		email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587");
//		email.getMailSession().getProperties().put("mail.smtps.socketFactory.class",   "javax.net.ssl.SSLSocketFactory");
//		email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
//		email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
		
//		email.setSmtpPort(port);
		email = email.setSSLOnConnect(true);
		
		email = email.setFrom(sendMail, "Recruitment");
		email = email.setSubject(subject);
		email = email.setMsg(msg);
		email = email.addTo(receiver);
		email.setSSL(true);
		
		email.send();
		
	}
}

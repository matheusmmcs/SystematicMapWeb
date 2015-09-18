package br.com.ufpi.systematicmap.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@RequestScoped
public class EmailUltils {
	public static String SUBJECT;
	public static String MENSSAGE;
	public static String RECEIVER_EMAIL;
	public static String RECEIVER_NAME;


	public static void send() throws AddressException, MessagingException, IOException {
		// create e read values default 
		final Properties properties = new Properties(); 
		InputStream in = EmailUltils.class.getResourceAsStream("/mail.properties");  
		properties.load(in); 
		in.close(); 

		Session session = Session.getInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(properties.getProperty("mail.user"), properties.getProperty("mail.password"));
					}
				});

		final MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(properties.getProperty("mail.user")));
		
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(RECEIVER_EMAIL));
		message.setContent(MENSSAGE, "text/html");
		message.setSubject(SUBJECT);
//		message.setText(MENSSAGE);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Transport.send(message);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}

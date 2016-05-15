package fr.iutinfo.skeleton.api;

import java.security.Security;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Mailer {

	
	public static void sendMail(String Adresse, String msg){
		if(Adresse.contains("@")){
			System.out.println("msg : " + msg );
		      // Recipient's email ID needs to be mentioned.
		      String to = Adresse;
	
		      // Sender's email ID needs to be mentioned
		      String from = "Afengi@laposte.net";
	
	
		      // Get system properties
		      Properties props = System.getProperties();
	
		        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		      // Setup mail server
				final String username = from;
				final String password = "2X6kg3ns";
	
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.laposte.net");
				props.put("mail.smtp.port", "587");
	
				Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
	
	
		      try{
		         // Create a default MimeMessage object.
		         MimeMessage message = new MimeMessage(session);
	
		         // Set From: header field of the header.
		         message.setFrom(new InternetAddress(from));
	
		         // Set To: header field of the header.
		         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	
		         // Set Subject: header field
		         message.setSubject("Audace a besoin de vous !");
	
		         // Now set the actual message
		         message.setText(msg);
	
		         // Send message
		         Transport.send(message);
		         System.out.println("Sent message successfully....");
		      }catch (MessagingException mex) {
		         mex.printStackTrace();
		      }
			
		
		}
		
	}
}

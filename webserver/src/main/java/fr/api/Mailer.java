package fr.api;

import java.security.Security;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Mailer {


	public static void sendMail(String Adresse, String msg){
		if(Adresse.contains("@")){
		      // Recipient's email ID needs to be mentioned.
		      String to = Adresse;
	
		      // Sender's email ID needs to be mentioned
		      String from = "no-reply@campus-audace.com";
	
	
		      // Get system properties
		      Properties props = System.getProperties();
	
		        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		      // Setup mail server
				final String username = from;
				final String password = "qm8w288m";
	
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
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
		      }catch (MessagingException mex) {
		         mex.printStackTrace();
		      }
			
		
		}
		
	}
	
	public static String pass(String pass){
		return "Votre mot de passe pour Audace est :" + pass;
	}
}

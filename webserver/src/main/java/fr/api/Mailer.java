package fr.api;

import java.security.Security;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Mailer {


	public static void sendMail(String Adresse, String msg, String sujet){
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
		         message.setSubject(sujet);
	
		         // Now set the actual message
		         message.setText(msg,"utf-8", "html");
	
		         // Send message
		         Transport.send(message);
		      }catch (MessagingException mex) {
		         mex.printStackTrace();
		      }
		}
		
	}
	
	public static void sendMail(Mail m){
		sendMail(m.getAdresse(), m.getMessage(), m.getSujet());
	}
	
	public static String pass(String pass){
		return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "\t<head>\n" +
                "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; />\n" +
                "\t\t<title>Demande de contact !</title>\n" +
                "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "\t</head>\n" +
                "\t\n" +
                "\t\n" +
                "\t<body style=\"margin: 0; padding: 20px 0 20px 0;\">\n" +
                "\n" +
                "\t\t<table align=\"center\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse; border: #a8a9ab;\">\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td align=\"center\" style=\"padding: 20px 0 20px 0;\">\n" +
                "\t\t\t\t\t<img src=\"http://www.campus-audace.com/images/logo.gif\" alt=\"Audace logo\" style=\"display: block;\" />\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td style=\"padding: 5px 0 5px 30px;\">Mot de passe Audace !</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td style=\"padding: 20px 30px 30px 30px;\">\n" +
                "\t\t\t\t Vous pouvez vous connecter à l'application byAudace avec votre adresse mail et le mot de passe :<br /><br />\n" +
                "\t\t\t\t <b>"+pass+"</b><br /><br /><br />\n" +
                "\t\t\t\t L'application est disponible sur le Google Play Store, <a href=\"https://play.google.com/store/apps/details?id=com.audace.byaudace&hl=fr\">Cliquez ici !</a>\n"+
                "\t\t\t</td></tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td bgcolor=\"#a8a9ab\" style=\"padding: 5px 0 5px 30px; color: #FFFFFF; font-family: Arial, sans-serif; font-size: 14px;\">\n" +
                "\t\t\t\t\t&reg; Audace, 2016<br/>\n" +
                "\t\t\t\t\t<font color=\"#FFFFFF\"><i>Ceci est un mail automatique, merci de ne pas y repondre</i></font>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</table>\n" +
                "\t\t\n" +
                "\t</body>\n" +
                "\n" +
                "\n" +
                "</html>";
	}
}

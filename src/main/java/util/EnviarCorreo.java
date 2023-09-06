package util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EnviarCorreo {
	
	public void correo(String correo, String password) {
		String emailEnviador = "cesardeveloper2001@gmail.com";
		String passwordEnviador = "ilgpadnlhtiuovuy";
		
		String mensaje= "<h1>Registro biblioteca</h1>"
				+ "<p>Gracias por registrarte en la biblioteca</p><br>"
				+ "<h2>Sus credenciales son:</h2>"
				+ "<p><strong>Correo:</strong>"+ correo +"</p><br>"
						+ "<p><strong>Correo:</strong>"+ password +"</p><br>"
						+ "<p>Que tenga un buen dia</p>";
		
		Properties props = System.getProperties();
	    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
	    props.put("mail.smtp.user", emailEnviador);
	    props.put("mail.smtp.clave", passwordEnviador);    //La clave de la cuenta
	    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
		
	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);

	    try {
	        message.setFrom(new InternetAddress(emailEnviador));
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));   //Se podrían añadir varios de la misma manera
	        message.setSubject("Biblioteca - Su cuenta fue creado correctamente");
	        message.setText(mensaje);
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", emailEnviador, passwordEnviador);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	    }
	    catch (MessagingException me) {
	        me.printStackTrace();   //Si se produce un error
	    }
	    
	}

}

package com.logicleaptech.email;

import javax.servlet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;


public class Email {

	private final String SMTPhost = "localhost";
	private final String SMTPprop = "mail.smtp.host";

	private String From = "";
	private String To = "";
	private String Cc = "";
	private String Bcc = "";
	private String Subject = "";
	private String Body = "";

	public Email() {
		super();
	}

	public Email(String To, String Subject, String Body, boolean BCConly) {
		super();

		if (BCConly) {
			this.setBcc(To);
		} else {
			this.setBcc(To);
		}
		this.setSubject(Subject);
		this.setBody(Body);

	}

	public boolean Send() {
		boolean retVal = true;

		try {
			Properties props = new Properties();
			props.put(SMTPprop, SMTPhost);
			Session s = Session.getInstance(props, null);

			MimeMessage message = new MimeMessage(s);

			InternetAddress from = new InternetAddress(Fr);
			message.setFrom(from);

			InternetAddress to = new InternetAddress(To);
			InternetAddress bcc = new InternetAddress(Bcc);

			message.addRecipient(Message.RecipientType.TO, to);
			message.addRecipient(Message.RecipientType.BCC, bcc);

			message.setSentDate(new Date());

			String name = request.getParameter("contactname");
			message.setSubject("*** New Contact: " + name
					+ " Beeline-PestControl.com Contact Request Form");
			message.setText("This message was sent from:"
					+ "\n"
					+ "http://www.beeline-pestcontrol.com/contactus.html"
					+ "\n"
					+ "------------------------------------------------------------"
					+ "\n"
					+ "Name:  "
					+ request.getParameter("contactname")
					+ "\n"
					+ "Email: "
					+ request.getParameter("contactemail")
					+ "\n"
					+ "Phone: "
					+ request.getParameter("contactphone")
					+ "\n"
					+ "------------------------------------------------------------"
					+ "\n\n"
					+ request.getParameter("contactmessage")
					+ "\n\n"
					+ "------------------------------------------------------------");

			Transport.send(message);

		} catch (MessagingException e) {
			errorFlag = true;
			// throw new RuntimeException(e);
		}

		return retVal;
	}

	public String getFrom() {
		return From;
	}

	public void setFrom(String from) {
		From = from;
	}

	public String getTo() {
		return To;
	}

	public void setTo(String to) {
		To = to;
	}

	public String getCc() {
		return Cc;
	}

	public void setCc(String cc) {
		Cc = cc;
	}

	public String getBcc() {
		return Bcc;
	}

	public void setBcc(String bcc) {
		Bcc = bcc;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getBody() {
		return Body;
	}

	public void setBody(String body) {
		Body = body;
	}

}

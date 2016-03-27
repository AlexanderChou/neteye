package com.base.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	public static boolean send(String from,String to,String smtpServer,String userName,String userPwd,String subject,String body){ 
		boolean flag = true;
		try { 
			Properties props = new Properties(); 
			props.put("mail.smtp.host", smtpServer); 
			props.put("mail.smtp.auth","true"); 
			Session ssn = Session.getInstance(props, null); 
			MimeMessage message = new MimeMessage(ssn); 
			InternetAddress fromAddress = new InternetAddress(from); 
			message.setFrom(fromAddress); 
			InternetAddress toAddress = new InternetAddress(to); 
			message.addRecipient(Message.RecipientType.TO, toAddress); 
			message.setSubject(subject); 
			message.setText(body); 
			message.setSentDate(new Date()); 	 
			message.saveChanges();
			Transport transport = ssn.getTransport("smtp"); 
			transport.connect(smtpServer, userName, userPwd); 
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO)); 
			transport.close();
		}catch (MessagingException ex){ 
			flag = false;
		} 
		return flag;
	}
}

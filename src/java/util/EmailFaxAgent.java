package com.cap.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EmailFaxAgent {

	public EmailFaxAgent() {
		super();
	}
	
	public static boolean sendEmail(String from, String to, String text, String subject, String path, String filename){
				
		try {
		    // look up MailSession
			Context context = new InitialContext();
			Session mailSession = (Session)context.lookup("mail/ximplemail");
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(from));
			Date timeStamp = new Date();

			// Set the subject and body text
			msg.setSubject(subject);
			msg.setText(text);
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			msg.setSentDate(timeStamp);

			String file = "";
			if( !filename.equals(""))
			{
				file = path + filename;
				setFileAsAttachment(msg, file);
			}
			// msg.saveChanges();
			//	send message
			Transport.send(msg);
			System.out.println("Message Sent");
			
			if( !filename.equals(""))
				removefile(file);      // file clean up after email sent
			
			return true;
		} catch (NamingException e) {
		    e.printStackTrace();
			return false;
		} catch (MessagingException e) {
		    e.printStackTrace();
			return false;
		} 
	} 

	public static boolean sendFax(String faxNo, String faxcfg_path, String filename, String contact, String company, String dept, String email){
		
		try {
			String cfg_filename = filename.split("\\.")[0] + ".txt";
			
			FileOutputStream fax_config = new FileOutputStream(faxcfg_path + cfg_filename);
			OutputStreamWriter config = new OutputStreamWriter(fax_config, "UTF-8");
			config.write("::" + contact + "," + company +"," + "" + "," + dept + "," + email);
			config.write(0x0d);
			config.write(0x0a);
			config.write("::" + faxNo);
			config.write(0x0d);
			config.write(0x0a);
			config.write("::A=" + filename);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
	} 
	
	private static void setFileAsAttachment(Message msg, String file) throws MessagingException {
		
		//Create and fill first part
/*		MimeBodyPart p1 = new MimeBodyPart();
		p1.setText("This is part one of a test multipart e-mail." +
			"The second part is file as an attachment");
*/
		//Create second part
		MimeBodyPart p2 = new MimeBodyPart();
		
		//Put a file in the second part
		FileDataSource fds = new FileDataSource(file);
		p2.setDataHandler(new DataHandler(fds));
		p2.setFileName(fds.getName());
		
		//Create the Multipart.  Add BodyParts to it.
		Multipart mp = new MimeMultipart();
//		mp.addBodyPart(p1);
		mp.addBodyPart(p2);
		
		//Set Multipart as the message's content
		msg.setContent(mp);
	}

	private static void removefile(String file){
		File pdfFile = new File(file);
		boolean isdeleted =   pdfFile.delete();
	} 
}

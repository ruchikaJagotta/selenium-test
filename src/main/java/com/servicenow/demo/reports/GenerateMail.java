package com.servicenow.demo.reports;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;


public class GenerateMail {

	public static void main(String[] args) {
		sendPDFReportByGMail("TestNG report from Eclipse Run", "Enclosed please find attachment");
	}

	public static void sendPDFReportByGMail(String subject, String body) {
		Properties properties = System.getProperties();
		try (FileInputStream fis = new FileInputStream("src/main/resources/config/mail.properties")) {
			properties.load(fis);
		} catch (IOException e) {
			Logger.getLogger(GenerateMail.class).error(e);
		}

		String pass = properties.getProperty("mail.smtp.password");
		String from = properties.getProperty("mail.smtp.from");
		String to = properties.getProperty("mail.smtp.to");
		String host= properties.getProperty("mail.smtp.host");
		
		properties.put("mail.smtp.starttls.enable", "true");

		properties.put("mail.smtp.host", host);

		properties.put("mail.smtp.user", from);

		properties.put("mail.smtp.password", pass);

		properties.put("mail.smtp.port", "587");

		properties.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(properties);

		MimeMessage message = new MimeMessage(session);

		try {

			// Set from address

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set subject

			message.setSubject(subject);

			message.setText(body);

			BodyPart objMessageBodyPart = new MimeBodyPart();

			objMessageBodyPart.setText("Please Find The Attached Report File!");

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(objMessageBodyPart);

			objMessageBodyPart = new MimeBodyPart();

			// Set path to the pdf report file

			String filename = "./test-output/emailable-report.html";

			// Create data source to attach the file in mail

			DataSource source = new FileDataSource(filename);

			objMessageBodyPart.setDataHandler(new DataHandler(source));

			objMessageBodyPart.setFileName(source.getName());

			multipart.addBodyPart(objMessageBodyPart);

			message.setContent(multipart);

			Transport transport = session.getTransport("smtp");

			transport.connect(host, from, pass);

			transport.sendMessage(message, message.getAllRecipients());

			transport.close();

		}

		catch (AddressException ae) {

			ae.printStackTrace();

		}

		catch (MessagingException me) {

			me.printStackTrace();

		}

	}
}

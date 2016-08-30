package com.MyProject.email;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.MyProject.domain.task.TaskEntity;
import com.MyProject.domain.task.TaskStatusHistoryEntity;
import com.MyProject.domain.user.UserEntity;

/**
 * @author Di
 *
 */
public class Email extends Thread{

	String typeEmail;
	UserEntity userEntity;
	TaskEntity taskEntity;
	TaskStatusHistoryEntity taskStatusHistoryEntity;

	/**
	 * @param typeEmail
	 * @param taskEntity
	 * @param taskStatusHistoryEntity
	 */
	public Email(String typeEmail, TaskEntity taskEntity, TaskStatusHistoryEntity taskStatusHistoryEntity) {
		this.typeEmail = typeEmail;
		this.taskEntity = taskEntity;
		this.taskStatusHistoryEntity = taskStatusHistoryEntity;
	}

	/**
	 * @param typeEmail
	 * @param userEntity
	 */
	public Email(String typeEmail, UserEntity userEntity) {
		this.typeEmail = typeEmail;
		this.userEntity = userEntity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run () {

		if(typeEmail.equals("SendEmailNewUser")){

			String mailSubject 	= "Notification from  MyProject Application";
			String mailMessage 	= "<html><body BODY>";
			mailMessage += getHTMLStyle();
			mailMessage += "<div class='Container100 Responsive100 GreenTheme White'> <div class='ContainerIndent TexAlCenter Fs16'>";
			mailMessage += "Welcome " + userEntity.toString()  + " to MyProject Application<br/>";
			mailMessage += "</div></div><div class='pageEmptyBox'></div>";
			mailMessage += "<div class='Container100 Responsive100 GreenTheme White'> <div class='ContainerIndent TexAlLeft Fs14'>";
			mailMessage += "User Name: " + userEntity.getUserName();
			mailMessage += "<br/><br/> Password: " + userEntity.getPassword();
			mailMessage += "</div></div>";
			mailMessage += getFooter();
			mailMessage += "</body></html>";
			String from = "MyProject@gmail.com";
			String to 	= userEntity.getEmail();

			SendEmail(mailSubject, mailMessage, from, to);

		}

		if(typeEmail.equals("SendEmailChangeStatus")){

			if (taskStatusHistoryEntity.getUserAuthor() != taskStatusHistoryEntity.getUserExecutor()) {

				String mailSubject 	= "Notification from  MyProject Application. Task #: " + taskEntity.getId() + ". Status has been changed";
				String mailMessage 	= "<html><body BODY>";
				mailMessage += getHTMLStyle();
				mailMessage += "<div class='Container100 Responsive100 GreenTheme White'> <div class='ContainerIndent TexAlLeft Fs14'>";
				mailMessage += "Status has been changed, Task #: "  + taskEntity.getId();
				mailMessage += "<br/><br/> Task Name: " + taskEntity.toString();
				mailMessage += "<br/><br/> at " + taskStatusHistoryEntity.getDate();
				mailMessage += "<br/><br/> Comment: "+ taskStatusHistoryEntity.getComment();
				mailMessage += "<br/><br/> You set as Executor, for current status: " + taskEntity.getTaskStatus();
				mailMessage += "</div></div>";
				mailMessage += getFooter();
				mailMessage += "</body></html>";
				String from = "MyProject@gmail.com";
				String to 	=  taskStatusHistoryEntity.getUserExecutor().getEmail();

				SendEmail(mailSubject, mailMessage, from, to);

			}
		}

		if(typeEmail.equals("SendEmailNewTask")){

			if (taskEntity.getUserResponsible() != taskEntity.getUsercurrentExecutor()) {

				String mailSubject 	= "Notification from  MyProject Application. New Task #: " + taskEntity.getId();
				String mailMessage 	= "<html><body BODY>";
				mailMessage += getHTMLStyle();
				mailMessage += "<div class='Container100 Responsive100 GreenTheme White'> <div class='ContainerIndent TexAlLeft Fs14'>";
				mailMessage += "New task has been created, Task #: " + taskEntity.getId();
				mailMessage += "<br/><br/> Task Name: " + taskEntity.toString();
				mailMessage += "<br/><br/> Type: " + taskEntity.getTaskType();
				mailMessage += "<br/><br/> Priority: " + taskEntity.getTaskPriority();
				mailMessage += "<br/><br/> You set as Executor, for current status: " + taskEntity.getTaskStatus();
				mailMessage += "</div></div>";
				mailMessage += getFooter();
				mailMessage += "</body></html>"; 
				String from = "MyProject@gmail.com";
				String to 	= taskStatusHistoryEntity.getUserExecutor().getEmail();

				SendEmail(mailSubject, mailMessage, from, to);

			}
		}

		if(typeEmail.equals("SendFileEmail")){

		}

	}

	/**
	 * Send email (text/html)
	 * @param String mailSubject
	 * @param String mailMessage
	 * @param String from
	 * @param String to
	 * @author Di
	 */
	public void SendEmail(String mailSubject, String mailMessage, String from, String to) {    

		String propFileName = "configEmail.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		
		try {
			Properties prop = new Properties();

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			// get the property value and print it out

			String mailTransportProtocol = prop.getProperty("mailTransportProtocol");
			String mailHost = prop.getProperty("mailHost");
			String mailSmtpPort = prop.getProperty("mailSmtpPort");
			String mailSmtpAuth = prop.getProperty("mailSmtpAuth");
			String mailSmtpStarttlsEnable = prop.getProperty("mailSmtpStarttlsEnable");
			String mailSmtpSslEnable = prop.getProperty("mailSmtpSslEnable");
			String mailSmtpSocketFactoryPort = prop.getProperty("mailSmtpSocketFactoryPort");
			String mailSmtpSocketFactoryClass = prop.getProperty("mailSmtpSocketFactoryClass");
			String mailSmtpSocketFactoryFallback = prop.getProperty("mailSmtpSocketFactoryFallback");
			final String username = prop.getProperty("username");
			final String password = prop.getProperty("password");

			Properties properties = System.getProperties();

			// Setup mail server
			properties.setProperty("mail.transport.protocol", 			mailTransportProtocol);
			properties.setProperty("mail.host", 						mailHost);
			properties.setProperty("mail.smtp.port", 					mailSmtpPort);
			properties.setProperty("mail.smtp.auth", 					mailSmtpAuth);
			properties.setProperty("mail.smtp.starttls.enable", 		mailSmtpStarttlsEnable);
			properties.setProperty("mail.smtp.ssl.enable", 				mailSmtpSslEnable);
			properties.setProperty("mail.smtp.socketFactory.port", 		mailSmtpSocketFactoryPort);
			properties.setProperty("mail.smtp.socketFactory.class", 	mailSmtpSocketFactoryClass);
			properties.setProperty("mail.smtp.socketFactory.fallback", 	mailSmtpSocketFactoryFallback);

			// Get the default Session object.
			Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			session.setDebug(true);

			try{
				// Create a default MimeMessage object.
				MimeMessage message = new MimeMessage(session);

				// Set From: header field of the header.
				message.setFrom(new InternetAddress(from));

				// Set To: header field of the header.
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

				// Set Subject: header field
				message.setSubject(mailSubject);

				// Now set the actual message
				message.setContent(mailMessage, "text/html");

				// Send message
				Transport.send(message);
				System.out.println("Sent message successfully....");
			}catch (MessagingException mex) {
				mex.printStackTrace();
			}

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Send file email (not works)
	 * @param mailSubject
	 * @param mailMessage
	 * @param from
	 * @param to
	 * @param filename
	 * @author Di
	 */
	public void SendFileEmail(String mailSubject, String mailMessage, String from, String to, ArrayList<String> filename)
	{
		// Recipient's email ID needs to be mentioned.
		// String to = "abcd@gmail.com";

		// Sender's email ID needs to be mentioned
		// String from = "web@gmail.com";

		// Assuming you are sending email from localhost
		String host = "localhost";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.transport.protocol", "smtp");

		if (host !="") {
			properties.setProperty("mail.host", host);
		}
		else {
			properties.setProperty("mail.host", "smtp.mail.ru");
		}

		properties.setProperty("mail.user", "t.c.d");
		properties.setProperty("mail.password", "password");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(mailSubject);

			// Create the message part 
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(mailMessage);

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			//         // Part two is attachment
			//         messageBodyPart = new MimeBodyPart();
			//         String filename = "file.txt";
			//         DataSource source = new FileDataSource(filename);
			//         messageBodyPart.setDataHandler(new DataHandler(source));
			//         messageBodyPart.setFileName(filename);
			//         multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		}catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	/**
	 * Get HTML Style
	 * @return String
	 * @author Di
	 */
	public String getHTMLStyle(){
		return "<style type='text/css'>"
				+".BODY{position: fixed;left: 50%;transform: translate(-50%, +10%);} "
				+".Container100{width: 100%;float: left;padding: 10px;} "
				+".Responsive100{width: 100%;float: left;} "
				+".ContainerIndent{display:block; margin:0px auto; padding: 10px;} "
				+".pageEmptyBox{display: block; width: 100%; height: 1px; overflow: hidden;} "
				+".Fs9{font-size:9px !important;} " 
				+".Fs10{font-size:10px !important;} "
				+".Fs11{font-size:11px !important;} "
				+".Fs12{font-size:12px !important;} " 
				+".Fs13{font-size:13px !important;} " 
				+".Fs14{font-size:14px !important;} " 
				+".Fs15{font-size:15px !important;} " 
				+".Fs16{font-size:16px !important;} " 
				+".Fs17{font-size:17px !important;} " 
				+".Fs18{font-size:18px !important;} " 
				+".Fs19{font-size:19px !important;} " 
				+".Fs20{font-size:20px !important;} "
				+".TexAlCenter{ text-align:center;} "
				+".TexAlLeft{ text-align:left;} "
				+".TexAlRight{ text-align:right !important;} "
				+".GreenTheme { background-image: linear-gradient(90deg, rgb(26, 188, 156) 25%,rgb(22, 160, 133) 100%);"
				+" background-image: -moz-linear-gradient(left, rgb(26, 188, 156) 25%,rgb(22, 160, 133) 100%);"
				+" background-image: -webkit-linear-gradient(left, rgb(26, 188, 156) 25%,rgb(22, 160, 133) 100%);"
				+" background-image: -o-linear-gradient(left, rgb(26, 188, 156) 25%,rgb(22, 160, 133) 100%);"
				+" background-image: -ms-linear-gradient(left, rgb(26, 188, 156) 25%,rgb(22, 160, 133) 100%); background-size: 100%;} "
				+".White{color:#ffffff !important;} "
				+".Blue{color: #0288D1;} "
				+".HoverEffect {-webkit-transition: all 0.5s ease;-moz-transition: all 0.5s ease;-ms-transition: all 0.5s ease;-o-transition: all 0.5s ease;transition: all 0.5s ease;} "
				+".HoverEffect:hover {opacity: 0.8;-webkit-transition: all 0.5s ease;-moz-transition: all 0.5s ease;-ms-transition: all 0.5s ease;-o-transition: all 0.5s ease;transition: all 0.5s ease;}"
				+"</style>";
	}

	/**
	 * Get footer
	 * @return String
	 */
	public String getFooter(){
		return "<div class='pageEmptyBox'></div>"
				+"<div class='Container100 Responsive100 GreenTheme White'> <div class='ContainerIndent TexAlLeft Fs14'>"
				+"Best regards"
				+"<br/><br/>"
				+ getLink()
				+"</div></div>";
	}

	public String getLink(){
		return "<div class='pageEmptyBox'></div>"
				+"<a href='http://myprojectapplication-myprojectaccount.rhcloud.com/' class='White Fs14 Fleft'>MyProject Application</a> Team";
	}

	public String getTypeEmail() {
		return typeEmail;
	}

	public void setTypeEmail(String typeEmail) {
		this.typeEmail = typeEmail;
	}

	public TaskStatusHistoryEntity getTaskStatusHistoryEntity() {
		return taskStatusHistoryEntity;
	}

	public void setTaskStatusHistoryEntity(TaskStatusHistoryEntity taskStatusHistoryEntity) {
		this.taskStatusHistoryEntity = taskStatusHistoryEntity;
	}

	public TaskEntity getTaskEntity() {
		return taskEntity;
	}

	public void setTaskEntity(TaskEntity taskEntity) {
		this.taskEntity = taskEntity;
	}

}
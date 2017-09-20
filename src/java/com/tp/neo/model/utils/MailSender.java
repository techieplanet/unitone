/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model.utils;

/**
 *
 * @author Swedge
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


public class MailSender {

    private static Session session ;
    private static String username;
    public static String companyName;
    public  static void setUPServer() {
         // Assuming you are sending email from localhost
     
        InputStream input = MailSender.class.getClassLoader().getResourceAsStream("com/tp/neo/properties/neo.properties"); // you could do com/tp/neo/foo.properties if your properties file is in a package folder
        Properties  tProperties = new Properties();
        try {
            tProperties.load(input);
        } catch (IOException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
        properties.put("mail.smtp.host",tProperties.getProperty("mail.smtp.host") );
        properties.put("mail.smtp.port", tProperties.getProperty("mail.smtp.port"));
        properties.put("mail.smtp.auth", tProperties.getProperty("mail.smtp.auth"));
        properties.put("mail.smtp.starttls.enable", tProperties.getProperty("mail.smtp.starttls.enable"));
        
        
         // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(tProperties.getProperty("smtp.username"), tProperties.getProperty("smtp.password"));
            }
        };
        
      // Get the default Session object.
      session = Session.getInstance(properties, auth);
      username = tProperties.getProperty("smtp.username");
    }
    
   public void sendSimpleEMail(String to, String from, String subject, String messageText)
   {
      if(session == null)
          setUPServer();
      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from ,companyName));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // Set Subject: header field
         message.setSubject(subject);

         // Now set the actual message
         message.setText(messageText);

         // Send message
         Transport.send(message);
         //System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      } catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        } 
      
   }
   
   
   public void sendHtmlEmail(String to, String from, String subject, String htmlMessage)
   {   
      if(session == null)
          setUPServer();

      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from , companyName));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // Set Subject: header field
         message.setSubject(subject);

         // Send the actual HTML message, as big as you like
         message.setContent(htmlMessage, "text/html" );

         // Send message
         Transport.send(message);
         //System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      } catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public MailSender setCompanyName(String CompanyName){
       this.companyName = CompanyName;
       return this;
   }
   public static void sendEmailAttachment (String to, String from, String subject, String htmlMessage , String tempFilename , String filename )
   {
       if(session == null)
          setUPServer();
       
      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(username , companyName));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress(to));

         // Set Subject: header field
         message.setSubject(subject);

         // Create the message part 
         BodyPart messageBodyPart = new MimeBodyPart();

         // Fill the message
         // Send the actual HTML message, as big as you like
         messageBodyPart.setContent(htmlMessage, "text/html" );
        // messageBodyPart.setText(htmlMessage);
         
         // Create a multipar message
         Multipart multipart = new MimeMultipart();

         // Set text message part
         multipart.addBodyPart(messageBodyPart);

         // Part two is attachment
         messageBodyPart = new MimeBodyPart();
         DataSource source = new FileDataSource(tempFilename);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(filename);
         multipart.addBodyPart(messageBodyPart);

         // Send the complete message parts
         message.setContent(multipart );

         // Send message
         Transport.send(message);
         //System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      } catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
 
}
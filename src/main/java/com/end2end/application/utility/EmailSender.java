package com.end2end.application.utility;

import com.end2end.application.user.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class EmailSender {

    public static void send(JavaMailSender mailSender, HashMap<String, String> emailContents) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom(emailContents.get("senderEmail"), emailContents.get("senderName"));
        messageHelper.setTo(emailContents.get("receiver"));
        messageHelper.setSubject(emailContents.get("subject"));
        messageHelper.setText(emailContents.get("content"), true);

        mailSender.send(message);
    }
}

package com.end2end.application.utility;

import com.end2end.application.user.User;

import java.util.HashMap;

public class EmailTemplate {
    public static HashMap<String, String> verificationEmail(String subject, String senderEmail ,String senderName, String url, User user) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("subject", subject);
        hashMap.put("senderEmail", senderEmail);
        hashMap.put("senderName", senderName);
        hashMap.put("receiver", user.getEmail());

        String emailContents = "<p>Hello, "+ user.getFirstName() +"</p>" +
                "<p>Thank you for joining our community!<br>"+
                "<p>Please click the link below to complete your registration</p>" +
                "<a href=\"" + url + "\">Click here to verify your account</a>" +
                "<p>See you soon !</p>";

        hashMap.put("content", emailContents);
        return hashMap;
    }
}

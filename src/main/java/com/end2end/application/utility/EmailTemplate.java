package com.end2end.application.utility;

import com.end2end.application.user.User;

import java.util.HashMap;

public class EmailTemplate {
    public static HashMap<String, String> createEmail(String subject, String senderEmail ,String senderName, String url, User user, boolean emailVerification) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("subject", subject);
        hashMap.put("senderEmail", senderEmail);
        hashMap.put("senderName", senderName);
        hashMap.put("receiver", user.getEmail());

        String emailContents = emailVerification ? emailVerificationContents(user, url) : passwordResetContents(user, url);

        hashMap.put("content", emailContents);
        return hashMap;
    }

    private static String emailVerificationContents(User user, String url) {
        return  "<p>Hello, "+ user.getFirstName() +"</p>" +
                "<p>Thank you for joining our community !<br>"+
                "<p>Please click the link below to complete your registration</p>" +
                "<a href=\"" + url + "\">Click here to verify your account</a>" +
                "<p>See you soon !</p>";
    }

    private static String passwordResetContents(User user, String url) {
        return  "<p>Hello, "+ user.getFirstName() +"</p>" +
                "<p>We've heard that you forgot you password !<br>"+
                "<p>Please click the link below to reset your password</p>" +
                "<a href=\"" + url + "\">Reset Password</a>" +
                "<p>Hope you get your account soon !</p>";
    }
}

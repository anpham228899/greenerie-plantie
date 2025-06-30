package com.example.greenerieplantie;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendVerificationEmailTask extends AsyncTask<Void, Void, Boolean> {

    private final String recipientEmail;
    private final String verificationCode;
    private final Context context;
    private final Runnable onSuccess;

    public SendVerificationEmailTask(Context context, String recipientEmail, String verificationCode, Runnable onSuccess) {
        this.recipientEmail = recipientEmail;
        this.verificationCode = verificationCode;
        this.context = context;
        this.onSuccess = onSuccess;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        final String senderEmail = "hoangt22411ca@st.uel.edu.vn";
        final String senderPassword = "ofks blyj ufcy cphn";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Your Verification Code");
            message.setText("Your verification code is: " + verificationCode);
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            onSuccess.run();
        } else {
            Toast.makeText(context, "Failed to send verification email.", Toast.LENGTH_LONG).show();
        }
    }
}

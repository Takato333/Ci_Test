package com.example.demo.service;

import com.example.demo.model.EmailDetail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(EmailDetail emailDetail) {
        try {
            Context context = new Context();
            context.setVariable("name",emailDetail.getReceiver().getEmail());
            context.setVariable("button","Go to home page");
            context.setVariable("link",emailDetail.getLink());
            String template = templateEngine.process("welcome-template", context);

            //Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            //Setting up necessary details
            mimeMessageHelper.setFrom("hairharmony@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getReceiver().getEmail());
            mimeMessageHelper.setText(template, true);
            mimeMessageHelper.setSubject(emailDetail.getSubject());

            //Send email
            javaMailSender.send(mimeMessage);
        }catch(MessagingException e) {
            System.out.println("ERROR SENT EMAIL!!!");
        }
    }
}


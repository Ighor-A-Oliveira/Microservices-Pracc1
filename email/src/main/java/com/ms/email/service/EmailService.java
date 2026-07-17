package com.ms.email.service;

import com.ms.email.repo.EmailRepo;
import com.ms.email.entity.EmailEntity;
import com.ms.email.entity.StatusEmail;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    private final EmailRepo emailRepo;
    private final JavaMailSender emailSender;


    public EmailService(EmailRepo emailRepo, JavaMailSender emailSender) {
        this.emailRepo = emailRepo;
        this.emailSender = emailSender;
    }


    @Value(value = "${spring.mail.username}")
    private String emailFrom;


    @Transactional
    public EmailEntity sendEmail(EmailEntity email) {
        try {
            email.setSendDateEmail(LocalDateTime.now());
            email.setEmailFrom(emailFrom);

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email.getEmailTo());
            msg.setSubject(email.getSubject());
            msg.setText(email.getText());
            emailSender.send(msg);

            email.setStatusEmail(StatusEmail.SENT);

        } catch (MailException e) {
            email.setStatusEmail(StatusEmail.ERROR);
        }

        return emailRepo.save(email);
    }

}

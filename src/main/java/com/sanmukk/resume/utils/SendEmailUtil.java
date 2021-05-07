package com.sanmukk.resume.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SendEmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String email, String meg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("简历审核结果");
        message.setFrom("18965600766ljl@sina.com");
        message.setTo(email);
        message.setText(meg);
        javaMailSender.send(message);
    }

}

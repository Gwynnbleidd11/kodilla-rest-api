package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail() {
        //Given
//        Mail mail = new Mail("test@test.com", "Test", "Test Message", null);
        Mail mail2 = new Mail();
        mail2.mailBuilder("test@test.com", "Test", "Test Message", "anothertest@test.com");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail2.getMailTo());
        mailMessage.setSubject(mail2.getSubject());
        mailMessage.setText(mail2.getMessage());

        //When
        simpleEmailService.send(mail2);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }

}
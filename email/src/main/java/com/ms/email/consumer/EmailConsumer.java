package com.ms.email.consumer;

import com.ms.email.dto.request.EmailRequest;
import com.ms.email.entity.EmailEntity;
import com.ms.email.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailService emailServ;

    public EmailConsumer(EmailService emailServ) {
        this.emailServ = emailServ;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRequest request){ //prox passo, mudar esse String para EmailDTO
        System.out.println(request.emailTo());
        var email = new EmailEntity();
        BeanUtils.copyProperties(request, email);

        emailServ.sendEmail(email);
    }

}

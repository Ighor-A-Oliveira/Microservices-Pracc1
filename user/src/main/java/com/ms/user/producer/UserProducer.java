package com.ms.user.producer;

import com.ms.user.dto.request.EmailRequest;
import com.ms.user.entity.UserEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {
    final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(UserEntity user){
        var emailRequest = new EmailRequest(
                user.getUserId(),
                user.getEmail(),
                "Cadastro feito com sucesso!",
                user.getName() + ", seja bem vindo(a)! \nAgradecemos o seu cadastro, aproveite agora todos os recursos da nossa plataforma"
        );

        rabbitTemplate.convertAndSend("", routingKey, emailRequest);
    }
}

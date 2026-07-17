package com.ms.user.service;

import com.ms.user.dto.request.UserRequest;
import com.ms.user.dto.response.UserResponse;
import com.ms.user.entity.UserEntity;
import com.ms.user.producer.UserProducer;
import com.ms.user.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final UserProducer userProd;

    public UserService(UserRepo userRepo, UserProducer userProd) {
        this.userRepo = userRepo;
        this.userProd = userProd;
    }

    @Transactional
    public UserResponse saveUser(UserRequest request){
        boolean userExiste = userRepo.existsByEmail(request.email());
        if(userExiste){
            throw new RuntimeException("Ja existe usuario cadastrado com esse email");
        }

        UserEntity user = new UserEntity();
        user.setEmail(request.email());
        user.setName(request.name());

        UserEntity userSalvo = userRepo.save(user);

        userProd.publishMessageEmail(userSalvo);

        return UserResponse.fromEntity(userSalvo);
    }
}

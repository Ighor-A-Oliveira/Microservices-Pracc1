package com.ms.email.repo;

import com.ms.email.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailRepo extends JpaRepository<EmailEntity, UUID> {
}

package com.ms.user.dto.request;

import java.util.UUID;

public record EmailRequest(
        UUID userId,
        String emailTo,
        String subject,
        String text
) {
    }
package dev.kopasz.sla_itsm_api.api.dto.response;

import java.time.LocalDateTime;

public record SlaResponse (
    String ticketId,
    LocalDateTime resolutionDeadline,
    boolean isBreached
)
{}

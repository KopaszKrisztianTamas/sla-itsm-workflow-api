package dev.kopasz.sla_itsm_api.dto;

import java.time.LocalDateTime;

public record SlaResponse (
    String tickedId,
    LocalDateTime resolutionDeadline,
    boolean isBreached
)
{}

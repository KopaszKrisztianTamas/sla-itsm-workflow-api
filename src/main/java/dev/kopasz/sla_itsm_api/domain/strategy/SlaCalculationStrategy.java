package dev.kopasz.sla_itsm_api.domain.strategy;

import dev.kopasz.sla_itsm_api.domain.model.Ticket;

import java.time.LocalDateTime;

public sealed interface SlaCalculationStrategy permits StandardSlaStrategy, CriticalSlaStrategy {

    LocalDateTime calculateDeadline(Ticket ticket);

}

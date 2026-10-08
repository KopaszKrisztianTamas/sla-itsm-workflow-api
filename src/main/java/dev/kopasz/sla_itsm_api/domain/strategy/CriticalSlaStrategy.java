package dev.kopasz.sla_itsm_api.domain.strategy;

import dev.kopasz.sla_itsm_api.domain.model.Ticket;

import java.time.LocalDateTime;

public final class CriticalSlaStrategy implements SlaCalculationStrategy{

    @Override
    public LocalDateTime calculateDeadline(Ticket ticket) {

        return ticket.getCreatedAt().plusHours(4);

    }
}

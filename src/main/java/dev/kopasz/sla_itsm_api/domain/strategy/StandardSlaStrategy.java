package dev.kopasz.sla_itsm_api.domain.strategy;

import dev.kopasz.sla_itsm_api.domain.model.Ticket;

import java.time.LocalDateTime;

public final class StandardSlaStrategy implements SlaCalculationStrategy{

    @Override
    public LocalDateTime calculateDeadline(Ticket ticket) {

        return switch (ticket.getPriority()) {
            case HIGH -> ticket.getCreatedAt().plusHours(24);
            case MEDIUM -> ticket.getCreatedAt().plusHours(48);
            case LOW -> ticket.getCreatedAt().plusHours(72);
        };

    }

}

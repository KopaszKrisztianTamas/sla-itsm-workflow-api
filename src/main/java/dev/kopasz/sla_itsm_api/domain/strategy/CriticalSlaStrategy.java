package dev.kopasz.sla_itsm_api.domain.strategy;

import dev.kopasz.sla_itsm_api.domain.model.Ticket;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public final class CriticalSlaStrategy implements SlaCalculationStrategy{

    @Override
    public LocalDateTime calculateDeadline(Ticket ticket) {

        return ticket.createdAt().plusHours(4);

    }
}

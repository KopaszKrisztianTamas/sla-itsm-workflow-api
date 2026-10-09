package dev.kopasz.sla_itsm_api.domain.strategy;

import dev.kopasz.sla_itsm_api.domain.model.Ticket;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public final class StandardSlaStrategy implements SlaCalculationStrategy{

    @Override
    public LocalDateTime calculateDeadline(Ticket ticket) {

        return switch (ticket.priority()) {
            case HIGH -> ticket.createdAt().plusHours(24);
            case NORMAL -> ticket.createdAt().plusHours(48);
            case LOW -> ticket.createdAt().plusHours(72);
            case CRITICAL -> throw new IllegalArgumentException(
                    "A StandardSlaStrategy nem kezelhet CRITICAL prioritású jegyet! Ticket ID: " + ticket.id()
            );
        };

    }

}

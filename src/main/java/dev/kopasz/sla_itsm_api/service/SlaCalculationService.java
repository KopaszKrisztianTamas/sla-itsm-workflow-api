package dev.kopasz.sla_itsm_api.service;

import dev.kopasz.sla_itsm_api.api.dto.request.TicketCreateRequest;
import dev.kopasz.sla_itsm_api.api.dto.response.SlaResponse;
import dev.kopasz.sla_itsm_api.domain.model.Priority;
import dev.kopasz.sla_itsm_api.domain.model.Ticket;
import dev.kopasz.sla_itsm_api.domain.model.TicketStatus;
import dev.kopasz.sla_itsm_api.domain.strategy.SlaCalculationStrategy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class SlaCalculationService {

    private Map<String, SlaCalculationStrategy> strategies;

    public SlaCalculationService(Map<String, SlaCalculationStrategy> strategies) {}

    public SlaResponse processTicketCreation(TicketCreateRequest ticketCreateRequest) {

        Priority priority = switch (ticketCreateRequest.severity()) {
            case S1 ->  Priority.CRITICAL;
            case S2 ->  Priority.HIGH;
            case S3 ->  Priority.MEDIUM;
            case S4 ->  Priority.LOW;
        };
        SlaCalculationStrategy strategy = strategies.get(priority.name());
        if (strategy == null) { throw new RuntimeException("Strategy not found"); }

        Ticket ticket = new Ticket(
            UUID.randomUUID().toString(),
            ticketCreateRequest.title(),
            priority,
            TicketStatus.OPEN,
            LocalDateTime.now(),
            null
        );

        ticket.deadline(strategy.calculateDeadline(ticket));



    }
}

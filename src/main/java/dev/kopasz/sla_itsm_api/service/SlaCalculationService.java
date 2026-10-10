package dev.kopasz.sla_itsm_api.service;

import dev.kopasz.sla_itsm_api.api.dto.request.TicketCreateRequest;
import dev.kopasz.sla_itsm_api.api.dto.response.SlaResponse;
import dev.kopasz.sla_itsm_api.domain.model.Priority;
import dev.kopasz.sla_itsm_api.domain.model.Ticket;
import dev.kopasz.sla_itsm_api.domain.model.TicketStatus;
import dev.kopasz.sla_itsm_api.domain.strategy.SlaStrategyFactory;
import org.springframework.stereotype.Service;

//SlaStrategy selection solution #1
//import java.util.Map;
//import dev.kopasz.sla_itsm_api.domain.exception.SlaCalculationException;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
public class SlaCalculationService {

    //  SlaStrategy selection solution #1 - Spring Map injection
    //  private Map<String, SlaCalculationStrategy> strategies;

    //  SlaStrategy selection solution #2 - Compile-time checked Domain Driven design - more robust
    private final SlaStrategyFactory slaStrategyFactory;

    public SlaCalculationService(SlaStrategyFactory slaStrategyFactory) {
        this.slaStrategyFactory = slaStrategyFactory;
    }

    public SlaResponse processTicketCreation(TicketCreateRequest ticketCreateRequest) {

        Priority priority = switch (ticketCreateRequest.severity()) {
            case S1 ->  Priority.CRITICAL;
            case S2 ->  Priority.HIGH;
            case S3 ->  Priority.NORMAL;
            case S4 ->  Priority.LOW;
        };

/*
         SlaStrategy selection solution #1

         SlaCalculationStrategy strategy = strategies.get(priority.name());
         if (strategy == null) {
             throw new SlaCalculationException(
                     "Strategy not found for ticket having severity " + ticketCreateRequest.severity() +
                     ", priority " + priority.name());
         }
*/

        Ticket ticket = new Ticket(
            UUID.randomUUID().toString(),
            ticketCreateRequest.title(),
            priority,
            TicketStatus.OPEN,
            now(),
                null
        );

        Ticket ticketWDeadline = new Ticket(
                ticket,
                //  solution #2
                slaStrategyFactory.getStrategy(priority).calculateDeadline(ticket)
        );

        return new SlaResponse(
                ticketWDeadline.id(), ticketWDeadline.deadline(), false
        );

    }
}

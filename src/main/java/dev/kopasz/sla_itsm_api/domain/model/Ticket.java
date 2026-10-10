package dev.kopasz.sla_itsm_api.domain.model;

import java.time.LocalDateTime;

public record Ticket(

    String id,
    String title,
    Priority priority,
    TicketStatus status,
    LocalDateTime createdAt,
    LocalDateTime deadline

) {
    public Ticket(Ticket baseTicket, LocalDateTime deadline) {
        this(baseTicket.id(),
             baseTicket.title(),
             baseTicket.priority,
             baseTicket.status,
             baseTicket.createdAt,
             deadline
        );
    }

}


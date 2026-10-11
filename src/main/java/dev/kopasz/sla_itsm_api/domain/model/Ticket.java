package dev.kopasz.sla_itsm_api.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public record Ticket(

    String id,
    String title,
    String description,
    Priority priority,
    TicketStatus status,
    LocalDateTime createdAt,
    List<String> affectedServices,
    LocalDateTime deadline

) {
    public Ticket(Ticket baseTicket, LocalDateTime deadline) {
        this(baseTicket.id,
             baseTicket.title,
             baseTicket.description,
             baseTicket.priority,
             baseTicket.status,
             baseTicket.createdAt,
             List.copyOf(baseTicket.affectedServices),
             deadline
        );
    }

}


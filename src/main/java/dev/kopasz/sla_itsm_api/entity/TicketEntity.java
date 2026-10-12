package dev.kopasz.sla_itsm_api.entity;

import dev.kopasz.sla_itsm_api.domain.model.Priority;
import dev.kopasz.sla_itsm_api.domain.model.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="ticket")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketEntity {

    @Id
    @Column(name="id", nullable = false, updatable = false)
    private String id;

    @Column(name="title", nullable = false, length = 100)
    private String title;

    @Column(name="description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name="priority", nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private TicketStatus status;

    @Column(name = "created_at",nullable = false, updatable = false)

}

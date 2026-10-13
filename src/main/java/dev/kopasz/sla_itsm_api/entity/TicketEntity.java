package dev.kopasz.sla_itsm_api.entity;

import dev.kopasz.sla_itsm_api.domain.model.Priority;
import dev.kopasz.sla_itsm_api.domain.model.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="ticket")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketEntity {

    @Id
    @Column(name="id", nullable = false, updatable = false)
    private String id;

    @Setter
    @Column(name="title", nullable = false, length = 100)
    private String title;

    @Setter
    @Column(name="description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name="priority", nullable = false)
    private Priority priority;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private TicketStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "ticket_affected_services", joinColumns = @JoinColumn(name = "ticket_id"))
    @Column(name = "service_name")
    private Set<String> affectedServices = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // hogy elkerüljük a getId()!=null-ra futást ami false-t adna önmagára
        if (!(o instanceof TicketEntity that)) return false;
//        return Objects.equals(getId(), that.getId());
        return getId()!=null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
//        return Objects.hashCode(getId());
        return getClass().hashCode();
    }



}

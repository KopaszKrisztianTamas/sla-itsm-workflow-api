package dev.kopasz.sla_itsm_api.mapper;

import dev.kopasz.sla_itsm_api.api.dto.request.TicketCreateRequestDTO;
import dev.kopasz.sla_itsm_api.domain.model.Priority;
import dev.kopasz.sla_itsm_api.domain.model.Severity;
import dev.kopasz.sla_itsm_api.domain.model.Ticket;
import dev.kopasz.sla_itsm_api.domain.model.TicketStatus;
import dev.kopasz.sla_itsm_api.entity.TicketEntity;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        imports = {UUID.class, LocalDateTime.class, TicketStatus.class, Set.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE, // due to id, status, deadline
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT // List->Set
)
public interface TicketMapper {

    @Mapping(source = "severity", target = "priority")
    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "status", expression = "java(TicketStatus.OPEN)")
    Ticket dtoToTicket(TicketCreateRequestDTO dto);

    TicketEntity ticketToEntity(Ticket  ticket);

    Ticket entitytoTicket(TicketEntity  ticketEntity);

    //    default Priority severityToPriority(Severity severity) {
    static Priority severityToPriority(Severity severity) {
        return switch (severity) {
            case S1 ->  Priority.CRITICAL;
            case S2 ->  Priority.HIGH;
            case S3 ->  Priority.NORMAL;
            case S4 ->  Priority.LOW;
        };

    }

}

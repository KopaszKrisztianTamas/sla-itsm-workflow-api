package dev.kopasz.sla_itsm_api.api.mapper;

import dev.kopasz.sla_itsm_api.api.dto.request.TicketCreateRequestDTO;
import dev.kopasz.sla_itsm_api.domain.model.Priority;
import dev.kopasz.sla_itsm_api.domain.model.Severity;
import dev.kopasz.sla_itsm_api.domain.model.Ticket;
import dev.kopasz.sla_itsm_api.domain.model.TicketStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        imports = {UUID.class, LocalDateTime.class, TicketStatus.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE // due to id, status, deadline
)
public interface TicketMapper {

    @Mapping(source = "severity", target = "priority")
    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "status", expression = "java(TicketStatus.OPEN)")
    Ticket TicketCreateRequestDTOToTicket(TicketCreateRequestDTO dto);

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

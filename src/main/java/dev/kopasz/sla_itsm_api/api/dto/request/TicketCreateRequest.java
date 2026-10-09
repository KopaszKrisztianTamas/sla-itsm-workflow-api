package dev.kopasz.sla_itsm_api.api.dto.request;

import dev.kopasz.sla_itsm_api.domain.model.Priority;
import dev.kopasz.sla_itsm_api.domain.model.Severity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Input payload of API POST request
 */
public record TicketCreateRequest(

    @NotNull(message = "A cím nem lehet üres")
    @NotBlank
    @Size(max = 100)
    String title,

    @NotBlank
    String description,

    @NotNull(message = "A severity megadása kötelező")
    Severity severity,

    List<String> affectedServices

){

    public TicketCreateRequest {
        if (affectedServices == null) {
            affectedServices = new ArrayList<>();
        }
        affectedServices=List.copyOf(affectedServices);
    }

}
package dev.kopasz.sla_itsm_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Input payload of API POST request
 */
public record TicketCreateRequest(

    @NotNull
    @NotBlank
    @Size(max = 100)
    String title,

    @NotBlank
    String description,

    @NotNull
    Priority priority,

    List<String> affectedServices

){

    public TicketCreateRequest {
        if (affectedServices == null) {
            affectedServices = new ArrayList<>();
        }
        affectedServices=List.copyOf(affectedServices);
    }

}
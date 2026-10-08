package dev.kopasz.sla_itsm_api.api.controller;

import dev.kopasz.sla_itsm_api.api.dto.response.SlaResponse;
import dev.kopasz.sla_itsm_api.api.dto.request.TicketCreateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    @PostMapping
    public ResponseEntity<SlaResponse> createTicket(@Valid @RequestBody TicketCreateRequest request){

        // TODO business logic

        SlaResponse mockResponse = new SlaResponse(
                "TKT-1001",
                LocalDateTime.now().plusHours(4),
                false
        );
        return ResponseEntity.ok(mockResponse);

    }



}

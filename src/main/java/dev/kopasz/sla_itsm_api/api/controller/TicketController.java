package dev.kopasz.sla_itsm_api.api.controller;

import dev.kopasz.sla_itsm_api.api.dto.response.SlaResponse;
import dev.kopasz.sla_itsm_api.api.dto.request.TicketCreateRequestDTO;
import dev.kopasz.sla_itsm_api.service.SlaCalculationService;
import dev.kopasz.sla_itsm_api.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<SlaResponse> createTicket(@Valid @RequestBody TicketCreateRequestDTO request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ticketService.createTicket(request));

    }

}

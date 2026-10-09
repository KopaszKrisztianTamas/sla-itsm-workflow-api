package dev.kopasz.sla_itsm_api.api.controller;

import dev.kopasz.sla_itsm_api.api.dto.response.SlaResponse;
import dev.kopasz.sla_itsm_api.api.dto.request.TicketCreateRequest;
import dev.kopasz.sla_itsm_api.service.SlaCalculationService;
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

    private final SlaCalculationService slaCalculationService;

    public TicketController(SlaCalculationService slaCalculationService) {
        this.slaCalculationService = slaCalculationService;
    }

    @PostMapping
    public ResponseEntity<SlaResponse> createTicket(@Valid @RequestBody TicketCreateRequest request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(slaCalculationService.processTicketCreation(request));

    }

}

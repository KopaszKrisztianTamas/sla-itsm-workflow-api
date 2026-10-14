package dev.kopasz.sla_itsm_api.service;

import dev.kopasz.sla_itsm_api.api.dto.request.TicketCreateRequestDTO;
import dev.kopasz.sla_itsm_api.api.dto.response.SlaResponse;
import dev.kopasz.sla_itsm_api.domain.model.Severity;
import dev.kopasz.sla_itsm_api.repository.TicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest()
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class TicketServiceRollbackTest {


    @Autowired
    TicketService ticketService;

    @Autowired
    TicketRepository ticketRepository;

    @MockitoSpyBean
    SlaCalculationService mockSlaCalculationService;

    @Test
    public void testCommit() {

        ticketRepository.deleteAll();

        TicketCreateRequestDTO dto = new TicketCreateRequestDTO(
                "Rollback",
                "Test Ticket for Rollback",
                Severity.S1,
                List.of("Service 1","Service 2")
        );

        SlaResponse response = ticketService.createTicket(dto);

        Assertions.assertEquals(1,ticketRepository.findAll().size());
        Assertions.assertTrue(
                ticketRepository.findById(response.ticketId()).orElseGet(null).getId()
                        .equals(response.ticketId()));

    }

    @Test
    public void testRollback() {

        ticketRepository.deleteAll();

        TicketCreateRequestDTO dto = new TicketCreateRequestDTO(
                "Rollback",
                "Test Ticket for Rollback",
                Severity.S1,
                List.of("Service 1","Service 2")
        );

        when(mockSlaCalculationService.processTicketCreation(dto)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () ->  ticketService.createTicket(dto));

        Assertions.assertEquals(0,ticketRepository.findAll().size());

    }

}

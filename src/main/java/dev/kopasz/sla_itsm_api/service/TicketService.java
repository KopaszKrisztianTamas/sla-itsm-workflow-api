package dev.kopasz.sla_itsm_api.service;

import dev.kopasz.sla_itsm_api.api.dto.request.TicketCreateRequestDTO;
import dev.kopasz.sla_itsm_api.api.dto.response.SlaResponse;
import dev.kopasz.sla_itsm_api.domain.model.Ticket;
import dev.kopasz.sla_itsm_api.entity.TicketEntity;
import dev.kopasz.sla_itsm_api.mapper.TicketMapper;
import dev.kopasz.sla_itsm_api.repository.TicketRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional(rollbackFor =  Exception.class)
public class TicketService {

    private TicketRepository ticketRepository;

    private SlaCalculationService slaCalculationService;

    private TicketMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, SlaCalculationService slaCalculationService, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.slaCalculationService = slaCalculationService;
        this.ticketMapper = ticketMapper;
    }


    public SlaResponse createTicket(TicketCreateRequestDTO ticketCreateRequestDTO) {

        Ticket ticket = slaCalculationService.processTicketCreation(ticketCreateRequestDTO);

        TicketEntity entity = ticketMapper.ticketToEntity(ticket);

        ticketRepository.save(entity);
        log.info("Created ticket with id {}", entity.getId());

        return new SlaResponse(entity.getId(), entity.getDeadline(), false);
    }

}

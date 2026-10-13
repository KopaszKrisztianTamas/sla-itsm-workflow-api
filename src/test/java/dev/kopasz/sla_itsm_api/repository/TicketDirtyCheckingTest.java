package dev.kopasz.sla_itsm_api.repository;

import dev.kopasz.sla_itsm_api.domain.model.Priority;
import dev.kopasz.sla_itsm_api.domain.model.TicketStatus;
import dev.kopasz.sla_itsm_api.entity.TicketEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TicketDirtyCheckingTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    EntityManager entityManager;

    private TicketEntity TicketCreate(){

        return transactionTemplate.execute(status -> {
            String uuid = UUID.randomUUID().toString();
            TicketEntity ticketEntity = TicketEntity.builder()
                    .id(uuid)
                    .title("title")
                    .description("description")
                    .priority(Priority.NORMAL)
                    .status(TicketStatus.OPEN)
                    .createdAt(LocalDateTime.now())
                    .affectedServices(Set.of("Printing", "File Sharing"))
                    .deadline(LocalDateTime.now().plusHours(24))
                    .build();
            return ticketRepository.save(ticketEntity);
        });

    }

    private void TicketPromoteToOpen(TicketEntity ticketEntity){
        transactionTemplate.executeWithoutResult(status -> {
            ticketEntity.setStatus(TicketStatus.OPEN);
            System.out.println("No explicit .save(ticketEntity) - handing over to dirtyCache checking");
            entityManager.flush(); // commit SQL UPDATE
            entityManager.clear(); // empty L1 cache-t, dropping proxies
            System.out.println("EntityManager is cleared");
        });
    }


    @Test
    public void TicketDirtyChecking(){

        TicketEntity ticketEntityCreated = TicketCreate();
        Assertions.assertNotNull(ticketEntityCreated);
        Assertions.assertNotNull(ticketEntityCreated.getId());
        System.out.println("Ticket created: " + ticketEntityCreated.getId());

        List<TicketEntity> ticketEntities = ticketRepository.findAll();
        Assertions.assertNotNull(ticketEntities);
        Assertions.assertNotNull(ticketEntities.getFirst().getId());
        Assertions.assertEquals(2,ticketEntities.getFirst().getAffectedServices().size());
        System.out.println("Ticket's affectedServices: " + ticketEntities.getFirst().getAffectedServices().toString());

        TicketPromoteToOpen(ticketEntities.getFirst());

        TicketEntity ticketEntityUpdated = ticketRepository.findById(ticketEntities.getFirst().getId()).orElseThrow();
        Assertions.assertNotNull(ticketEntityUpdated);
        Assertions.assertEquals(TicketStatus.OPEN, ticketEntityUpdated.getStatus());
        System.out.println("Ticket promoted to OPEN: " + ticketEntityUpdated.getStatus().toString());

    }
}


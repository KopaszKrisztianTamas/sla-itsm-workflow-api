package dev.kopasz.sla_itsm_api.repository;

import dev.kopasz.sla_itsm_api.entity.TicketEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity,String > {

    @EntityGraph(attributePaths = {"affectedServices"} )
    List<TicketEntity> findAll();
}

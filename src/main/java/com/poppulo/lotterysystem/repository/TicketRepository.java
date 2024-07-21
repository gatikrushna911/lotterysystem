package com.poppulo.lotterysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poppulo.lotterysystem.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}


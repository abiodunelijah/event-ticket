package com.coder2client.repositories;

import com.coder2client.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

 int countByTicketTypeId(UUID ticketTypeId);
 Page<Ticket> findByPurchaserId(UUID purchaserId, Pageable pageable);
 Optional<Ticket> findByIdAndPurchaserId(UUID id, UUID purchaserId);
}

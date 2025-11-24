package com.coder2client.services;

import com.coder2client.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TicketService {
    Page<Ticket> listTicketForUser(UUID ticketTypeId, Pageable pageable);
    Optional<Ticket> getTicketForUser(UUID userId, UUID ticketTypeId);
}

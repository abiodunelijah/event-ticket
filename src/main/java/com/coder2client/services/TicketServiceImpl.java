package com.coder2client.services;

import com.coder2client.entity.Ticket;
import com.coder2client.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;


    @Override
    public Page<Ticket> listTicketForUser(UUID ticketTypeId, Pageable pageable) {
        return ticketRepository.findByPurchaserId(ticketTypeId, pageable);
    }

    @Override
    public Optional<Ticket> getTicketForUser(UUID userId, UUID ticketTypeId) {
        return ticketRepository.findByIdAndPurchaserId(userId, ticketTypeId);
    }
}

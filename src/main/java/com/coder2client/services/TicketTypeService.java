package com.coder2client.services;

import com.coder2client.entity.Ticket;

import java.util.UUID;

public interface TicketTypeService {

    Ticket purchaseTicket(UUID uuid, UUID ticketTypeId);
}

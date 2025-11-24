package com.coder2client.services;

import com.coder2client.entity.Ticket;
import com.coder2client.entity.TicketType;
import com.coder2client.entity.User;
import com.coder2client.enums.TicketStatusEnum;
import com.coder2client.exceptions.TicketTypeNotFoundException;
import com.coder2client.exceptions.TicketsSoldOutException;
import com.coder2client.exceptions.UserNotFoundException;
import com.coder2client.repositories.TicketRepository;
import com.coder2client.repositories.TicketTypeRepository;
import com.coder2client.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {


    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID uuid, UUID ticketTypeId) {
        User user = userRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", uuid)));
        TicketType ticketType = ticketTypeRepository.findByIdWithLock(uuid).orElseThrow(() -> new TicketTypeNotFoundException(String.format("Ticket type with id %s not found", uuid)));
        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if (purchasedTickets + 1 > totalAvailable){
            throw new TicketsSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setTicketType(ticketType);
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);

        qrCodeService.generateQrCode(savedTicket);
        return ticketRepository.save(ticket);
    }
}

package com.coder2client.services;

import com.coder2client.dtos.CreateEventRequest;
import com.coder2client.entity.Event;
import com.coder2client.entity.TicketType;
import com.coder2client.entity.User;
import com.coder2client.exceptions.UserNotFoundException;
import com.coder2client.repositories.EventRepository;
import com.coder2client.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository  userRepository;
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest createEventRequest) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with ID '%s' not found", organizerId)));

        List<TicketType> ticketTypesToCreate = createEventRequest.getTicketTypes().stream()
                .map(ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.getName());
                    ticketTypeToCreate.setPrice(ticketType.getPrice());
                    ticketTypeToCreate.setDescription(ticketType.getDescription());
                    return ticketTypeToCreate;
                }).toList();


        Event eventToCreate = getEventToCreate(createEventRequest, organizer, ticketTypesToCreate);

        return eventRepository.save(eventToCreate);
    }

    private static Event getEventToCreate(CreateEventRequest createEventRequest, User organizer, List<TicketType> ticketTypesToCreate) {
        Event eventToCreate = new Event();
        eventToCreate.setName(createEventRequest.getName());
        eventToCreate.setStart(createEventRequest.getStart());
        eventToCreate.setEnd(createEventRequest.getEnd());
        eventToCreate.setVenue(createEventRequest.getVenue());
        eventToCreate.setSalesStart(createEventRequest.getSalesStart());
        eventToCreate.setSalesEnd(createEventRequest.getSalesEnd());
        eventToCreate.setStatus(createEventRequest.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);
        return eventToCreate;
    }
}

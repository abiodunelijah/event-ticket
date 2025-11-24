package com.coder2client.services;

import com.coder2client.dtos.CreateEventRequest;
import com.coder2client.dtos.UpdateEventRequest;
import com.coder2client.dtos.UpdateTicketTypeRequest;
import com.coder2client.entity.Event;
import com.coder2client.entity.TicketType;
import com.coder2client.entity.User;
import com.coder2client.enums.EventStatusEnum;
import com.coder2client.exceptions.EventNotFoundException;
import com.coder2client.exceptions.EventUpdateException;
import com.coder2client.exceptions.TicketTypeNotFoundException;
import com.coder2client.exceptions.UserNotFoundException;
import com.coder2client.repositories.EventRepository;
import com.coder2client.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository  userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Event createEvent(UUID organizerId, CreateEventRequest createEventRequest) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with ID '%s' not found", organizerId)));

        Event eventToCreate = new Event();

        List<TicketType> ticketTypesToCreate = createEventRequest.getTicketTypes().stream()
                .map(ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.getName());
                    ticketTypeToCreate.setPrice(ticketType.getPrice());
                    ticketTypeToCreate.setDescription(ticketType.getDescription());
                    ticketTypeToCreate.setEvent(eventToCreate);
                    return ticketTypeToCreate;
                }).toList();


        eventToCreate.setName(createEventRequest.getName());
        eventToCreate.setStart(createEventRequest.getStart());
        eventToCreate.setEnd(createEventRequest.getEnd());
        eventToCreate.setVenue(createEventRequest.getVenue());
        eventToCreate.setSalesStart(createEventRequest.getSalesStart());
        eventToCreate.setSalesEnd(createEventRequest.getSalesEnd());
        eventToCreate.setStatus(createEventRequest.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);
    }

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
       return eventRepository.findByOrganizerId(organizerId, pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID id) {
        return eventRepository.findByIdAndOrganizerId(id, organizerId);
    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest updateEventRequest) {

        if (updateEventRequest.getId() == null){
             throw new EventUpdateException("Event ID cannot be null");
        }

        if (!id.equals(updateEventRequest.getId())){
            throw new EventUpdateException("Cannot update the ID of an Event.");
        }

        Event existingEvent = eventRepository.findByIdAndOrganizerId(id, organizerId)
                .orElseThrow(() -> new EventNotFoundException(String.format("Event with '%s' does not exist", id)));

        existingEvent.setName(updateEventRequest.getName());
        existingEvent.setStart(updateEventRequest.getStart());
        existingEvent.setEnd(updateEventRequest.getEnd());
        existingEvent.setVenue(updateEventRequest.getVenue());
        existingEvent.setSalesStart(updateEventRequest.getSalesStart());
        existingEvent.setSalesEnd(updateEventRequest.getSalesEnd());
        existingEvent.setStatus(updateEventRequest.getStatus());

        Set<UUID> requestTicketTypeIds = updateEventRequest.getTicketTypes()
                .stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        existingEvent.getTicketTypes()
                .removeIf(existingTicketType -> !requestTicketTypeIds.contains(existingTicketType.getId()));

        Map<UUID, TicketType> existingTicketTypeIndex = existingEvent.getTicketTypes().stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));

        for (UpdateTicketTypeRequest ticketTypeRequest : updateEventRequest.getTicketTypes()){
            if (ticketTypeRequest.getId() == null){
                //create
                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketTypeRequest.getName());
                ticketTypeToCreate.setPrice(ticketTypeRequest.getPrice());
                ticketTypeToCreate.setDescription(ticketTypeRequest.getDescription());
                ticketTypeToCreate.setEvent(existingEvent);
                existingEvent.getTicketTypes().add(ticketTypeToCreate);

            }else if (existingTicketTypeIndex.containsKey(ticketTypeRequest.getId())){
                //update
                TicketType existinTicketType = existingTicketTypeIndex.get(ticketTypeRequest.getId());
                existinTicketType.setName(ticketTypeRequest.getName());
                existinTicketType.setPrice(ticketTypeRequest.getPrice());
                existinTicketType.setDescription(ticketTypeRequest.getDescription());
                existinTicketType.setTotalAvailable(ticketTypeRequest.getTotalAvailable());

            }else {
                throw new TicketTypeNotFoundException(String.format("Ticket Type with ID '%s' does not exist.", ticketTypeRequest.getId()));
            }
        }

        return eventRepository.save(existingEvent);
    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID organizerId, UUID id) {

        getEventForOrganizer(organizerId, id).ifPresent(eventRepository::delete);

    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return eventRepository.searchEvents(query, pageable);
    }

    @Override
    public Optional<Event> getPublishedEvent(UUID uuid) {
        return eventRepository.findByIdAndStatus(uuid, EventStatusEnum.PUBLISHED);
    }

}

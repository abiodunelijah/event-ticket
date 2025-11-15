package com.coder2client.services;

import com.coder2client.dtos.CreateEventRequest;
import com.coder2client.entity.Event;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest createEventRequest);

}

package com.example.demo.service;

import com.example.demo.entity.Event;

import java.util.List;

public interface EventService {
    Event createEvent(Event event, long organizerId);
    List<Event> getAllPublicEvents();
    List<Event> getEventsByOrganizer(long organizerId);
    Event getEvent(long eventId);
    String deleteEvent(long eventId, long organizerId);
}


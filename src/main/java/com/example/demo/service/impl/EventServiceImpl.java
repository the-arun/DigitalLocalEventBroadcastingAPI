package com.example.demo.service.impl;

import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Event createEvent(Event event, long organizerId) {
        Optional<User> organizerOpt = userRepository.findById(organizerId);
        if (organizerOpt.isEmpty()) {
            throw new RuntimeException("Organizer not found");
        }
        event.setOrganizer(organizerOpt.get());
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllPublicEvents() {
        return eventRepository.findByIsPublicTrue();
    }

    @Override
    public List<Event> getEventsByOrganizer(long organizerId) {
        return eventRepository.findByOrganizerId(organizerId);
    }

    @Override
    public Event getEvent(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public String deleteEvent(long eventId, long organizerId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty()) {
            return "Event not found";
        }
        Event event = eventOpt.get();
        if (!event.getOrganizer().getId().equals(organizerId)) {
            return "Event not found / not authorized";
        }
        eventRepository.delete(event);
        return "Event deleted";
    }
}


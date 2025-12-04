package com.example.demo.service.impl;

import com.example.demo.entity.Broadcast;
import com.example.demo.entity.Event;
import com.example.demo.entity.NotificationLog;
import com.example.demo.entity.Subscriber;
import com.example.demo.repository.BroadcastRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.NotificationLogRepository;
import com.example.demo.service.BroadcastService;
import com.example.demo.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BroadcastServiceImpl implements BroadcastService {

    @Autowired
    private BroadcastRepository broadcastRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private NotificationLogRepository notificationLogRepository;

    @Override
    public Broadcast scheduleBroadcast(long eventId, String channel, LocalDateTime scheduledOn) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty()) {
            throw new RuntimeException("Event not found");
        }
        Event event = eventOpt.get();
        
        String message = "Event: " + event.getTitle() + " - " + 
                        (event.getDescription() != null && event.getDescription().length() > 100 
                            ? event.getDescription().substring(0, 100) + "..." 
                            : event.getDescription());
        
        Broadcast broadcast = new Broadcast();
        broadcast.setEvent(event);
        broadcast.setChannel(channel);
        broadcast.setMessage(message);
        broadcast.setScheduledOn(scheduledOn);
        broadcast.setStatus("PENDING");
        broadcast.setRecipientsCount(0);
        
        return broadcastRepository.save(broadcast);
    }

    @Override
    @Transactional
    public Broadcast runBroadcast(long broadcastId) {
        Optional<Broadcast> broadcastOpt = broadcastRepository.findById(broadcastId);
        if (broadcastOpt.isEmpty()) {
            throw new RuntimeException("Broadcast not found");
        }
        
        Broadcast broadcast = broadcastOpt.get();
        Event event = broadcast.getEvent();
        
        // Find matching subscribers
        List<Subscriber> matchingSubscribers = subscriberService.findMatchingSubscribers(
            event.getCategory(), 
            event.getLocation()
        );
        
        int recipientsCount = matchingSubscribers.size();
        broadcast.setRecipientsCount(recipientsCount);
        
        if (recipientsCount == 0) {
            broadcast.setStatus("FAILED");
            broadcast.setMessage("No matching subscribers");
        } else {
            broadcast.setStatus("SENT");
            broadcast.setScheduledOn(LocalDateTime.now());
            
            // Create notification logs for each recipient
            for (Subscriber subscriber : matchingSubscribers) {
                NotificationLog log = new NotificationLog();
                log.setBroadcast(broadcast);
                log.setRecipientEmail(subscriber.getEmail());
                log.setStatus("SENT");
                notificationLogRepository.save(log);
            }
        }
        
        return broadcastRepository.save(broadcast);
    }

    @Override
    public List<Broadcast> getBroadcastsByEvent(long eventId) {
        return broadcastRepository.findByEventId(eventId);
    }
}


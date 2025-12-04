package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Events", description = "Event management endpoints")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/public")
    @Operation(summary = "Get all public events", description = "Retrieve all public events (no authentication required)")
    public ResponseEntity<List<Event>> getAllPublicEvents() {
        return ResponseEntity.ok(eventService.getAllPublicEvents());
    }

    @PostMapping("/create/{organizerId}")
    @Operation(summary = "Create a new event", description = "Create a new event by an organizer", 
               security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> createEvent(@PathVariable long organizerId, @RequestBody Event event) {
        try {
            Event createdEvent = eventService.createEvent(event, organizerId);
            return ResponseEntity.ok(createdEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create event: " + e.getMessage());
        }
    }

    @GetMapping("/organizer/{organizerId}")
    @Operation(summary = "Get events by organizer", description = "Retrieve all events created by a specific organizer",
               security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Event>> getEventsByOrganizer(@PathVariable long organizerId) {
        return ResponseEntity.ok(eventService.getEventsByOrganizer(organizerId));
    }

    @GetMapping("/{eventId}")
    @Operation(summary = "Get event by ID", description = "Retrieve event details by event ID",
               security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> getEvent(@PathVariable long eventId) {
        try {
            Event event = eventService.getEvent(eventId);
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Event not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/{eventId}/organizer/{organizerId}")
    @Operation(summary = "Delete an event", description = "Delete an event by ID (only by the organizer who created it)",
               security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> deleteEvent(@PathVariable long eventId, @PathVariable long organizerId) {
        String result = eventService.deleteEvent(eventId, organizerId);
        if (result.equals("Event deleted")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}


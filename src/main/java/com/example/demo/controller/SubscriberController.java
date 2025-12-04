package com.example.demo.controller;

import com.example.demo.entity.Subscriber;
import com.example.demo.service.SubscriberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscribers")
@Tag(name = "Subscribers", description = "Subscriber management endpoints")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @PostMapping("/register")
    @Operation(summary = "Register a new subscriber", description = "Register a new subscriber with preferences (no authentication required)")
    public ResponseEntity<?> registerSubscriber(@RequestBody Subscriber subscriber) {
        try {
            Subscriber savedSubscriber = subscriberService.registerSubscriber(subscriber);
            return ResponseEntity.ok(savedSubscriber);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to register subscriber: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all subscribers", description = "Retrieve all registered subscribers (organizer/admin only)",
               security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Subscriber>> getAllSubscribers() {
        return ResponseEntity.ok(subscriberService.getAllSubscribers());
    }

    @GetMapping("/match")
    @Operation(summary = "Find matching subscribers", description = "Find subscribers matching category and/or location",
               security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Subscriber>> findMatchingSubscribers(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location) {
        
        if (category == null) category = "";
        if (location == null) location = "";
        
        List<Subscriber> matching = subscriberService.findMatchingSubscribers(category, location);
        return ResponseEntity.ok(matching);
    }
}


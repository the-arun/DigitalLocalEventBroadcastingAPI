package com.example.demo.controller;

import com.example.demo.dto.BroadcastScheduleRequest;
import com.example.demo.entity.Broadcast;
import com.example.demo.entity.NotificationLog;
import com.example.demo.repository.NotificationLogRepository;
import com.example.demo.service.BroadcastService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/broadcasts")
@Tag(name = "Broadcasts", description = "Broadcast management endpoints")
public class BroadcastController {

    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private NotificationLogRepository notificationLogRepository;

    @PostMapping("/schedule/{eventId}")
    @Operation(summary = "Schedule a broadcast", description = "Schedule a broadcast for an event with channel and scheduled time",
               security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> scheduleBroadcast(
            @PathVariable long eventId,
            @RequestBody BroadcastScheduleRequest request) {
        try {
            Broadcast broadcast = broadcastService.scheduleBroadcast(
                eventId, 
                request.getChannel(), 
                request.getScheduledOn()
            );
            return ResponseEntity.ok(broadcast);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to schedule broadcast: " + e.getMessage());
        }
    }

    @PostMapping("/run/{broadcastId}")
    @Operation(summary = "Run a broadcast", description = "Execute a scheduled broadcast and send notifications to matching subscribers",
               security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> runBroadcast(@PathVariable long broadcastId) {
        try {
            Broadcast broadcast = broadcastService.runBroadcast(broadcastId);
            return ResponseEntity.ok(broadcast);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to run broadcast: " + e.getMessage());
        }
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get broadcasts by event", description = "Retrieve broadcast history for a specific event",
               security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Broadcast>> getBroadcastsByEvent(@PathVariable long eventId) {
        return ResponseEntity.ok(broadcastService.getBroadcastsByEvent(eventId));
    }

    @GetMapping("/{broadcastId}/logs")
    @Operation(summary = "Get notification logs", description = "Retrieve notification logs for a specific broadcast",
               security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<NotificationLog>> getNotificationLogs(@PathVariable long broadcastId) {
        List<NotificationLog> logs = notificationLogRepository.findByBroadcastId(broadcastId);
        return ResponseEntity.ok(logs);
    }
}


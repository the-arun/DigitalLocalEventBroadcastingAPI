package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "broadcasts")
public class Broadcast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private String channel; // "EMAIL", "SMS", "PUSH"

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private LocalDateTime scheduledOn;

    @Column(nullable = false)
    private String status; // "PENDING", "SENT", "FAILED"

    @Column(nullable = false)
    private Integer recipientsCount = 0;

    @OneToMany(mappedBy = "broadcast", cascade = CascadeType.ALL)
    private List<NotificationLog> notificationLogs;

    // Constructors
    public Broadcast() {
    }

    public Broadcast(Event event, String channel, String message, LocalDateTime scheduledOn, String status) {
        this.event = event;
        this.channel = channel;
        this.message = message;
        this.scheduledOn = scheduledOn;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getScheduledOn() {
        return scheduledOn;
    }

    public void setScheduledOn(LocalDateTime scheduledOn) {
        this.scheduledOn = scheduledOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRecipientsCount() {
        return recipientsCount;
    }

    public void setRecipientsCount(Integer recipientsCount) {
        this.recipientsCount = recipientsCount;
    }

    public List<NotificationLog> getNotificationLogs() {
        return notificationLogs;
    }

    public void setNotificationLogs(List<NotificationLog> notificationLogs) {
        this.notificationLogs = notificationLogs;
    }
}


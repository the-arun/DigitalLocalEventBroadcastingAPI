package com.example.demo.dto;

import java.time.LocalDateTime;

public class BroadcastScheduleRequest {
    private String channel;
    private LocalDateTime scheduledOn;

    public BroadcastScheduleRequest() {
    }

    public BroadcastScheduleRequest(String channel, LocalDateTime scheduledOn) {
        this.channel = channel;
        this.scheduledOn = scheduledOn;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public LocalDateTime getScheduledOn() {
        return scheduledOn;
    }

    public void setScheduledOn(LocalDateTime scheduledOn) {
        this.scheduledOn = scheduledOn;
    }
}


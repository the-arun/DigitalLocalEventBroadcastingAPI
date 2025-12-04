package com.example.demo.service;

import com.example.demo.entity.Broadcast;

import java.time.LocalDateTime;
import java.util.List;

public interface BroadcastService {
    Broadcast scheduleBroadcast(long eventId, String channel, LocalDateTime scheduledOn);
    Broadcast runBroadcast(long broadcastId);
    List<Broadcast> getBroadcastsByEvent(long eventId);
}


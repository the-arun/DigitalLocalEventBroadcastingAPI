package com.example.demo.service;

import com.example.demo.entity.Subscriber;

import java.util.List;

public interface SubscriberService {
    Subscriber registerSubscriber(Subscriber sub);
    List<Subscriber> getAllSubscribers();
    List<Subscriber> findMatchingSubscribers(String category, String location);
}


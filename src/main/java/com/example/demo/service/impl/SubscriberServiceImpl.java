package com.example.demo.service.impl;

import com.example.demo.entity.Subscriber;
import com.example.demo.repository.SubscriberRepository;
import com.example.demo.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Override
    public Subscriber registerSubscriber(Subscriber sub) {
        return subscriberRepository.save(sub);
    }

    @Override
    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    @Override
    public List<Subscriber> findMatchingSubscribers(String category, String location) {
        return subscriberRepository.findMatchingSubscribers(category, location);
    }
}


package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscribers")
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String preferredCategories; // comma-separated: "Music,Meetup,Sports"

    @Column(nullable = false)
    private String preferredLocations; // comma-separated: "Downtown,North"

    @Column(nullable = false)
    private LocalDateTime subscribedOn;

    @PrePersist
    protected void onCreate() {
        subscribedOn = LocalDateTime.now();
    }

    // Constructors
    public Subscriber() {
    }

    public Subscriber(String name, String email, String preferredCategories, String preferredLocations) {
        this.name = name;
        this.email = email;
        this.preferredCategories = preferredCategories;
        this.preferredLocations = preferredLocations;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferredCategories() {
        return preferredCategories;
    }

    public void setPreferredCategories(String preferredCategories) {
        this.preferredCategories = preferredCategories;
    }

    public String getPreferredLocations() {
        return preferredLocations;
    }

    public void setPreferredLocations(String preferredLocations) {
        this.preferredLocations = preferredLocations;
    }

    public LocalDateTime getSubscribedOn() {
        return subscribedOn;
    }

    public void setSubscribedOn(LocalDateTime subscribedOn) {
        this.subscribedOn = subscribedOn;
    }
}


package com.example.demo.repository;

import com.example.demo.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    boolean existsByEmail(String email);
    
    @Query("SELECT s FROM Subscriber s WHERE " +
           "LOWER(s.preferredCategories) LIKE LOWER(CONCAT('%', :category, '%')) OR " +
           "LOWER(s.preferredLocations) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Subscriber> findMatchingSubscribers(@Param("category") String category, @Param("location") String location);
}


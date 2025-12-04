package com.example.demo.repository;

import com.example.demo.entity.Broadcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BroadcastRepository extends JpaRepository<Broadcast, Long> {
    List<Broadcast> findByEventId(Long eventId);
}


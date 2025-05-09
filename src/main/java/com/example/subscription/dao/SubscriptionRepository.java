package com.example.subscription.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.subscription.models.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    
    @Query("SELECT s.subscriptionType, COUNT(s) FROM Subscription s WHERE s.active = true GROUP BY s.subscriptionType ORDER BY COUNT(s) DESC")
    List<Object[]> findTopPopular(Pageable pageable);
}

package com.example.subscription.controllers;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.subscription.dto.TopSubscription;
import com.example.subscription.services.SubscriptionService;

import lombok.RequiredArgsConstructor;

@RestController("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/top")
    public ResponseEntity<List<TopSubscription>> getTopSubscriptions() {
        Pageable pageable = PageRequest.of(0, 3);
        List<TopSubscription> result = subscriptionService.findTopPopular(pageable);
        return ResponseEntity.ok(result);
    }
    
}

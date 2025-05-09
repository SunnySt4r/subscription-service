package com.example.subscription.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.subscription.dao.SubscriptionRepository;
import com.example.subscription.dto.SubscriptionDto;
import com.example.subscription.dto.TopSubscription;
import com.example.subscription.exceptions.SubscriptionNotFoundException;
import com.example.subscription.exceptions.UserNotFoundException;
import com.example.subscription.models.Subscription;
import com.example.subscription.models.SubscriptionEnum;
import com.example.subscription.models.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    
    private final UserService userService;
    private final SubscriptionRepository subscriptionRepository;

    public List<TopSubscription> findTopPopular(Pageable pageable) {
        return subscriptionRepository.findTopPopular(pageable)
                .stream()
                .map(r -> new TopSubscription((String) r[0], (long) r[1]))
                .collect(Collectors.toList());
    }

    public boolean addSubscription(UUID id, SubscriptionEnum subscriptionType) {
        User user = userService.getUserById(id);
        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(subscriptionType);
        subscription.setUser(user);
        subscription.setActive(true);
        subscriptionRepository.save(subscription);
        return true;
    }

    public List<SubscriptionDto> getUserSubscriptions(UUID id) {
        User user = userService.getUserById(id);
        return user.getSubscriptions() == null ? List.of() :
            user.getSubscriptions()
                .stream()
                .map(s -> new SubscriptionDto(s.getSubscriptionType().getValue(), s.isActive()))
                .collect(Collectors.toList());
    }

    public boolean deleteSubscription(UUID userId, UUID subId) {
        Subscription subscription = subscriptionRepository.findById(subId)
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found with id: " + subId));

        if (!subscription.getUser().getId().equals(userId)) {
            throw new UserNotFoundException("Subscription does not belong to the user with id: " + userId);
        }

        subscriptionRepository.delete(subscription);
        return true;
    }
}

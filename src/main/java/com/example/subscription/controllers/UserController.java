package com.example.subscription.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.subscription.dto.SubscriptionDto;
import com.example.subscription.dto.SuccessDto;
import com.example.subscription.dto.UserDto;
import com.example.subscription.mappers.UserMapper;
import com.example.subscription.models.SubscriptionEnum;
import com.example.subscription.services.SubscriptionService;
import com.example.subscription.services.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController("/users")
@RequiredArgsConstructor
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final SubscriptionService subscriptionService;

    private final UserMapper userMapper;

    @PostMapping()
    public ResponseEntity<SuccessDto> createUser(@RequestBody UserDto userDto) {
        logger.info("Received request to create user with data: {}", userDto);
        boolean result = userService.createUser(userMapper.toUser(userDto));
        logger.info("User creation result: {}", result);
        return ResponseEntity.ok(new SuccessDto(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable UUID id) {
        logger.info("Received request to get user with id: {}", id);
        UserDto userDto = userMapper.toUserDto(userService.getUserById(id));
        logger.info("Returning user details for id: {}", id);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessDto> updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        logger.info("Received request to update user with id: {} and data: {}", id, userDto);
        boolean result = userService.updateUser(id, userDto);
        logger.info("User update result for id {}: {}", id, result);
        return ResponseEntity.ok(new SuccessDto(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessDto> deleteUser(@PathVariable UUID id) {
        logger.info("Received request to delete user with id: {}", id);
        boolean result = userService.deleteUser(id);
        logger.info("User deletion result for id {}: {}", id, result);
        return ResponseEntity.ok(new SuccessDto(result));
    }

    @PostMapping("/{id}/subscriptions")
    public ResponseEntity<SuccessDto> addSubscription(@PathVariable UUID id, @RequestBody String subscriptionType) {
        logger.info("Received request to add subscription for user id: {} with type: {}", id, subscriptionType);
        boolean result = subscriptionService.addSubscription(id, SubscriptionEnum.fromValue(subscriptionType));
        logger.info("Subscription add result for user id {}: {}", id, result);
        return ResponseEntity.ok(new SuccessDto(result));
    }

    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<List<SubscriptionDto>> getUserSubscriptions(@PathVariable UUID id) {
        logger.info("Received request to fetch subscriptions for user id: {}", id);
        List<SubscriptionDto> subscriptions = subscriptionService.getUserSubscriptions(id);
        logger.info("Returning {} subscriptions for user id: {}", subscriptions.size(), id);
        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/{id}/subscriptions/{sub_id}")
    public ResponseEntity<SuccessDto> deleteSubscription(@PathVariable UUID id, @PathVariable("sub_id") UUID subId) {
        logger.info("Received request to delete subscription {} for user id: {}", subId, id);
        boolean result = subscriptionService.deleteSubscription(id, subId);
        logger.info("Subscription deletion result for sub id {} for user id {}: {}", subId, id, result);
        return ResponseEntity.ok(new SuccessDto(result));
    }
    
}

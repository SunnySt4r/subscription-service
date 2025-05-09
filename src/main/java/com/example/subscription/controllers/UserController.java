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


@RestController("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    private final UserMapper userMapper;

    @PostMapping()
    public ResponseEntity<SuccessDto> createUser(@RequestBody UserDto userDto) {
        boolean result = userService.createUser(userMapper.toUser(userDto));
        return ResponseEntity.ok(new SuccessDto(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable UUID id) {
        UserDto userDto = userMapper.toUserDto(userService.getUserById(id));
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessDto> updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        boolean result = userService.updateUser(id, userDto);
        return ResponseEntity.ok(new SuccessDto(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessDto> deleteUser(@PathVariable UUID id) {
        boolean result = userService.deleteUser(id);
        return ResponseEntity.ok(new SuccessDto(result));
    }

    @PostMapping("/{id}/subscriptions")
    public ResponseEntity<SuccessDto> addSubscription(@PathVariable UUID id, @RequestBody String subscriptionType) {
        boolean result = subscriptionService.addSubscription(id, SubscriptionEnum.fromValue(subscriptionType));
        return ResponseEntity.ok(new SuccessDto(result));
    }

    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<List<SubscriptionDto>> getUserSubscriptions(@PathVariable UUID id) {
        List<SubscriptionDto> subscriptions = subscriptionService.getUserSubscriptions(id);
        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/{id}/subscriptions/{sub_id}")
    public ResponseEntity<SuccessDto> deleteSubscription(@PathVariable UUID id, @PathVariable("sub_id") UUID subId) {
        boolean result = subscriptionService.deleteSubscription(id, subId);
        return ResponseEntity.ok(new SuccessDto(result));
    }
    

}

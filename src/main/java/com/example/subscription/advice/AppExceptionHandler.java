package com.example.subscription.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.subscription.dto.SuccessDto;
import com.example.subscription.exceptions.SubscriptionNotFoundException;
import com.example.subscription.exceptions.UserNotFoundException;
import com.example.subscription.exceptions.WrongTypeSubscritionException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(WrongTypeSubscritionException.class)
    public ResponseEntity<SuccessDto> handleSubscriptionNotFoundException(WrongTypeSubscritionException ex) {
        logger.error("Wrong type subscription exception occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new SuccessDto(false, ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<SuccessDto> handleUserNotFoundException(UserNotFoundException ex) {
        logger.error("User not found exception occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new SuccessDto(false, ex.getMessage()));
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<SuccessDto> handleSubscriptionNotFoundException(SubscriptionNotFoundException ex) {
        logger.error("Subscription not found exception occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new SuccessDto(false, ex.getMessage()));
    }
}
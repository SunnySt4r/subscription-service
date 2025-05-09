package com.example.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessDto {
    private boolean success;
    private String message;

    public SuccessDto(boolean success) {
        this.success = success;
    }
}

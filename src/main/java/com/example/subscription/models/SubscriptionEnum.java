package com.example.subscription.models;

import com.example.subscription.exceptions.WrongTypeSubscritionException;

public enum SubscriptionEnum {
    SPOTIFY("spotify"),
    APPLE_MUSIC("apple_music"),
    YOUTUBE_MUSIC("youtube_music"),
    YANDEX_MUSIC("yandex_music");

    private final String value;

    SubscriptionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SubscriptionEnum fromValue(String value) {
        for (SubscriptionEnum subscription : SubscriptionEnum.values()) {
            if (subscription.value.equals(value)) {
                return subscription;
            }
        }
        throw new WrongTypeSubscritionException("Unknown subscription type: " + value);
    }
}

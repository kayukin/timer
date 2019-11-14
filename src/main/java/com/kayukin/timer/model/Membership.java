package com.kayukin.timer.model;

import lombok.Data;

@Data
public class Membership {
    private String userId;

    private Object hourlyRate;

    private String targetId;

    private String membershipType;

    private String membershipStatus;
}

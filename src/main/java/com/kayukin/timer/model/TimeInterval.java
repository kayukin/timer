package com.kayukin.timer.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class TimeInterval {
    private LocalDateTime start;

    private LocalDateTime end;

    private Duration duration;
}
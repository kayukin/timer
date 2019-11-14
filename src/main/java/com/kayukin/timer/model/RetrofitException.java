package com.kayukin.timer.model;

public class RetrofitException extends RuntimeException {
    public RetrofitException(Exception e) {
        super(e);
    }
}

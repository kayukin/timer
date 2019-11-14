package com.kayukin.timer.service;

import lombok.extern.slf4j.Slf4j;
import org.cinnamon.ScreenSaver;
import org.freedesktop.dbus.interfaces.DBusSigHandler;

@Slf4j
public class DBusHandler implements DBusSigHandler<ScreenSaver.ActiveChanged> {
    private final ClockifyService clockifyService;

    public DBusHandler(ClockifyService clockifyService) {
        this.clockifyService = clockifyService;
    }

    @Override
    public void handle(ScreenSaver.ActiveChanged signal) {
        try {
            if (signal.getLocked()) {
                log.info("Received locked event");
                clockifyService.pauseActiveTimers();
            } else {
                log.info("Received unlocked event");
                clockifyService.resumePausedTimers();
            }
        } catch (Throwable e) {
            log.error("Unhandled exception: ", e);
        }
    }
}

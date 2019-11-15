package com.kayukin.timer.service;

import com.kayukin.timer.toggl.service.TogglService;
import lombok.extern.slf4j.Slf4j;
import org.cinnamon.ScreenSaver;
import org.freedesktop.dbus.interfaces.DBusSigHandler;

@Slf4j
public class DBusHandler implements DBusSigHandler<ScreenSaver.ActiveChanged> {
    private final TogglService togglService;

    public DBusHandler(TogglService togglService) {
        this.togglService = togglService;
    }

    @Override
    public void handle(ScreenSaver.ActiveChanged signal) {
        try {
            if (signal.getLocked()) {
                log.info("Received locked event");
                togglService.pauseActiveTimers();
            } else {
                log.info("Received unlocked event");
                togglService.resumePausedTimers();
            }
        } catch (Throwable e) {
            log.error("Unhandled exception: ", e);
        }
    }
}

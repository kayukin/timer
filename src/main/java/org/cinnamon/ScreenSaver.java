package org.cinnamon;

import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.messages.DBusSignal;

public interface ScreenSaver extends DBusInterface {
    public class ActiveChanged extends DBusSignal {
        private final Boolean locked;

        public ActiveChanged(String path, Boolean locked)
                throws DBusException {
            super(path, locked);
            this.locked = locked;
        }

        public Boolean getLocked() {
            return locked;
        }
    }
}
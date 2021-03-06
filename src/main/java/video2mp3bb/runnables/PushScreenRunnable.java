/*
 * PushScreenRunnable.java
 * 
 * Copyright 2010 Denver Coneybeare
 * 
 * This file is part of Video2MP3BB.
 *
 * Video2MP3BB is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Video2MP3BB is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Video2MP3BB.  If not, see <http://www.gnu.org/licenses/>.
 */
package video2mp3bb.runnables;

import java.lang.ref.WeakReference;

import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;

public class PushScreenRunnable implements Runnable {

    private final Screen _screen;
    private final WeakReference _appRef;

    public PushScreenRunnable(Screen screen) {
        this(screen, UiApplication.getUiApplication());
    }

    public PushScreenRunnable(Screen screen, UiApplication app) {
        if (screen == null) {
            throw new NullPointerException("screen==null");
        } else if (app == null) {
            throw new NullPointerException("app==null");
        }

        this._screen = screen;
        this._appRef = new WeakReference(app);
    }

    public void run() {
        final UiApplication app = (UiApplication) this._appRef.get();
        if (app != null) {
            app.pushScreen(this._screen);
        }
    }

}

/*
 * Video2MP3MenuItem.java
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
package video2mp3bb;

import net.rim.device.api.system.Application;
import net.rim.device.api.ui.component.Dialog;

import net.rim.blackberry.api.menuitem.ApplicationMenuItem;

public class Video2MP3MenuItem extends ApplicationMenuItem {

    public Video2MP3MenuItem() {
        super(0);
    }

    public Object run(Object context) {
        if (context instanceof String) {
            try {
                this.setUrl((String) context);
            } catch (final Throwable e) {
                Dialog.alert("ERROR: " + e);
            }
        } else {
            Dialog.alert("Unknown Object: " + context);
        }

        return null;
    }

    public boolean setUrl(String url) {
        Application appObject;
        try {
            appObject = Application.getApplication();
        } catch (final RuntimeException e) {
            appObject = null;
        }

        Video2MP3BBApplication app;
        if (appObject instanceof Video2MP3BBApplication) {
            app = (Video2MP3BBApplication) appObject;
        } else {
            final TransientData data = Main.getTransientData();
            app = data.getApplication();

            if (app == null) {
                try {
                    Main.launch();
                } catch (final Throwable e) {
                    Dialog.alert("ERROR: " + e);
                    return false;
                }

                try {
                    app = data.waitForApplication(2000L);
                } catch (final InterruptedException e) {
                    // oh well
                }
            }
        }

        if (app == null) {
            Dialog.alert("Failed to launch application");
            return false;
        }

        final Video2MP3Screen screen = app.getScreen();
        final Runnable runnable = new SetUrlRunnable(screen, url);
        app.requestForeground();
        app.invokeAndWait(runnable);
        return true;
    }

    public String toString() {
        return Video2MP3Screen.STR_DOWNLOAD;
    }

    public static class SetUrlRunnable implements Runnable {

        private final Video2MP3Screen _screen;
        private final String _url;

        public SetUrlRunnable(Video2MP3Screen screen, String url) {
            if (screen == null) {
                throw new NullPointerException("screen==null");
            }
            this._screen = screen;
            this._url = url;
        }

        public void run() {
            this._screen.getUrlField().setText(this._url);
            this._screen.getSelectButtonField().setFocus();
        }
    }
}

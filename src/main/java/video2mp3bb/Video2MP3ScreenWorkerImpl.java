/*
 * Video2MP3ScreenWorkerImpl.java
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

import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.CodeModuleManager;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;

public class Video2MP3ScreenWorkerImpl implements Video2MP3Screen.Worker {

    public void about() {
        ApplicationDescriptor descriptor;
        try {
            descriptor = Main.getDescriptor(null);
        } catch (final Throwable e) {
            return;
        }

        final String appName = descriptor.getLocalizedName();
        final int moduleHandle = descriptor.getModuleHandle();
        final int moduleIndex = descriptor.getIndex();
        final String author = CodeModuleManager.getModuleVendor(moduleHandle);
        final String version = CodeModuleManager.getModuleVersion(moduleHandle, moduleIndex);
        final String description = CodeModuleManager.getModuleDescription(moduleHandle);

        final AboutScreen screen =
            new AboutScreen(appName, version, author, description, Main.LICENSE);
        UiApplication.getUiApplication().pushScreen(screen);
    }

    public void cleanup() {
        Main.runCleanups();
        System.exit(0);
    }

    public void handleInvalidUrl(Video2MP3Screen screen, String url) {
        Dialog.alert("Invalid URL");
        final Field field = screen.getUrlField();
        field.setFocus();
    }

    public void handleUrl(String url, boolean hq) {
        final BrowserSession session = Browser.getDefaultSession();
        final String video2mp3Url = urlToVideo2MP3Url(url, hq);
        session.displayPage(video2mp3Url);
    }

    public void userSelectedUrl(Video2MP3Screen screen, String url, final boolean hq) {
        final String urlClean = cleanUrl(url);
        if (urlClean == null) {
            this.handleInvalidUrl(screen, url);
        } else {
            new Thread() {
                public void run() {
                    Video2MP3ScreenWorkerImpl.this.handleUrl(urlClean, hq);
                }
            }.start();
        }
    }

    public static String cleanUrl(String url) {
        if (url != null) {
            url = url.trim();
            if (url.length() == 0) {
                url = null;
            }
        }
        return url;
    }

    public static String urlToVideo2MP3Url(String url, boolean hq) {
        final StringBuffer sb = new StringBuffer();
        sb.append("http://www.video2mp3.net/index.php?url=");

        int index = url.indexOf("&v=");
        if (index < 0) {
            index = url.indexOf("?v=");
        }

        if (index < 0) {
            sb.append(url);
        } else {
            index += 3;
            int endIndex = url.indexOf('&', index + 1);
            if (endIndex < 0) {
                endIndex = url.length();
            }
            final String videoId = url.substring(index, endIndex);

            sb.append("http://www.youtube.com/watch?v=");
            sb.append(videoId);
        }

        if (hq) {
            sb.append("&hq=1");
        }

        final String video2mp3Url = sb.toString();
        return video2mp3Url;
    }
}

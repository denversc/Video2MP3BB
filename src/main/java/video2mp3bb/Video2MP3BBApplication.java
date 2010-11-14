/*
 * Video2MP3BBApplication.java
 * By: Denver Coneybeare
 * Nov 13, 2010
 *
 * Research In Motion Limited proprietary and confidential
 * Copyright Research In Motion Limited, 2010-2010
 */
package video2mp3bb;

import net.rim.device.api.ui.UiApplication;

public class Video2MP3BBApplication extends UiApplication {

    private final Video2MP3Screen _screen;

    public Video2MP3BBApplication() {
        final Video2MP3ScreenWorkerImpl worker = new Video2MP3ScreenWorkerImpl();
        this._screen = new Video2MP3Screen(worker);
        this.pushScreen(this._screen);
    }

    public Video2MP3Screen getScreen() {
        return this._screen;
    }
}

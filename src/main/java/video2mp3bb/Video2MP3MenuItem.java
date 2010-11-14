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

            if (app == null || !app.isAlive()) {
                try {
                    Main.launch();
                } catch (final Throwable e) {
                    Dialog.alert("ERROR: " + e);
                    return false;
                }

                for (int i = 0; i < 100 && app == null; i++) {
                    app = data.getApplication();
                }
            }
        }

        if (app == null) {
            Dialog.alert("Failed to launch application");
            return false;
        }

        final Video2MP3Screen screen = app.getScreen();
        final Runnable runnable = new SetUrlRunnable(screen, url);
        app.invokeAndWait(runnable);
        app.requestForeground();
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

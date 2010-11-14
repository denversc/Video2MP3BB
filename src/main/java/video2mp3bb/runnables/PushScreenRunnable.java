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

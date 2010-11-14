package video2mp3bb;

import java.lang.ref.WeakReference;
import java.util.Vector;

public class TransientData {

    private final Vector _cleanups;
    private WeakReference _applicationRef;
    private final Object _applicationRefMutex;

    public TransientData() {
        this._cleanups = new Vector();
        this._applicationRefMutex = new Object();
    }

    public void addCleanup(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("runnable==null");
        }
        this._cleanups.addElement(runnable);
    }

    public Video2MP3BBApplication getApplication() {
        final WeakReference ref;
        synchronized (this._applicationRefMutex) {
            ref = this._applicationRef;
        }

        if (ref != null) {
            final Video2MP3BBApplication app = (Video2MP3BBApplication) ref.get();
            if (app.isAlive()) {
                return app;
            }
        }

        return null;
    }

    public Runnable[] getCleanups(boolean removeAll) {
        synchronized (this._cleanups) {
            final Runnable[] array = new Runnable[this._cleanups.size()];
            this._cleanups.copyInto(array);
            if (removeAll) {
                this._cleanups.removeAllElements();
            }
            return array;
        }
    }

    public void setApplication(Video2MP3BBApplication application) {
        final WeakReference ref = new WeakReference(application);
        synchronized (this._applicationRefMutex) {
            this._applicationRef = ref;
            if (application != null) {
                this._applicationRefMutex.notifyAll();
            }
        }
    }

    public Video2MP3BBApplication waitForApplication(long timeout) throws InterruptedException {
        synchronized (this._applicationRefMutex) {
            Video2MP3BBApplication app = this.getApplication();
            if (app == null) {
                this._applicationRefMutex.wait(timeout);
            }
            app = this.getApplication();
            return app;
        }
    }
}

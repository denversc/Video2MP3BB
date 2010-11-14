package video2mp3bb;

import java.lang.ref.WeakReference;
import java.util.Vector;

public class TransientData {

    private final Vector _cleanups;
    private WeakReference _applicationRef;

    public TransientData() {
        this._cleanups = new Vector();
    }

    public void addCleanup(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("runnable==null");
        }
        this._cleanups.addElement(runnable);
    }

    public Video2MP3BBApplication getApplication() {
        return (Video2MP3BBApplication) this._applicationRef.get();
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
        this._applicationRef = new WeakReference(application);
    }

}

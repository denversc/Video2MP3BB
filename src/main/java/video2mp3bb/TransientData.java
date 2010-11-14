package video2mp3bb;

import java.util.Vector;

public class TransientData {

    private final Vector _cleanups;

    public TransientData() {
        this._cleanups = new Vector();
    }

    public void addCleanup(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("runnable==null");
        }
        this._cleanups.addElement(runnable);
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

}

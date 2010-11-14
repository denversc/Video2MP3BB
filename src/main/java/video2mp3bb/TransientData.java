package video2mp3bb;

import java.util.Vector;

public class TransientData {

    private final Vector _cleanups;
    
    public TransientData() {
        _cleanups = new Vector();
    }

    public void addCleanup(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("runnable==null");
        }
        _cleanups.addElement(runnable);
    }
    
    public Runnable[] getCleanups(boolean removeAll) {
        synchronized (_cleanups) {
            Runnable[] array = new Runnable[_cleanups.size()];
            _cleanups.copyInto(array);
            if (removeAll) {
                _cleanups.removeAllElements();
            }
            return array;
        }
    }
    
}

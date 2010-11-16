/*
 * TransientData.java
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

import java.lang.ref.WeakReference;
import java.util.Vector;

public class TransientData {

    private final Vector _cleanups;

    private WeakReference _applicationRef;
    private final Object _applicationRefMutex;

    private boolean _initStarted;
    private boolean _initCompleted;
    private final Object _initMutex;

    public TransientData() {
        this._cleanups = new Vector();
        this._applicationRefMutex = new Object();
        this._initMutex = new Object();
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
            final Object referent = ref.get();
            if (referent instanceof Video2MP3BBApplication) {
                final Video2MP3BBApplication app = (Video2MP3BBApplication) referent;
                if (app.isAlive()) {
                    return app;
                }
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

    public boolean isInitCompleted() {
        synchronized (this._initMutex) {
            return this._initCompleted;
        }
    }

    public boolean isInitStarted() {
        synchronized (this._initMutex) {
            return this._initStarted;
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

    public void setInitCompleted() {
        synchronized (this._initMutex) {
            this._initCompleted = true;
            this._initMutex.notifyAll();
        }
    }

    public void setInitStarted() {
        synchronized (this._initMutex) {
            this._initStarted = true;
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

    public boolean waitForInitCompleted(long timeout) throws InterruptedException {
        synchronized (this._initMutex) {
            boolean result = this.isInitCompleted();
            if (!result) {
                this._initMutex.wait(timeout);
                result = this.isInitCompleted();
            }
            return result;
        }
    }
}

package video2mp3bb.runnables;

import net.rim.device.api.ui.component.Dialog;

public class DialogAlertRunnable implements Runnable {

    private final String _message;

    public DialogAlertRunnable(String message) {
        this._message = message;
    }

    public void run() {
        Dialog.alert(this._message);
    }
}

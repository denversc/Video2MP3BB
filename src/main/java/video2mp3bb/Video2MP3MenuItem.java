package video2mp3bb;

import net.rim.device.api.ui.component.Dialog;

import net.rim.blackberry.api.menuitem.ApplicationMenuItem;

public class Video2MP3MenuItem extends ApplicationMenuItem {
    
    public Video2MP3MenuItem() {
        super(0);
    }

    public Object run(Object context) {
        if (context instanceof String) {
            try {
                Main.postSetUrlGlobalEvent((String) context);
            } catch (Throwable e) {
                Dialog.alert("ERROR: " + e);
            }
        } else {
            Dialog.alert("Unknown Object: " + context);
        }
        
        return null;
    }

    public String toString() {
        return null;
    }

}

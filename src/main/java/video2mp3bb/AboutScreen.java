/*
 * AboutScreen.java
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

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.ActiveRichTextField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BorderFactory;

public class AboutScreen extends PopupScreen {

    private boolean _closeOnTouchUp;

    public AboutScreen(Object appName, Object version, Object author, Object description,
            Object license) {
        super(new VerticalFieldManager(USE_ALL_WIDTH), DEFAULT_CLOSE | DEFAULT_MENU);

        final LabelField appNameField = new LabelField(appName);
        appNameField.setFont(appNameField.getFont().derive(Font.BOLD));
        final LabelField versionField = new LabelField("Version " + version);
        final LabelField authorField = new LabelField("By: " + author);

        final Manager titleManager = new VerticalFieldManager(USE_ALL_WIDTH | VERTICAL_SCROLL);
        titleManager.setBorder(BorderFactory.createRoundedBorder(new XYEdges(10, 10, 10, 10)));
        titleManager.add(appNameField);
        titleManager.add(versionField);
        titleManager.add(authorField);

        final StringBuffer sb = new StringBuffer();
        sb.append(description).append('\n').append(license);
        final Manager textManager = new VerticalFieldManager(VERTICAL_SCROLL);
        textManager.add(new ActiveRichTextField(sb.toString()));

        this.add(titleManager);
        this.add(new LabelField(""));
        this.add(textManager);
    }

    protected boolean keyUp(int keycode, int time) {
        if (!super.keyUp(keycode, time)) {
            this.close();
        }
        return true;
    }

    protected boolean navigationUnclick(int status, int time) {
        if (!super.navigationUnclick(status, time)) {
            this.close();
        }
        return true;
    }

    protected boolean touchEvent(TouchEvent event) {
        boolean consumed = super.touchEvent(event);
        if (!consumed) {
            switch (event.getEvent()) {
                case TouchEvent.UNCLICK:
                case TouchEvent.UP:
                    if (this._closeOnTouchUp) {
                        this.close();
                        consumed = true;
                    } else {
                        this._closeOnTouchUp = true;
                    }
                    break;
                case TouchEvent.GESTURE:
                case TouchEvent.MOVE:
                    this._closeOnTouchUp = false;
                    break;
            }
        }
        return consumed;
    }
}

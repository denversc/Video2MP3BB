/*
 * Video2MP3Screen.java
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

import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class Video2MP3Screen extends MainScreen {

    public static final String STR_DOWNLOAD;
    static {
        STR_DOWNLOAD = "Convert Video to MP3";
    }

    private final Worker _worker;
    private final EditField _urlField;
    private final CheckboxField _hqCheckboxField;
    private final ButtonField _selectButton;

    public Video2MP3Screen(Worker worker) {
        if (worker == null) {
            throw new NullPointerException("worker==null");
        }
        this._worker = worker;
        this.setTitle("Video2MP3");

        final MenuItem selectUrlMenuItem = new SelectUrlMenuItem(0x00010000, 0);
        this.addMenuItem(selectUrlMenuItem);
        this.addMenuItem(new CleanupsMenuItem(0x00010000 * 2, 0));
        this.addMenuItem(new AboutMenuItem(0x00010000 * 3, 0));

        this._urlField =
            new EditField(null, null, TextField.DEFAULT_MAXCHARS, BasicEditField.FILTER_URL);
        final Border border = BorderFactory.createRoundedBorder(new XYEdges(5, 5, 5, 5));
        this._urlField.setBorder(border);

        this._hqCheckboxField = new CheckboxField("High Quality", true);

        this._selectButton =
            new ButtonField(selectUrlMenuItem.toString(), ButtonField.CONSUME_CLICK
                | ButtonField.NEVER_DIRTY);
        this._selectButton.setRunnable(selectUrlMenuItem);

        this.add(new LabelField("Enter the URL of the video to convert to MP3:"));
        this.add(this._urlField);
        this.add(this._hqCheckboxField);
        this.add(this._selectButton);
    }

    public CheckboxField getHighQualityField() {
        return this._hqCheckboxField;
    }

    public ButtonField getSelectButtonField() {
        return this._selectButton;
    }

    public EditField getUrlField() {
        return this._urlField;
    }

    public Worker getWorker() {
        return this._worker;
    }

    protected boolean onSavePrompt() {
        return true;
    }

    public void userSelectedAbout() {
        this._worker.about();
    }

    public void userSelectedCleanup() {
        this._worker.cleanup();
    }

    public void userSelectedUrl() {
        final String url = this.getUrlField().getText();
        final boolean hq = this.getHighQualityField().getChecked();
        this._worker.userSelectedUrl(this, url, hq);
    }

    private class AboutMenuItem extends MenuItem {

        public AboutMenuItem(int ordinal, int priority) {
            super("About", ordinal, priority);
        }

        public void run() {
            Video2MP3Screen.this.userSelectedAbout();
        }
    }

    private class CleanupsMenuItem extends MenuItem {

        public CleanupsMenuItem(int ordinal, int priority) {
            super("Prepare For Upgrade", ordinal, priority);
        }

        public void run() {
            Video2MP3Screen.this.userSelectedCleanup();
        }
    }

    private class SelectUrlMenuItem extends MenuItem {

        public SelectUrlMenuItem(int ordinal, int priority) {
            super(STR_DOWNLOAD, ordinal, priority);
        }

        public void run() {
            Video2MP3Screen.this.userSelectedUrl();
        }
    }

    public static interface Worker {

        public void about();

        public void cleanup();

        public void userSelectedUrl(Video2MP3Screen screen, String url, boolean hq);

    }
}

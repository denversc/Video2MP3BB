package video2mp3bb;

import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class Video2MP3Screen extends MainScreen {

    private final Worker _worker;
    private final EditField _urlField;

    public Video2MP3Screen(Worker worker) {
        if (worker == null) {
            throw new NullPointerException("worker==null");
        }
        this._worker = worker;
        this.setTitle("Video2MP3");

        final MenuItem selectUrlMenuItem = new SelectUrlMenuItem(0x00010000, 0);
        this.addMenuItem(selectUrlMenuItem);

        this._urlField =
            new EditField(null, null, TextField.DEFAULT_MAXCHARS, BasicEditField.FILTER_URL);
        final Border border = BorderFactory.createRoundedBorder(new XYEdges(5, 5, 5, 5));
        this._urlField.setBorder(border);

        final ButtonField selectButton =
            new ButtonField("Get MP3 From URL", ButtonField.CONSUME_CLICK | ButtonField.NEVER_DIRTY);
        selectButton.setRunnable(selectUrlMenuItem);

        this.add(new LabelField("Enter the Url of the video to convert to MP3:"));
        this.add(this._urlField);
        this.add(selectButton);
    }

    public EditField getUrlField() {
        return this._urlField;
    }

    public Worker getWorker() {
        return this._worker;
    }

    public void selectUrl() {
        final String url = this.getUrlField().getText();
        this._worker.userSelectedUrl(this, url);
    }

    private class SelectUrlMenuItem extends MenuItem {

        public SelectUrlMenuItem(int ordinal, int priority) {
            super("Download MP3 from URL", ordinal, priority);
        }

        public void run() {
            Video2MP3Screen.this.selectUrl();
        }
    }

    public static interface Worker {

        public void userSelectedUrl(Video2MP3Screen screen, String url);

    }
}
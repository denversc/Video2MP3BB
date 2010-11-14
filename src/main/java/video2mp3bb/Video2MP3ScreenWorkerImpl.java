package video2mp3bb;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.component.Dialog;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;

public class Video2MP3ScreenWorkerImpl implements Video2MP3Screen.Worker {

    public void handleInvalidUrl(Video2MP3Screen screen, String url) {
        Dialog.alert("Invalid URL");
        final Field field = screen.getUrlField();
        field.setFocus();
    }

    public void handleUrl(String url, boolean hq) {
        final BrowserSession session = Browser.getDefaultSession();
        final String video2mp3Url = urlToVideo2MP3Url(url, hq);
        session.displayPage(video2mp3Url);
    }
    
    public void cleanup() {
    }

    public void userSelectedUrl(Video2MP3Screen screen, String url, final boolean hq) {
        final String urlClean = cleanUrl(url);
        if (urlClean == null) {
            this.handleInvalidUrl(screen, url);
        } else {
            new Thread() {
                public void run() {
                    Video2MP3ScreenWorkerImpl.this.handleUrl(urlClean, hq);
                }
            }.start();
        }
    }

    public static String cleanUrl(String url) {
        if (url != null) {
            url = url.trim();
            if (url.length() == 0) {
                url = null;
            }
        }
        return url;
    }

    public static String urlToVideo2MP3Url(String url, boolean hq) {
        final StringBuffer sb = new StringBuffer();
        sb.append("http://www.video2mp3.net/index.php?url=");

        int index = url.indexOf("&v=");
        if (index < 0) {
            index = url.indexOf("?v=");
        }

        if (index < 0) {
            sb.append(url);
        } else {
            index += 3;
            int endIndex = url.indexOf('&', index + 1);
            if (endIndex < 0) {
                endIndex = url.length();
            }
            final String videoId = url.substring(index, endIndex);

            sb.append("http://www.youtube.com/watch?v=");
            sb.append(videoId);
        }

        if (hq) {
            sb.append("&hq=1");
        }

        final String video2mp3Url = sb.toString();
        return video2mp3Url;
    }
}

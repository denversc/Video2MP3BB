package video2mp3bb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class Video2MP3ScreenWorkerImplTest {

    @Test
    public void test_urlToVideo2MP3Url_EmptyString() {
        final String actual = Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url("");
        final String expected = createVideo2MP3Url("");
        assertEquals(expected, actual);
    }

    @Test
    public void test_urlToVideo2MP3Url_NoVEq() {
        final String url = "http://www.youtube.com/watch?feature=grec_index&q=Y0F23fnELZY";
        final String actual = Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url(url);
        final String expected = createVideo2MP3Url(url);
        assertEquals(expected, actual);
    }

    @Test
    public void test_urlToVideo2MP3Url_NPE() {
        try {
            Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url(null);
        } catch (final NullPointerException e) {
            return;
        }
        fail("Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url(null) should have thrown NPE");
    }

    @Test
    public void test_urlToVideo2MP3Url_StartsAndEndsWithVEqEndsWithOther() {
        final String actual =
            Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url("http://www.abc.de/watch?v=Y0F23fnELZY");
        final String expected = createVideo2MP3Url("http://www.youtube.com/watch?v=Y0F23fnELZY");
        assertEquals(expected, actual);
    }

    @Test
    public void test_urlToVideo2MP3Url_StartsWithOtherEndsWithVEq() {
        final String actual =
            Video2MP3ScreenWorkerImpl
                .urlToVideo2MP3Url("http://www.abc.de/watch?feature=grec_index&v=Y0F23fnELZY");
        final String expected = createVideo2MP3Url("http://www.youtube.com/watch?v=Y0F23fnELZY");
        assertEquals(expected, actual);
    }

    @Test
    public void test_urlToVideo2MP3Url_StartsWithVEqEndsWithOther() {
        final String actual =
            Video2MP3ScreenWorkerImpl
                .urlToVideo2MP3Url("http://www.abc.de/watch?v=Y0F23fnELZY&feature=grec_index");
        final String expected = createVideo2MP3Url("http://www.youtube.com/watch?v=Y0F23fnELZY");
        assertEquals(expected, actual);
    }

    public static String createVideo2MP3Url(String sourceUrl) {
        return "http://www.video2mp3.net/index.php?url=" + sourceUrl + "&hq=1";
    }
}

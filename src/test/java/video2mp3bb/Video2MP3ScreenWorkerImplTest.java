/*
 * Video2MP3ScreenWorkerImplTest.java
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class Video2MP3ScreenWorkerImplTest {

    @Test
    public void test_urlToVideo2MP3Url_EmptyString() {
        final String actual = Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url("", false);
        final String expected = createVideo2MP3Url("", false);
        assertEquals(expected, actual);
    }

    @Test
    public void test_urlToVideo2MP3Url_EmptyString_HQ() {
        final String actual = Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url("", true);
        final String expected = createVideo2MP3Url("", true);
        assertEquals(expected, actual);
    }

    @Test
    public void test_urlToVideo2MP3Url_NoVEq() {
        final String url = "http://www.youtube.com/watch?feature=grec_index&q=Y0F23fnELZY";
        final String actual = Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url(url, false);
        final String expected = createVideo2MP3Url(url, false);
        assertEquals(expected, actual);
    }

    @Test
    public void test_urlToVideo2MP3Url_NoVEq_HQ() {
        final String url = "http://www.youtube.com/watch?feature=grec_index&q=Y0F23fnELZY";
        final String actual = Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url(url, true);
        final String expected = createVideo2MP3Url(url, true);
        assertEquals(expected, actual);
    }

    @Test
    public void test_urlToVideo2MP3Url_NPE() {
        try {
            Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url(null, false);
        } catch (final NullPointerException e) {
            return;
        }
        fail("Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url(null) should have thrown NPE");
    }

    @Test
    public void test_urlToVideo2MP3Url_NPE_HQ() {
        try {
            Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url(null, true);
        } catch (final NullPointerException e) {
            return;
        }
        fail("Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url(null) should have thrown NPE");
    }

    @Test
    public void test_urlToVideo2MP3Url_StartsAndEndsWithVEqEndsWithOther() {
        final String actual =
            Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url("http://www.abc.de/watch?v=Y0F23fnELZY",
                false);
        final String expected =
            createVideo2MP3Url("http://www.youtube.com/watch?v=Y0F23fnELZY", false);
        assertEquals(expected, actual);
    }

    @Test
    public void test_urlToVideo2MP3Url_StartsAndEndsWithVEqEndsWithOther_HQ() {
        final String actual =
            Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url("http://www.abc.de/watch?v=Y0F23fnELZY",
                true);
        final String expected =
            createVideo2MP3Url("http://www.youtube.com/watch?v=Y0F23fnELZY", true);
        assertEquals(expected, actual);
    }

    @Test
    public void test_urlToVideo2MP3Url_StartsWithOtherEndsWithVEq() {
        final String actual =
            Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url(
                "http://www.abc.de/watch?feature=grec_index&v=Y0F23fnELZY", false);
        final String expected =
            createVideo2MP3Url("http://www.youtube.com/watch?v=Y0F23fnELZY", false);
        assertEquals(expected, actual);
    }

    @Test
    public void test_urlToVideo2MP3Url_StartsWithOtherEndsWithVEq_HQ() {
        final String actual =
            Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url(
                "http://www.abc.de/watch?feature=grec_index&v=Y0F23fnELZY", true);
        final String expected =
            createVideo2MP3Url("http://www.youtube.com/watch?v=Y0F23fnELZY", true);
        assertEquals(expected, actual);
    }

    @Test
    public void test_urlToVideo2MP3Url_StartsWithVEqEndsWithOther() {
        final String actual =
            Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url(
                "http://www.abc.de/watch?v=Y0F23fnELZY&feature=grec_index", false);
        final String expected =
            createVideo2MP3Url("http://www.youtube.com/watch?v=Y0F23fnELZY", false);
        assertEquals(expected, actual);
    }

    @Test
    public void test_urlToVideo2MP3Url_StartsWithVEqEndsWithOther_HQ() {
        final String actual =
            Video2MP3ScreenWorkerImpl.urlToVideo2MP3Url(
                "http://www.abc.de/watch?v=Y0F23fnELZY&feature=grec_index", true);
        final String expected =
            createVideo2MP3Url("http://www.youtube.com/watch?v=Y0F23fnELZY", true);
        assertEquals(expected, actual);
    }

    public static String createVideo2MP3Url(String sourceUrl, boolean hq) {
        String result = "http://www.video2mp3.net/index.php?url=" + sourceUrl;
        if (hq) {
            result += "&hq=1";
        }
        return result;
    }
}

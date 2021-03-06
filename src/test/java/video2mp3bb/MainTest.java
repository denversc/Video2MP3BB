/*
 * MainTest.java
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

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class MainTest {

    public void testRun(String[] args, int expectedNormalCount, int expectedInitCount) {
        final MainSpy main = new MainSpy(args);
        main.run();
        main.assertRunNormalCount(expectedNormalCount);
        main.assertRunInitCount(expectedInitCount);
    }

    @Test
    public void testRunArrayLength0() {
        this.testRunExpectNormal(new String[0]);
    }

    @Test
    public void testRunArrayLength1BlahElement0() {
        this.testRunExpectNormal(new String[] { "blah" });
    }

    @Test
    public void testRunArrayLength1EmptyStringElement0() {
        this.testRunExpectNormal(new String[] { "" });
    }

    @Test
    public void testRunArrayLength1InitElement0() {
        this.testRunExpectInit(new String[] { "init" });
    }

    @Test
    public void testRunArrayLength1NullElement0() {
        this.testRunExpectNormal(new String[1]);
    }

    @Test
    public void testRunArrayLength2BlahElement0InitElement1() {
        this.testRunExpectNormal(new String[] { "blah", "init" });
    }

    @Test
    public void testRunArrayLength2EmptyStringElement0NullElement1() {
        this.testRunExpectNormal(new String[] { "", null });
    }

    @Test
    public void testRunArrayLength2InitElement0BlahElement1() {
        this.testRunExpectNormal(new String[] { "init", "blah" });
    }

    @Test
    public void testRunArrayLength2InitElement0NullElement1() {
        this.testRunExpectNormal(new String[] { "init", null });
    }

    @Test
    public void testRunArrayLength2NullElement0EmptyStringElement1() {
        this.testRunExpectNormal(new String[] { null, "" });
    }

    public void testRunExpectInit(String[] args) {
        this.testRun(args, 0, 1);
    }

    public void testRunExpectNormal(String[] args) {
        this.testRun(args, 1, 0);
    }

    @Test
    public void testRunNull() {
        this.testRunExpectNormal(null);
    }

    private static class MainSpy extends Main {

        private int _runInitCount;
        private int _runNormalCount;

        private final Object _runInitCountMutex;
        private final Object _runNormalCountMutex;

        public MainSpy(String[] args) {
            super(args);
            this._runInitCountMutex = new Object();
            this._runNormalCountMutex = new Object();
        }

        public void assertRunInitCount(int expected) {
            assertEquals("runInit() invocation count", expected, this._runInitCount);
        }

        public void assertRunNormalCount(int expected) {
            assertEquals("runNormal() invocation count", expected, this._runNormalCount);
        }

        public void runInit() {
            synchronized (this._runInitCountMutex) {
                this._runInitCount++;
            }
        }

        public void runNormal() {
            synchronized (this._runNormalCountMutex) {
                this._runNormalCount++;
            }
        }
    }
}

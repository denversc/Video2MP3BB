/*
 * Util.java
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

public class Util {

    private Util() {
    }

    public static String[] copy(String[] array) {
        String[] copy;
        if (array == null) {
            copy = null;
        } else {
            copy = new String[array.length];
            System.arraycopy(array, 0, copy, 0, array.length);
        }
        return copy;
    }
}

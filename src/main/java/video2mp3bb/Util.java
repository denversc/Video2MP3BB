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

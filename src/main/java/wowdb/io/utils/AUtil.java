package wowdb.io.utils;

public class AUtil {
    public static void reverse(byte[] array) {
        if (array != null) {
            reverse((byte[])array, 0, array.length);
        }
    }

    public static void reverse(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array != null) {
            int i = Math.max(startIndexInclusive, 0);

            for(int j = Math.min(array.length, endIndexExclusive) - 1; j > i; ++i) {
                byte tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }
}

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class Utils {
    static int[] ramdomIntArray() {
        int[] array = new int[1000000];
        Random rd = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = rd.nextInt();
        }
        return array;
    }
}

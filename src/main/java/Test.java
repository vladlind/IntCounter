import java.util.Arrays;
import java.util.Random;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        IntCounter intCounter = new IntCounter(ramdomIntArray());
        System.out.println(intCounter.sumInt(1));
        System.out.println(intCounter.sumInt(2));
        System.out.println(intCounter.sumInt(4));

        IntCounter intCounter2 = new IntCounter(sameNumberIntArray(1));
        System.out.println(intCounter2.sumInt(1));
        System.out.println(intCounter2.sumInt(2));
        System.out.println(intCounter2.sumInt(4));
    }

    private static int[] ramdomIntArray() {
        int[] array = new int[1000000];
        Random rd = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = rd.nextInt();
        }
        return array;
    }

    private static int[] sameNumberIntArray(int number) {
        int[] array = new int[1000000];
        Arrays.fill(array, number);
        return array;
    }
}

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

public class IntCounterTest {

    @RepeatedTest(100)
    public void testIfExecTimeFasterMultiThreaded() throws InterruptedException {
        IntCounter intCounter = new IntCounter(Utils.ramdomIntArray());
        intCounter.sumInt(1);
        long oneThreadExecTime = intCounter.getExecTime();
        intCounter.sumInt(2);
        long twoThreadsExecTime = intCounter.getExecTime();
        intCounter.sumInt(4);
        long fourThreadsExecTime = intCounter.getExecTime();
        Assertions.assertTrue(oneThreadExecTime > twoThreadsExecTime);

        /* 2 Threads are enough to reach the speedup limit for this task.
           4 Threads consume more time for starting and joining rather than for sum calculation.
           Adding just a little delay inside the loop in threads run() proves this theory. */
        // Assertions.assertTrue(twoThreadsExecTime > fourThreadsExecTime);
    }

    @RepeatedTest(10)
    public void testIfEqualSumCounted() throws InterruptedException {
        IntCounter intCounter = new IntCounter(Utils.ramdomIntArray());
        int oneThreadSum = intCounter.sumInt(1);
        int twoThreadsSum = intCounter.sumInt(2);
        int fourThreadsSum = intCounter.sumInt(4);
        Assertions.assertTrue((oneThreadSum == twoThreadsSum) && (twoThreadsSum == fourThreadsSum));
    }
}

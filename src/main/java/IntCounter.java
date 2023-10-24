import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class IntCounter {

    Logger LOGGER = Logger.getLogger(IntCounter.class.getName());

    private AtomicInteger totalSum;
    private final int[] array;
    private CountDownLatch countDownLatch;
    private long execTime;

    public long getExecTime() {
        return execTime;
    }

    public IntCounter(int[] array) {
        this.array = array;
    }

    public int sumInt(int threadCount) throws InterruptedException {
        final long startTime = System.nanoTime();
        handleMultiThreaded(threadCount, startTime);
        return totalSum.get();
    }

    private void handleMultiThreaded(int threadCount, long startTime) throws InterruptedException {
        totalSum = new AtomicInteger(0);
        countDownLatch = new CountDownLatch(threadCount);

        // Splitting 1M array into equal peaces for every thread
        int increment = 0;
        for (int i = 1; i <= threadCount; i++) {
            Thread t = new Thread(new Summarizer(increment, i * array.length / threadCount));
            increment = i * array.length / threadCount;
            t.start();
        }
        // Waiting for all threads to be completed
        countDownLatch.await();
        logExecTime(threadCount, startTime);
    }

    private void logExecTime(int threadCount, long startTime) {
        execTime = System.nanoTime() - startTime;
        LOGGER.info(threadCount +" Threads Exec duration microseconds: "+ (execTime)/ 1000);
    }

    private class Summarizer implements Runnable {

        private final int minIndex;
        private final int maxIndex;
        private int localThreadSum;

        public Summarizer(int minIndex, int maxIndex) {
            this.minIndex = minIndex;
            this.maxIndex = maxIndex;
        }

        @lombok.SneakyThrows
        @Override
        public void run() {
            LOGGER.info(Thread.currentThread().getName() + " started");
            for (int i = minIndex; i < maxIndex; i++) {
                // Thread.sleep(1); // simulating cpu intensive calculations to show benefit of 4 threads
                localThreadSum += array[i];
            }
            // Atomically writing counted sum of the array slice
            totalSum.addAndGet(localThreadSum);
            LOGGER.info(Thread.currentThread().getName() + " finished");
            countDownLatch.countDown();
        }
    }
}

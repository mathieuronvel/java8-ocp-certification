package mathieuronvel.java8ocp.concurrency;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CallableAwaitTerminationExercise {

    /**
     * Checks the behaviour of ExecutorService.awaitTermination with a thread pool.
     * The expected behaviour is depending on `timeout`.
     * If the timeout has a big value (e.g. 1s), all threads will be finished.
     * Otherwise one or several threads will not have time to finish
     * (e.g. 300ms gives only 1 thread done).
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int timeout = 300;
        System.out.println("testAwaitTermination, timeout = " + timeout);
        ExecutorService executorService = null;
        List<Future<?>> futures = new ArrayList<>();
        try {
            executorService = Executors.newFixedThreadPool(4);
            futures.add(executorService.submit(() -> doSleepLoop(1, 5)));
            futures.add(executorService.submit(() -> doSleepLoop(2, 4)));
            futures.add(executorService.submit(() -> doSleepLoop(3, 3)));
            futures.add(executorService.submit(() -> doSleepLoop(4, 2)));
            System.out.println("executorService invoked");
        } finally {
            if (executorService != null) {
                System.out.println("executorService.shutdown()");
                executorService.shutdown();
            }
        }
        executorService.awaitTermination(timeout, TimeUnit.MILLISECONDS);
        if (executorService.isTerminated()) {
            System.out.println("All tasks finished");
        } else {
            System.out.println("At least one task is still running");
        }
        for (int i = 0; i < futures.size(); i++) {
            System.out.println("Future of index `" + i + "` is done : "
                + futures.get(i).isDone());
        }
        executorService.shutdownNow();
    }

    private static int doSleepLoop(int id, int maxIndex) throws InterruptedException {
        for (int i = 0; i < maxIndex; i++) {
            System.out.println(" ID: `" + id + "` - index: " +i);
            Thread.sleep(100);
        }
        return id;
    }

}
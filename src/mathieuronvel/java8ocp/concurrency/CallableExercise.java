package mathieuronvel.java8ocp.concurrency;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CallableExercise {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        testInvokeAll();
        testInvokeAny();
        testAwaitTermination(1000); // Enough time to finish all task
        testAwaitTermination(100); // Timeout will be triggered
    }

    private static void testInvokeAll() throws InterruptedException, ExecutionException {
        System.out.println("testInvokeAll");
        ExecutorService executorService = null;
        try {
            executorService = Executors.newSingleThreadExecutor();
            List<Future<Integer>> futures = executorService.invokeAll(List.of(
                () ->1,
                () ->2,
                () ->3,
                () ->4,
                () ->5,
                () ->6
            ));
            for (Future<Integer> future : futures) {
                System.out.println(future.get());
            }
        } finally {
            if (executorService != null) {
                executorService.shutdown();
            }
        }
    }

    private static void testInvokeAny() throws InterruptedException, ExecutionException {
        System.out.println("testInvokeAny");
        ExecutorService executorService = null;
        try {
            executorService = Executors.newFixedThreadPool(4);
            Integer firstResult = executorService.invokeAny(List.of(
                () -> {Thread.sleep(1000); return 1;},
                () -> {Thread.sleep(800); return 2;},
                () -> {Thread.sleep(600); return 3;},
                () -> {Thread.sleep(400); return 4;}
            ));
            System.out.println(firstResult);
        } finally {
            if (executorService != null) {
                executorService.shutdown();
            }
        }
    }

    private static void testAwaitTermination(int timeout) throws InterruptedException, ExecutionException {
        System.out.println("testAwaitTermination, timeout = " + timeout);
        ExecutorService executorService = null;
        try {
            executorService = Executors.newFixedThreadPool(4);
            executorService.submit(() -> doSleepLoop(1, 5));
            executorService.submit(() -> doSleepLoop(2, 4));
            executorService.submit(() -> doSleepLoop(3, 3));
            executorService.submit(() -> doSleepLoop(4, 2));
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
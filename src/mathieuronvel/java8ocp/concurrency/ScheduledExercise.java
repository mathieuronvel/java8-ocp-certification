package mathieuronvel.java8ocp.concurrency;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExercise {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    testSchedule();
    testScheduleFixedRate();
    testScheduleFixedDelay();
  }

  private static void testSchedule() throws InterruptedException, ExecutionException {
    System.out.println("testSchedule");
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    try {
      // This first runnable will not be called
      // because of the 3rd scheduled runnable doing a shutdownNow
      executorService.schedule(() -> System.out.println(1), 200, TimeUnit.MILLISECONDS);
      executorService.schedule(() -> System.out.println(2), 100, TimeUnit.MILLISECONDS);
      executorService.schedule(executorService::shutdownNow, 150, TimeUnit.MILLISECONDS);
      System.out.println("executorService invoked");
    } finally {
      System.out.println("executorService.shutdown()");
    }
  }

  private static void testScheduleFixedRate() throws InterruptedException, ExecutionException {
    System.out.println("testScheduleFixedRate");
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
    try {
      executorService.scheduleAtFixedRate(() -> System.out.println(1), 10, 200, TimeUnit.MILLISECONDS);
      executorService.scheduleAtFixedRate(() -> System.out.println(2), 0, 100, TimeUnit.MILLISECONDS);
      executorService.schedule(executorService::shutdownNow, 500, TimeUnit.MILLISECONDS);
      System.out.println("executorService invoked");
    } finally {
      System.out.println("executorService.shutdown()");
      //executorService.shutdown();
    }
    executorService.awaitTermination(10, TimeUnit.SECONDS);
  }

  private static void testScheduleFixedDelay() throws InterruptedException, ExecutionException {
    System.out.println("testScheduleFixedDelay");
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
    try {
      executorService.scheduleWithFixedDelay(() -> doSleep(1), 100, 1, TimeUnit.MILLISECONDS);
      executorService.scheduleWithFixedDelay(() -> doSleep(2), 0, 100, TimeUnit.MILLISECONDS);
      executorService.schedule(executorService::shutdownNow, 500, TimeUnit.MILLISECONDS);
      System.out.println("executorService invoked");
    } finally {
      System.out.println("executorService.shutdown()");
      //executorService.shutdown();
    }
  }

  private static void doSleep(int id) {
    System.out.println("ID: " + id + " - " + ZonedDateTime.now());
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static int doSleepLoop(int id, int maxIndex) throws InterruptedException {
    for (int i = 0; i < maxIndex; i++) {
      System.out.println(" ID: `" + id + "` - index: " +i);
      Thread.sleep(100);
    }
    return id;
  }

}

package mathieuronvel.java8ocp.concurrency;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Reproduces this common issue found in https://stackoverflow.com/questions/461896/what-is-the-most-frequent-concurrency-issue-youve-encountered-in-java
 * "My #1 most painful concurrency problem ever occurred when two different open source libraries did something like this:"
 * <pre>
 *   private static final String LOCK = "LOCK";  // use matching strings
 *                                               // in two different libraries
 *
 *   public doSomestuff() {
 *      synchronized(LOCK) {
 *          this.work();
 *      }
 *   }
 * </pre>
 * Example of result:
 * <pre>
 * 2021-01-07T22:25:39.813512 - It will start to acquired lock 1
 * 2021-01-07T22:25:39.813702 - It will start to acquired lock 2
 * 2021-01-07T22:25:39.840175 - Lock 1 acquired
 * 2021-01-07T22:25:40.841003 - Lock 1 released
 * 2021-01-07T22:25:40.841003 - Lock 2 acquired
 * 2021-01-07T22:25:41.841888 - Lock 2 released
 * </pre>
 */
public class StringConcurrencyLockIssue {

  private static String LOCK_1 = "LOCK";
  private static Object LOCK_2 = "LOCK";

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService executorService = null;
    try {
      executorService = Executors.newFixedThreadPool(2);
      List<Future<?>> futures = new ArrayList<>();
      futures.add(executorService.submit(() -> {lock1(); return null;}));
      futures.add(executorService.submit(() -> {lock2(); return null;}));
      executorService.awaitTermination(5, TimeUnit.SECONDS);
      executorService.shutdownNow();
    } finally {
      if (executorService != null) {
        executorService.shutdown();
      }
    }
  }

  private static void lock1() throws InterruptedException {
    System.out.println(LocalDateTime.now() + " - It will start to acquired lock 1");
    synchronized (LOCK_1) {
      System.out.println(LocalDateTime.now() + " - Lock 1 acquired");
      Thread.sleep(1000);
    }
    System.out.println(LocalDateTime.now() + " - Lock 1 released");
  }

  private static void lock2() throws InterruptedException {
    System.out.println(LocalDateTime.now() + " - It will start to acquired lock 2");
    synchronized (LOCK_2) {
      System.out.println(LocalDateTime.now() + " - Lock 2 acquired");
      Thread.sleep(1000);
    }
    System.out.println(LocalDateTime.now() + " - Lock 2 released");
  }
}

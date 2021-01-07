package mathieuronvel.java8ocp.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class DeadlockExercise {

  private static Object food = new Object();
  private static Object water = new Object();

  /**
   * Reproduces a deadlock with the example coming from the OCP book (about foxes).
   * A couple of foxes want to eat and drink. Fox 1 to eat then drink, fox 2 to drink then eat.
   * They cannot eat together or drink together.
   * Deadlock result:
   * <pre>
   *   Fox 1 will start to eat
   *   Fox 1 eat
   *   Fox 2 will start to dring
   *   Fox 2 drink
   * </pre>
   * Fox 1 cannot drink and fox 2 cannot eat.
   */
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService executorService = null;
    try {
      executorService = Executors.newFixedThreadPool(2);
      List<Future<?>> futures = new ArrayList<>();
      futures.add(executorService.submit(() -> {eatAndDrink(); return null;}));
      futures.add(executorService.submit(() -> {drinkAndEat(); return null;}));
      executorService.awaitTermination(1, TimeUnit.SECONDS);
      executorService.shutdownNow();
      futures.forEach(f -> System.out.println("Thread is still running after 1s : " + !f.isDone()));
      System.exit(0); // Exit the app because of the deadlock
    } finally {
      if (executorService != null) {
        executorService.shutdown();
      }
    }
  }

  private static void eatAndDrink() throws InterruptedException {
    System.out.println("Fox 1 will start to eat");
    synchronized (food) {
      System.out.println("Fox 1 eat");
      Thread.sleep(100);
      synchronized (water) {
        System.out.println("Fox 1 drink");
      }
    }
  }

  private static void drinkAndEat() throws InterruptedException {
    System.out.println("Fox 2 will start to dring");
    synchronized (water) {
      System.out.println("Fox 2 drink");
      Thread.sleep(100);
      synchronized (food) {
        System.out.println("Fox 2 eat");
      }
    }
  }

}

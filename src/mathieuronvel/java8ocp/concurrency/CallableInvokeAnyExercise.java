package mathieuronvel.java8ocp.concurrency;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableInvokeAnyExercise {

  /**
   * Checks the behaviour of ExecutorService.invokeAny with a thread pool.
   * The expected result is the thread which gives the quickest response.
   * In majority of the cases it will print `4`.
   */
  public static void main(String[] args) throws InterruptedException, ExecutionException {
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
}

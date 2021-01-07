package mathieuronvel.java8ocp.concurrency;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableInvokeAll1ThreadExercise {

  /**
   * Checks the behaviour of ExecutorService.invokeAll with only 1 thread but multiple requests.
   * The expected result is a sorted list of integer futures from 1 to 6.
   */
  public static void main(String[] args) throws InterruptedException, ExecutionException {
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
}

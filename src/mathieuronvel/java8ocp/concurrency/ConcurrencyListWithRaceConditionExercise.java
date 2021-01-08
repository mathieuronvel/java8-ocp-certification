package mathieuronvel.java8ocp.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyListWithRaceConditionExercise {

  private static List<String> items = new ArrayList<>();

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService executorService = null;
    try {
      executorService = Executors.newFixedThreadPool(100);
      for (int i = 0; i < 100; i++) {
        int index = i;
        executorService.submit(() -> addMoreItems(index));
      }
      executorService.awaitTermination(4, TimeUnit.SECONDS);
      if (items.size() == 100_000) {
        System.out.println("Success, no race condition");
      } else {
        System.out.println((100_000 - items.size()) + " race conditions happened");
      }
    } finally {
      if (executorService != null) {
        executorService.shutdown();
      }
    }
  }

  private static void addMoreItems(int threadIndex) {
    for (int i = 0; i < 1000; i++) {
      String item = threadIndex + " - " + i;
      items.add(item);
      System.out.println(item);
    }
  }

}

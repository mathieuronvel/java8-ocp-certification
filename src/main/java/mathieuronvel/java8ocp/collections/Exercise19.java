package mathieuronvel.java8ocp.collections;

import java.util.LinkedList;
import java.util.Queue;

public class Exercise19 {

  public static void main(String ... s) {
    Queue<Integer> q = new LinkedList<>();
    q.add(10);
    q.add(12);
    System.out.print(q.remove(1)); // Object `1` does not exist
    System.out.print(q);
  }

}

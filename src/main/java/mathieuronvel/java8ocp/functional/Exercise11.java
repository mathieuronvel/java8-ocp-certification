package mathieuronvel.java8ocp.functional;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Exercise11 {

  public static void main(String ... s) {
    System.out.println(Stream.iterate(1, x -> ++x)
        .limit(5)
        .map(x -> "" + x)
        .collect(Collectors.joining()));
  }

}

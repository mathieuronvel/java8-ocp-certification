package mathieuronvel.java8ocp.functional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ExerciseReduce {

  public static void main(String[] args) {
    System.out.println(List.of("aaa", "bbb", "ccc", "ddd", "eee", "fff")
        .stream().parallel()
        .reduce(new StringBuilder(), StringBuilder::append, StringBuilder::append));

    Stream<String> stream = Stream.of("w", "o", "l", "f").parallel();
    String word = stream.reduce("", (s, c) -> s + c, (c,d) -> c + d);
    System.out.println(word); // wolf

    List<String> list = Arrays.asList("1","2","3","4","5","6",
        "7","8","9","10","11","12");
    String result = list.parallelStream()
        .collect(StringBuilder::new, StringBuilder::append,
            StringBuilder::append).toString();
    System.out.println(result);
  }

}

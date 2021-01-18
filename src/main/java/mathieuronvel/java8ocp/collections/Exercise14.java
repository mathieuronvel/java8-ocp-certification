package mathieuronvel.java8ocp.collections;

import java.util.Comparator;
import java.util.TreeSet;

public class Exercise14 implements  Comparable<Exercise14>, Comparator<Exercise14> {

  private int num;
  private String text;

  Exercise14(int n, String t) {
    this.num = n;
    this.text = t;
  }

  public String toString() {
    return "" + num;
  }

  public int compareTo(Exercise14 e) {
    return text.compareTo(e.text);
  }

  public int compare(Exercise14 e1, Exercise14 e2) {
    return e1.num - e2.num;
  }

  public static void main(String s[]) {
    Exercise14 e1 = new Exercise14(88, "a");
    Exercise14 e2 = new Exercise14(55, "b");
    TreeSet<Exercise14> t1 = new TreeSet<>();
    t1.add(e1); t1.add(e2);
    TreeSet<Exercise14> t2 = new TreeSet<>(t1);
    t2.add(e1); t2.add(e2);
    System.out.println(t1 + " " + t2);
  }

}

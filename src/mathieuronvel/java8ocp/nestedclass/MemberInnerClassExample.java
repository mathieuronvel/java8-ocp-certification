package mathieuronvel.java8ocp.nestedclass;

import java.util.function.Predicate;

public class MemberInnerClassExample {

  private String greeting = "Hi";

  protected class Inner {
    public int repeat = 3;
    public void go() {
      for (int i = 0; i < repeat; i++) {
        System.out.println(greeting);
      }
    }
  }

  public static void main(String [] args) {
    MemberInnerClassExample outer = new MemberInnerClassExample();
    Inner inner = outer.new Inner();
    inner.go();
  }

}

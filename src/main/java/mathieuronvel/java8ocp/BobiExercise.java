package mathieuronvel.java8ocp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BobiExercise {

  public static void main(String[] args) {
    System.out.println("[2,3] = " + computePermutations(List.of("2", "3")));
    System.out.println("[2,3,4] = " + computePermutations(List.of("2", "3", "4")));
    System.out.println("[2,3,4,5] = " + computePermutations(List.of("2", "3", "4", "5")));
  }

  private static Collection<List<String>> computePermutations(List<String> items) {
    Collection<List<String>> permutations = new HashSet<>();
    for (int i = 0; i < items.size(); i++) {
      for (int j=0; j < items.size(); j++) {
        List<String> permutation = new ArrayList<>(items);
        permute(permutation, i, j);
        permutations.add(permutation);
      }
    }
    return permutations;
  }

  private static void permute(List<String> items, int indexToMove, int indexToPlace) {
    String temp = items.get(indexToPlace);
    items.set(indexToPlace, items.get(indexToMove));
    items.set(indexToMove, temp);
  }

}

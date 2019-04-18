package edu.grinnell.sortingvisualizer.sorts;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SortsTest {

  Random rand = new Random();

  // +-----------+---------------------------------------------------
  // | Utilities |
  // +-----------+

  void checkSort(Consumer<Integer[]> sorter, Integer[] expected, Integer[] values) {
    sorter.accept(values);
    assertArrayEquals(expected, values);
  }

  void checkSort(Consumer<Integer[]> sorter, Integer[] sorted) {
    Integer[] copy = sorted.clone();
    randomlyPermute(copy);
    checkSort(sorter, sorted, copy);
  }

  private void randomlyPermute(Object[] array) {
    for (int i = array.length - 1; i >= 0; i--) {
      swap(array, i, rand.nextInt(i));
    }
  }

  private void swap(Object[] array, int num1, int num2) {
    Object temp = array[num1];
    array[num1] = array[num2];
    array[num2] = temp;
  }

  private Integer[] randomArrayWith(int n) {
    Integer[] strArray = new Integer[n];
    for (int i = 0; i < n; i++) {
      strArray[i] = rand.nextInt(n);
    }
    return strArray;
  }

  // +--------------+------------------------------------------------
  // | Tests Helper |
  // +--------------+

  void testEmpty(Consumer<Integer[]> sorter) {
    checkSort(sorter, new Integer[0]);
  }

  void testOrdered(Consumer<Integer[]> sorter) {
    for (int size = 11; size < 20; size++) {
      Integer[] sorted = new Integer[size];
      for (int i = 0; i < size; i++) {
        sorted[i] = i;
      }
      checkSort(sorter, sorted, sorted.clone());
    }
  }

  void testBackwards(Consumer<Integer[]> sorter) {
    for (int size = 1; size < 20; size++) {
      Integer[] sorted = new Integer[size];
      Integer[] backwards = new Integer[size];
      for (int i = 0; i < size; i++) {
        backwards[i] = size - i;
        sorted[i] = i + 1;
      } // for
      checkSort(sorter, sorted, backwards);
    } // for
  } // testBackwards}

  void testRandom(Consumer<Integer[]> sorter) {
    for (int trials = 0; trials < 10; trials++) {
      Integer[] values = randomArrayWith(100);
      Integer[] expected = values.clone();
      Arrays.sort(expected);
      checkSort(sorter, expected, values);
    }
  }

  void sortTest(Consumer<Integer[]> sorter) {
    testEmpty(sorter);
    testOrdered(sorter);
    testBackwards(sorter);
    testRandom(sorter);
  }

  // +-------+-------------------------------------------------------
  // | Tests |
  // +-------+

  @Test void selectionTest() {
    sortTest((arr) -> Sorts.selectionSort(arr));
  }

  @Test void insertionTest() {
    sortTest((arr) -> Sorts.insertionSort(arr));
  }

  @Test void mergeTest() {
    sortTest((arr) -> Sorts.mergeSort(arr));
  }

  @Test void quickTest() {
    sortTest((arr) -> Sorts.quickSort(arr));
  }

  @Test void bubbleTest() {
    sortTest((arr) -> Sorts.bubbleSort(arr));
  }
}

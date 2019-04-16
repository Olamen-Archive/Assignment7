package edu.grinnell.sortingvisualizer.sorts;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import edu.grinnell.sortingvisualizer.sortevents.SortEvent;

class SortsTest {

  @Test
  void test() {
    Random rand = new Random();
    for (int i = 1; i <= 100; i++) {
      Integer[] absArr = new Integer[i];
      for (int j = 0; j < i; j++) {
        absArr[j] = rand.nextInt(100);
      }
      Integer[] sortArr = absArr.clone();
      Integer[] eventArr = absArr.clone();
      
      List<SortEvent <Integer>> eventList = new ArrayList<>();
      
      eventList = Sorts.bubbleSort(sortArr);
      Sorts.eventSort(eventArr, eventList);
      Arrays.sort(absArr);
      
      if (!isSame(absArr, sortArr)) {
        System.out.println("Different absArr and sortArr");
        System.out.println("Abs: " + Arrays.toString(absArr));
        System.out.println("Sort: " + Arrays.toString(sortArr));
      }
      
      if (!isSame(sortArr, eventArr)) {
        System.out.println("Different sortArr and eventArr");
        System.out.println("sort: " + Arrays.toString(sortArr));
        System.out.println("event: " + Arrays.toString(eventArr));
      }
    }
  }

  boolean isSame(Comparable[] arr1, Comparable[] arr2) {
    if (arr1.length != arr2.length) {
      return false;
    }

    for (int i = 0; i < arr1.length; i++) {
      if (arr1[i].compareTo(arr2[i]) != 0) {
        return false;
      }
    }

    return true;
  }
}

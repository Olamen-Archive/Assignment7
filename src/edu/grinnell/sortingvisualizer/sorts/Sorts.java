package edu.grinnell.sortingvisualizer.sorts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.grinnell.sortingvisualizer.events.CompareEvent;
import edu.grinnell.sortingvisualizer.events.CopyEvent;
import edu.grinnell.sortingvisualizer.events.SortEvent;
import edu.grinnell.sortingvisualizer.events.SwapEvent;

public class Sorts {
  
  /**
   * Sorts the array by repeatedly applying the events in the list
   * 
   * @param arr the array to be sorted
   * @param events the List of events to be applied
   */ 
  public static <T extends Comparable<T>> void eventSort(T[] arr, List<SortEvent<T>> events) {
    for (SortEvent<T> event : events) {
      event.apply(arr);
    }
  }
  
  /**
   * Sorts the array by selection sort
   * 
   * @param arr the array to be sorted
   * @return the list of events that this sort applies
   */ 
  public static <T extends Comparable<T>> List<SortEvent<T>> selectionSort(T[] arr) {
    List<SortEvent<T>> events = new ArrayList<>();
    for (int i = 0; i < arr.length - 1; i++) {
      int minIndex = i;
      for (int j = i + 1; j < arr.length; j++) {
        if (less(arr, j, minIndex, events)) {
          minIndex = j;
        }
      }
      swap(arr, minIndex, i, events);
    }
    return events;
  }

  /**
   * Sorts the array by insertion sort
   * 
   * @param arr the array to be sorted
   * @return the list of events that this sort applies
   */
  public static <T extends Comparable<T>> List<SortEvent<T>> insertionSort(T[] arr) {
    List<SortEvent<T>> events = new ArrayList<>();
    for (int i = 0; i < arr.length; i++) {
      int pos = i;
      while (pos > 0 && less(arr, pos, pos - 1, events)) {
        swap(arr, pos - 1, pos, events);
        pos = pos - 1;
      }
    }
    return events;
  }

  /**
   * Sorts the array by merge sort
   * 
   * @param arr the array to be sorted
   * @return the list of events that this sort applies
   */
  public static <T extends Comparable<T>> List<SortEvent<T>> mergeSort(T[] arr) {
    List<SortEvent<T>> events = new ArrayList<>();
    mergeSort(arr, 0, arr.length, events);
    return events;
  }

  /**
   * Sorts the array by merge sort recursively
   * 
   * @param lo the lower bound 
   * @param hi the higher boundary
   * @param arr the array to be partially sorted
   * @param events the list of events that will be added to
   */
  private static <T extends Comparable<T>> void mergeSort(T[] arr, int lo, int hi,
      List<SortEvent<T>> events) {
    if (hi - lo <= 1) {
      return;
    }
    int mid = (hi + lo) / 2;
    mergeSort(arr, lo, mid, events);
    mergeSort(arr, mid, hi, events);
    merge(arr, lo, mid, hi, events);
  }

  /**
   * Merges two sorted arrays
   * 
   * @param lo the lower bound of the first portion
   * @param mid the higher boundary of the first portion
   *        - the lower boundary of the second portion
   * @param hi the higher boundary of the second portion
   * @param arr the array to be merged
   * @param events the list of events that will be added to
   */
  private static <T extends Comparable<T>> void merge(T[] arr, int lo, int mid, int hi,
      List<SortEvent<T>> events) {
    @SuppressWarnings("unchecked")
    T[] temp = (T[]) new Comparable[hi - lo];
    int i = lo;
    int j = mid;
    for (int k = 0; k < temp.length; k++) {
      if (i >= mid) {
        temp[k] = arr[j];
        j++;
      } else if (j >= hi) {
        temp[k] = arr[i];
        i++;
      } else if (less(arr, i, j, events)) {
        temp[k] = arr[i];
        i++;
      } else {
        temp[k] = arr[j];
        j++;
      }
    }

    for (int l = 0; l < temp.length; l++) {
      copy(arr, temp[l], lo + l, events);
    }
  }

  /**
   * Sorts the array by quicksort
   * 
   * @param arr the array to be sorted
   * @return the list of events that this sort applies
   */
  public static <T extends Comparable<T>> List<SortEvent<T>> quickSort(T[] arr) {
    List<SortEvent<T>> events = new ArrayList<>();
    shuffle(arr, events);
    quickSort(arr, 0, arr.length, events);
    return events;
  }

  /**
   * Sorts the array by quicksort recursively
   * 
   * @param arr the array to be sorted
   * @param lo the lower boundary of the array to partition
   * @param hi the higher boundary of the array to partition
   * @param events the list of events that this method uses
   */
  private static <T extends Comparable<T>> void quickSort(T[] arr, int lo, int hi, List<SortEvent<T>> events) {
    if (hi - lo <= 1) {
      return;
    }
    //Pivot is always at position lo
    int lp = lo + 1;
    int hp = hi - 1;

    while (lp <= hp) {
      while (lp < hi && less(arr, lp, lo, events)) {
        lp++;
      }

      while (hp > lp && !less(arr, hp, lo, events)) {
        hp--;
      }

      if (lp <= hp) {
        swap(arr, lp++, hp--, events);
      }
    }
    swap(arr, lo, hp, events);

    quickSort(arr, lo, hp, events);
    quickSort(arr, hp + 1, hi, events);
  }

  /**
   * Sorts the array by bubble sort
   * 
   * @param arr the array to be sorted
   * @return events the list of events that this method uses
   */
  public static <T extends Comparable<T>> List<SortEvent<T>> bubbleSort(T[] arr) {
    List<SortEvent<T>> events = new ArrayList<>();
    for (int i = 0; i < arr.length - 1; i++) {
      for (int j = 0; j < arr.length - 1 - i; j++)
        if (less(arr, j+1, j, events)) {
          swap(arr, j, j + 1, events);
        }
    }
    return events;
  }

  /**
   * Randomly permutes an array
   * @param arr the array to be shuffled
   * @param events the list of events that will be added to
   */
  private static <T> void shuffle(T[] arr, List<SortEvent<T>> events) {
    Random rand = new Random();
    for (int i = arr.length - 1; i >= 1; i--) {
      swap(arr, i, rand.nextInt(i), events);
    }
  }

  /**
   * Swaps elements at two indices of an array
   * @param arr the array in which the elements will be swapped
   * @param i the index of the first element to be swapped
   * @param j the index of the second element to be swapped
   * @param events the list of events that will be added to
   */
  public static <T> void swap(T[] arr, int i, int j, List<SortEvent<T>> events) {
    events.add(new SwapEvent<T>(i, j));
    T temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }


  /**
   * Compares two elements of an array
   * @param arr the array in which two elements will be compared
   * @param first the index of the first element
   * @param second the index of the second element
   * @param events the list of events that will be added to
   * @returns true if the element at the first index is less than that of the second,
   *          false otherwise
   */
  public static <T extends Comparable<T>> boolean less(T[] arr, int first, int second,
      List<SortEvent<T>> events) {
    events.add(new CompareEvent<T>(first, second));
    return arr[first].compareTo(arr[second]) < 0;
  }

  /**
   * Copies a value into an array
   * @param arr the array in which the value will be copied
   * @param val the value to be copied
   * @param index the index where the value will be copied into in the array
   * @param events the list of events that will be added to
   */
  public static <T extends Comparable<T>> void copy(T[] arr, T val, int index,
      List<SortEvent<T>> events) {
    arr[index] = val;
    events.add(new CopyEvent<T>(index, val));
  }

}

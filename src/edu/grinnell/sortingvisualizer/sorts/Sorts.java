package edu.grinnell.sortingvisualizer.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import edu.grinnell.sortingvisualizer.sortevents.CompareEvent;
import edu.grinnell.sortingvisualizer.sortevents.CopyEvent;
import edu.grinnell.sortingvisualizer.sortevents.SortEvent;
import edu.grinnell.sortingvisualizer.sortevents.SwapEvent;

public class Sorts {
  
  public static <T extends Comparable<T>> void eventSort(T[] arr, List<SortEvent<T>> events) {
    for (SortEvent<T> event : events) {
      event.apply(arr);
    }
  }
  
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

  public static <T extends Comparable<T>> List<SortEvent<T>> mergeSort(T[] arr) {
    List<SortEvent<T>> events = new ArrayList<>();
    mergeSort(arr, 0, arr.length, events);
    return events;
  }

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

  public static <T extends Comparable<T>> List<SortEvent<T>> quickSort(T[] arr) {
    List<SortEvent<T>> events = new ArrayList<>();
    shuffle(arr, events);
    quickSort(arr, 0, arr.length, events);
    return events;
  }

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

  private static <T> void shuffle(T[] arr, List<SortEvent<T>> events) {
    Random rand = new Random();
    for (int i = arr.length - 1; i >= 1; i--) {
      swap(arr, i, rand.nextInt(i), events);
    }
  }

  public static <T> void swap(T[] arr, int i, int j, List<SortEvent<T>> events) {
    events.add(new SwapEvent<T>(i, j));
    T temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  public static <T extends Comparable<T>> boolean less(T[] arr, int first, int second,
      List<SortEvent<T>> events) {
    events.add(new CompareEvent<T>(first, second));
    return arr[first].compareTo(arr[second]) < 0;
  }

  public static <T extends Comparable<T>> void copy(T[] arr, T val, int index,
      List<SortEvent<T>> events) {
    arr[index] = val;
    events.add(new CopyEvent<T>(index, val));
  }

}

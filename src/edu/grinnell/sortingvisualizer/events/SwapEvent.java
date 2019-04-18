package edu.grinnell.sortingvisualizer.events;

import java.util.Arrays;
import java.util.List;

public class SwapEvent<T> implements SortEvent<T> {

  private int firstIndex;
  private int secondIndex;
  
  public SwapEvent(int first, int second) {
    firstIndex = first;
    secondIndex = second;
  }
  @Override
  public void apply(T[] arr) {
    T val = arr[firstIndex];
    arr[firstIndex] = arr[secondIndex];
    arr[secondIndex] = val;
  }

  @Override
  public List<Integer> getAffectedIndices() {
    return Arrays.asList(firstIndex, secondIndex);
  }

  @Override
  public boolean isEmphasized() {
    return true;
  }
  

}

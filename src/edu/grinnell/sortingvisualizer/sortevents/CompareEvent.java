package edu.grinnell.sortingvisualizer.sortevents;

import java.util.Arrays;
import java.util.List;

public class CompareEvent<T> implements SortEvent<T> {
  private int firstIndex;
  private int secondIndex;
  
  public CompareEvent(int first, int second){
    firstIndex = first;
    secondIndex = second;
  }
  
  @Override
  public void apply(T[] arr) {
    return;
  }

  @Override
  public List<Integer> getAffectedIndices() {
    return Arrays.asList(firstIndex, secondIndex);
  }

  @Override
  public boolean isEmphasized() {
    return false;
  }

}

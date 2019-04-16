package edu.grinnell.sortingvisualizer.sortevents;

import java.util.Arrays;
import java.util.List;

public class CopyEvent<T> implements SortEvent<T> {

  private int index;
  private T val;
  
  public CopyEvent(int index, T val) {
    this.index = index;
    this.val = val;
  }
  
  public void apply(T[] arr) {
    arr[index] = val;
  }

  @Override
  public List<Integer> getAffectedIndices() {
    return Arrays.asList(index);
  }

  @Override
  public boolean isEmphasized() {
    return true;
  }

}

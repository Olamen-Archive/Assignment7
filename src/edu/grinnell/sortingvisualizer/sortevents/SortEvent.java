package edu.grinnell.sortingvisualizer.sortevents;

import java.util.List;

public interface SortEvent<T>  {
  
  /**
   * Applies the event to the array
   * 
   * @param arr the array that the event will be applied to
   */
  public void apply (T[] arr);
  
  /**
   * @return  a list containing all the indices that the event affects 
   */
  public List<Integer> getAffectedIndices();
  
  /**
   * @return true if this event should be emphasized by the visualizer/audibilizer
   *         false otherwise
   * 
   */
  public boolean isEmphasized();
  
}

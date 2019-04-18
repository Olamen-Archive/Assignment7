package edu.grinnell.sortingvisualizer;

import java.util.Random;

/**
 * A collection of indices into a Scale object. These indices are the subject of the various sorting
 * algorithms in the program.
 */
public class NoteIndices {
  Integer [] indices;
  boolean[] highlight;

  /**
   * @param n the size of the scale object that these indices map into
   */
  public NoteIndices(int n) {
    indices = new Integer[n];
    highlight = new boolean[n];
    for (int i = 0; i < n; i++) {
      indices[i] = i;
    }

  }

  /**
   * Reinitializes this collection of indices to map into a new scale object of the given size. The
   * collection is also shuffled to provide an initial starting point for the sorting process.
   * 
   * @param n the size of the scale object that these indices map into
   */
  public void initializeAndShuffle(int n) {
    
    indices = new Integer[n];
    highlight = new boolean[n];
    for (int i = 0; i < n; i++) {
      indices[i] = i;
    }
    
    Random rand = new Random();
    for (int i = indices.length - 1; i >= 1; i--) {
      int temp = indices[i];
      int randIndex = rand.nextInt(i);
      indices[i] = indices[randIndex];
      indices[randIndex] = temp;
    }
  }


  /** @return the indices of this NoteIndices object */
  public Integer[] getNotes() {
    return indices;
  }

  /**
   * Highlights the given index of the note array
   * 
   * @param index the index to highlight
   */
  public void highlightNote(int index) {
    this.highlight[index] = true;
  }

  /** @return true if the given index is highlighted */
  public boolean isHighlighted(int index) {
    return highlight[index];
  }

  /** Clears all highlighted indices from this collection */
  public void clearAllHighlighted() {
    for(int i = 0; i < highlight.length; i++) {
      highlight[i] = false;
    }
  }
}

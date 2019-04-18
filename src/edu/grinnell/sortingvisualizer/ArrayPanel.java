package edu.grinnell.sortingvisualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ArrayPanel extends JPanel {

  private NoteIndices notes;

  /**
   * Constructs a new ArrayPanel that renders the given note indices to the screen.
   * 
   * @param notes the indices to render
   * @param width the width of the panel
   * @param height the height of the panel
   */
  public ArrayPanel(NoteIndices notes, int width, int height) {
    this.notes = notes;
    this.setPreferredSize(new Dimension(width, height));
  }

  /**
   * Draws rectangles based on the note indices
   * 
   * @param g a Graphics object
   */
  @Override
  public void paintComponent(Graphics g) {
    g.clearRect(0, 0, this.getWidth(), this.getHeight());
    int recWidth = this.getWidth() / notes.indices.length;
    for (int i = 0; i < notes.indices.length; i++) {
      Color color = getColor(notes.indices[i], notes.indices.length);
      if(notes.isHighlighted(i)) {
        //Reverse the red color when highlighted
        color = new Color(255 - color.getRed(), color.getGreen(), color.getBlue());
      }
      g.setColor(color);
      int recHeight = ((notes.indices[i] + 1) * this.getHeight()) / (notes.indices.length + 1);
      g.fillRect(recWidth * i, this.getHeight() - recHeight, recWidth, recHeight);
    }
  }
  
  /**
   * Returns a color based the height of the bar, with higher bars being
   * a deeper shade of pink
   * 
   * @param index the index of the note to be drawn
   * @param length the number of all indices
   */
  private Color getColor(int index, int length) {
    return new Color(255, 255 / length * (length - index), 255);
    //return new Color(255, 255 - (255 / length * index), 255);
  }
  
}

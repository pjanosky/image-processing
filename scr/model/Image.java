package model;

import java.awt.Color;

/**
 * Represents a picture as a collection of pixels.
 */
public interface Image {

  /**
   * Gets the horizontal number of pixels in the image.
   * @return the width of the image in pixels
   */
  int getWidth();

  /**
   * Gets the vertical number of pixels in the image.
   * @return the height of the image in pixels
   */
  int getHeight();

  /**
   * Gets the red channel value of a pixel at a certain location in the image.
   * @param row the row of the pixel measured from the top to bottom of the image
   * @param col the column o the pixel measured from the left to the right of the image
   * @return the red channel value of the pixel
   */
  int getRedValueAt(int row, int col);



  /**
   * Gets the green channel value of a pixel at a certain location in the image.
   * @param row the row of the pixel measured from the top to bottom of the image
   * @param col the column o the pixel measured from the left to the right of the image
   * @return the green channel value of the pixel
   */
  int getGreenValueAt(int row, int col);


  /**
   * Gets the blue channel value of a pixel at a certain location in the image.
   * @param row the row of the pixel measured from the top to bottom of the image
   * @param col the column o the pixel measured from the left to the right of the image
   * @return the blue channel value of the pixel
   */
  int getBlueValueAt(int row, int col);

  /**
   * Gets the color of a pixel at a certain location in the image.
   * @param row the row of the pixel measured from the top to bottom of the image
   * @param col the column o the pixel measured from the left to the right of the image
   * @return the color of the pixel
   */
  Color getPixelAt(int row, int col);
}

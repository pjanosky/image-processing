package model;

import java.util.List;

/**
 * Represents a picture as a collection of pixels.
 */
public interface Image {

  /**
   * Gets the horizontal number of pixels in the image.
   *
   * @return the width of the image in pixels
   */
  int getWidth();

  /**
   * Gets the vertical number of pixels in the image.
   *
   * @return the height of the image in pixels
   */
  int getHeight();

  /**
   * Gets the 2D array of pixels of the image.
   *
   * @return the matrix of pixels
   */

  List<List<Pixel>> getPixelList();

  /**
   * Gets the red channel value of a pixel at a certain location in the image.
   *
   * @param row the row of the pixel measured from the top to bottom of the image
   * @param col the column o the pixel measured from the left to the right of the image
   * @return the red channel value of the pixel
   * @throws IllegalArgumentException if the row or column is an invalid index for a pixel in the
   *                                  image
   */
  int getRedValueAt(int row, int col) throws IllegalArgumentException;


  /**
   * Gets the green channel value of a pixel at a certain location in the image.
   *
   * @param row the row of the pixel measured from the top to bottom of the image
   * @param col the column o the pixel measured from the left to the right of the image
   * @return the green channel value of the pixel
   * @throws IllegalArgumentException if the row or column is an invalid index for a pixel in the
   *                                  image
   */
  int getGreenValueAt(int row, int col) throws IllegalArgumentException;


  /**
   * Gets the blue channel value of a pixel at a certain location in the image.
   *
   * @param row the row of the pixel measured from the top to bottom of the image
   * @param col the column o the pixel measured from the left to the right of the image
   * @return the blue channel value of the pixel
   * @throws IllegalArgumentException if the row or column is an invalid index for a pixel in the
   *                                  image
   */
  int getBlueValueAt(int row, int col) throws IllegalArgumentException;

  /**
   * Gets the value of the specified color channel for a pixel at a certain location in the image.
   *
   * @param row     the row of the pixel measured from the top to bottom of the image
   * @param col     the column o the pixel measured from the left to the right of the image
   * @param channel the color channel to get the value of
   * @return the color channel value of the pixel
   * @throws IllegalArgumentException if the row or column is an invalid index for a pixel in the
   *                                  image
   */
  int getValueAt(int row, int col, ColorChannel channel) throws IllegalArgumentException;

  /**
   * Gets the color of a pixel at a certain location in the image.
   *
   * @param row the row of the pixel measured from the top to bottom of the image
   * @param col the column o the pixel measured from the left to the right of the image
   * @return the color of the pixel
   * @throws IllegalArgumentException if the row or column is an invalid index for a pixel in the
   *                                  image
   */
  Pixel getPixelAt(int row, int col) throws IllegalArgumentException;

//  /**
//   * Constructs a {@link Image} in a manner selected be each concrete subclass of this class.
//   *
//   * @param red   the red channel of the image
//   * @param green the green channel of the image
//   * @param blue  the blue channel of the image
//   * @return the new {@code Image}
//   */
//  Image fromRGB(Integer[][] red, Integer[][] green, Integer[][] blue);
}

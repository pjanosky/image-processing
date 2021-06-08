package model;

import java.awt.Color;
import java.lang.reflect.Array;

/**
 * Represents a 24-bit image consisting of 3 8-bit red, green, and blue, color channels.
 */
public class ImageImpl implements Image {
  private final Integer[][] red;
  private final Integer[][] green;
  private final Integer[][] blue;

  /**
   * Constructs a new ImageImp from the red, green, and blue color channels.
   * @param red a two-dimensional array of the red values for every pixel in the image
   *              where each inner array represents a row of pixel values
   * @param green a two-dimensional array of the green values for every pixel in the image
   *              where each inner array represents a row of pixel values
   * @param blue a two-dimensional array of the blue values for every pixel in the image
   *              where each inner array represents a row of pixel values
   * @throws IllegalArgumentException if any of the arguments are null, if each row in
   *         every color channel is not the same length, if the color channels are empty,
   *         or if color values are outside the range of [0, 255].
   */
  public ImageImpl(Integer[][] red, Integer[][] green, Integer[][] blue)
      throws IllegalArgumentException{
    ensureValidChannel(red);
    ensureValidChannel(green);
    ensureValidChannel(blue);
    if (!sameDimensions(red, green, blue)) {
      throw new IllegalArgumentException(
          "Red, green, and blue channels must be the same size.");
    }
    
    this.red = red;
    this.green = green;
    this.blue = blue;
  }


  @Override
  public int getWidth() {
    return Array.getLength(red[0]);
  }

  @Override
  public int getHeight() {
    return Array.getLength(red);
  }

  @Override
  public int getRedValueAt(int row, int col) {
    return red[row][col];
  }

  @Override
  public int getGreenValueAt(int row, int col) {
    return green[row][col];
  }

  @Override
  public int getBlueValueAt(int row, int col) {
    return blue[row][col];
  }

  @Override
  public Color getPixelAt(int row, int col) {
    return new Color(blue[row][col] + (green[row][col] << 8) + (red[row][col] << 8));
  }

  /**
   * Checks the validity of a color channel for an image. A valid color channel has the
   * same number of non-zero values in the range of [0, 255] in every row.
   *
   * @param channel the color channel to check the validity of
   * @throws IllegalArgumentException if the channel has no color values or if each row of the
   *         channel is not the same length.
   */
  private static void ensureValidChannel(Integer[][] channel) throws IllegalArgumentException {
    if (channel.length == 0 || channel[0].length == 0) {
      throw new IllegalArgumentException("Color channels must be non-empty.");
    }
    int length = channel[0].length;
    for (Integer[] row : channel) {
      if (row.length != length) {
        throw new IllegalArgumentException("Each row of a color channel must be the same length");
      }
      for (Integer value : row) {
        if (value < 0 || value > 255) {
          throw new IllegalArgumentException("Color values must be in the range of [0, 255].");
        }
      }
    }
  }

  /**
   * Determines whether a number of color channels have the same width and height.
   * @param channels the positive number of channels to check the dimensions of.
   *                 Channels must not have any empty rows or columns.
   * @return whether the color channels have the same width an height
   */
  private static boolean sameDimensions(Integer[][]... channels) {
    int height = channels[0].length;
    int width = channels[0][0].length;

    for (Integer[][] channel : channels) {
      if (channel.length != height) {
        return false;
      }
      for (Integer[] row : channel) {
        if (row.length != width) {
          return false;
        }
      }
    }
    return true;
  }
}

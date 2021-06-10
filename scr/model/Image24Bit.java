package model;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Represents a 24-bit image consisting of 3 8-bit red, green, and blue, color channels.
 */
public class Image24Bit implements Image {

//  private final Integer[][] red;
//  private final Integer[][] green;
//  private final Integer[][] blue;

  private final List<List<Pixel>> pixelList;

  public static final int MAX_VALUE = 255;

//  /**
//   * Constructs a new ImageImp from the red, green, and blue color channels.
//   *
//   * @param red   a two-dimensional array of the red values for every pixel in the image where each
//   *              inner array represents a row of pixel values
//   * @param green a two-dimensional array of the green values for every pixel in the image where
//   *              each inner array represents a row of pixel values
//   * @param blue  a two-dimensional array of the blue values for every pixel in the image where each
//   *              inner array represents a row of pixel values
//   * @throws IllegalArgumentException if any of the arguments are null, if each row in every color
//   *                                  channel is not the same length, if the color channels are
//   *                                  empty, or if color values are outside the range of [0, 255].
//   */
//  public Image24Bit(Integer[][] red, Integer[][] green, Integer[][] blue)
//      throws IllegalArgumentException {
//    ensureValidChannel(red);
//    ensureValidChannel(green);
//    ensureValidChannel(blue);
//    if (!sameDimensions(red, green, blue)) {
//      throw new IllegalArgumentException(
//          "Red, green, and blue channels must be the same size.");
//    }
//
//    this.red = red;
//    this.green = green;
//    this.blue = blue;
//  }

  public Image24Bit(List<List<Pixel>> pixelList) throws IllegalArgumentException {
    if (pixelList == null) {
      throw new IllegalArgumentException("The pixels cannot be null!");
    }
    this.pixelList = pixelList;
  }

  public Image24Bit(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("The image cannot be null!");
    }
    this.pixelList = image.getPixelList();
  }

  @Override
  public int getWidth() {
    return pixelList.get(0).size();
  }

  @Override
  public int getHeight() {
    return pixelList.size();
  }

  @Override
  public List<List<Pixel>> getPixelList() {
    return this.pixelList;
  }

  @Override
  public int getRedValueAt(int row, int col) {
    checkCoordinates(row, col);
    return pixelList.get(row).get(col).getRedValue();
  }

  @Override
  public int getGreenValueAt(int row, int col) {
    checkCoordinates(row, col);
    return pixelList.get(row).get(col).getGreenValue();
  }

  @Override
  public int getBlueValueAt(int row, int col) {
    checkCoordinates(row, col);
    return pixelList.get(row).get(col).getBlueValue();
  }

  @Override
  public int getValueAt(int row, int col, ColorChannel channel) {
    switch (channel) {
      case RED:
        return getRedValueAt(row, col);
      case GREEN:
        return getGreenValueAt(row, col);
      case BLUE:
        return getBlueValueAt(row, col);
      default:
        throw new IllegalArgumentException("Unsupported color channel");
    }
  }

  @Override
  public Pixel getPixelAt(int row, int col) {
    return pixelList.get(row).get(col);
  }

//  @Override
//  public Image fromRGB(Integer[][] red, Integer[][] green, Integer[][] blue) {
//    return new Image24Bit(red, green, blue);
//  }

  /**
   * Checks the validity of a color channel for an image. A valid color channel has the same number
   * of non-zero values in the range of [0, 255] in every row.
   *
   * @param channel the color channel to check the validity of
   * @throws IllegalArgumentException if the channel has no color values or if each row of the
   *                                  channel is not the same length.
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
        if (value < 0 || value > MAX_VALUE) {
          throw new IllegalArgumentException("Color values must be in the range of [0, 255].");
        }
      }
    }
  }

  /**
   * Determines whether a number of color channels have the same width and height.
   *
   * @param channels the positive number of channels to check the dimensions of. Channels must not
   *                 have any empty rows or columns.
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

  /**
   * Checks whether a coordinate is a valid index for a pixel in this image.
   *
   * @param row the row of the image from top to bottom
   * @param col the column of the image from left to right
   * @throws IllegalArgumentException if the row o
   */
  private void checkCoordinates(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth() ) {
      throw new IllegalArgumentException("Invalid row or column.");
    }
  }
}

package model;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractImage implements Image {
  private final Pixel[][] pixels;


  /**
   * Constructs a new ImageImp from the red, green, and blue color channels.
   *
   * @param pixels a two-dimensional array of the pixels in the image where each inner array
   *               represents a row of pixel values
   * @param clamp whether to clamp the pixel values to the allowed range. Values above the maximum
   *              value will be set to the maximum, values below the minimum value will be set to
   *              the minimum.
   * @throws IllegalArgumentException if any of the arguments are null, if each row in every color
   *                                  channel is not the same length, if the color channels are
   *                                  empty, or if color values are outside the valid range if
   *                                  clamping is false.
   */
  public AbstractImage(Pixel[][] pixels, boolean clamp) throws IllegalArgumentException {
    if (pixels == null) {
      throw new IllegalArgumentException("Pixels must not be null.");
    }
    ensureValidPixels(pixels);

    if (clamp) {
      this.pixels = clampPixels(pixels);
    } else {
      this.pixels = checkPixels(pixels);
    }
  }

  protected AbstractImage(Pixel[][] pixels) throws IllegalArgumentException {
    this(pixels, false);
  }

  @Override
  public int getWidth() {
    if (getHeight() == 0) {
      return 0;
    } else {
      return pixels[0].length;
    }
  }

  @Override
  public int getHeight() {
    return pixels.length;
  }

  @Override
  public int getRedValueAt(int row, int col) {
    checkCoordinates(row, col);
    return pixels[row][col].getRedValue();
  }

  @Override
  public int getGreenValueAt(int row, int col) {
    checkCoordinates(row, col);
    return pixels[row][col].getGreenValue();
  }

  @Override
  public int getBlueValueAt(int row, int col) {
    checkCoordinates(row, col);
    return pixels[row][col].getBlueValue();
  }

  @Override
  public int getValueAt(int row, int col, ColorChannel channel) {
    checkCoordinates(row, col);
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
    checkCoordinates(row, col);
    return pixels[row][col];
  }

  /**
   * Checks the validity of the given 2-dimensional array of pixels for an image.
   * A valid array of pixels has the same number of non-zero values in every row.
   *
   * @param pixels the pixels to check the validity of
   * @throws IllegalArgumentException if the channel has no color values or if each row of the
   *                                  channel is not the same length.
   */
  private static void ensureValidPixels(Pixel[][] pixels) throws IllegalArgumentException {
    if (pixels.length == 0 || pixels[0].length == 0) {
      throw new IllegalArgumentException("Image must contain at least one pixel.");
    }
    int length = pixels[0].length;
    for (Pixel[] row : pixels) {
      if (row.length != length) {
        throw new IllegalArgumentException("Each row of a color channel must be the same length");
      }
    }
  }

  private Pixel[][] checkPixels(Pixel[][] pixels) {
    for (Pixel[] row : pixels) {
      for (Pixel pixel : row) {
        if (pixel.minValue() < this.minValue() || pixel.maxValue() > this.maxValue()) {
          throw new IllegalArgumentException(
              String.format("Color values must be in the range [%d, %d]",
                  minValue(), maxValue()));
        }
      }
    }
    return pixels;
  }

  private Pixel[][] clampPixels(Pixel[][] pixels) {
    Pixel[][] clamped = new Pixel[pixels.length][pixels[0].length];
    for (int row = 0; row < pixels.length; row += 1) {
      for (int col = 0; col < pixels[0].length; col += 1) {
        clamped[row][col] = pixels[row][col].clamp(minValue(), maxValue());
      }
    }
    return clamped;
  }

  /**
   * Checks whether a coordinate is a valid index for a pixel in this image.
   *
   * @param row the row of the image from top to bottom
   * @param col the column of the image from left to right
   * @throws IllegalArgumentException if the row o
   */
  private void checkCoordinates(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
      throw new IllegalArgumentException("Invalid row or column.");
    }
  }

  /**
   * Returns the minimum allowed value for color values in this image
   * @return the minimum allowed color value
   */
  protected abstract int minValue();

  /**
   * Returns the maximum allowed value for color values in this image
   * @return the maximum allowed color value
   */
  protected abstract int maxValue();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AbstractImage)) {
      return false;
    }
    AbstractImage other = (AbstractImage) o;

    if (this.getHeight() != other.getHeight() || this.getWidth() != other.getWidth()) {
      return false;
    }

    for (int row = 0; row < getHeight(); row += 1) {
      for (int col = 0; col < getWidth(); col += 1) {
        if (!this.getPixelAt(row, col).equals(other.getPixelAt(row, col))) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(pixels);
  }
}

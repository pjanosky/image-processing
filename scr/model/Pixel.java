package model;

import java.util.Objects;

/**
 * Represents the color of one pixel in an image composed of 3 8-bit color channels.
 */
public class Pixel {

  private final int red;
  private final int green;
  private final int blue;

  public static final int MIN_VALUE = 0;
  public static final int MAX_VALUE = 255;

  /**
   * Constructs a new Pixel from red, green, and blue color values. Any color value outside the
   * allowed range of [MIN_VALUE, MAX_VALUE] are clamped. Clamping sets values higher than MAX_VALUE
   * to MAX_VALUE and sets values below MIN_VALUE to MIN_VALUE;
   *
   * @param red   the red value for the pixel
   * @param green the green value for the pixel
   * @param blue  the blue value for the pixel
   */
  public Pixel(int red, int green, int blue) {
    this.red = clamp(red);
    this.green = clamp(green);
    this.blue = clamp(blue);
  }

  public int getRedValue() {
    return red;
  }

  public int getGreenValue() {
    return green;
  }

  public int getBlueValue() {
    return blue;
  }

  public double[] getRgbVector() {
    return new double[]{
        (double) getRedValue(),
        (double) getGreenValue(),
        (double) getBlueValue()};
  }

  /**
   * Camps the given value to the range [MIN_VALUE, MAX_VALUE] allowed for color values in this
   * pixel. Values higher than MAX_VALUE are set to MAX_VALUE, values less than MIN_VALUE are set to
   * MIN_VALUE.
   *
   * @param value the value to check
   * @return whether the value is valid
   */
  private static int clamp(int value) {
    if (value > MAX_VALUE) {
      return MAX_VALUE;
    } else if (value < MIN_VALUE) {
      return MIN_VALUE;
    } else {
      return value;
    }
  }

  @Override
  public String toString() {
    return String.format("%d %d %d", getRedValue(), getGreenValue(), getBlueValue());
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Pixel)) {
      return false;
    }
    Pixel other = (Pixel) o;

    return this.getRedValue() == other.getRedValue()
        && this.getGreenValue() == other.getGreenValue()
        && this.getBlueValue() == other.getBlueValue();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getRedValue(), getGreenValue(), getBlueValue());
  }
}

/*
public class Pixel {

  private int[][] rgbMatrix;

  public Pixel(int[][] rgbMatrix) throws IllegalArgumentException {
    if (rgbMatrix == null) {
      throw new IllegalArgumentException("The given matrix cannot be null");
    }
    if (rgbMatrix.length != 3) {
      throw new IllegalArgumentException("Invalid matrix!");
    }
    this.rgbMatrix = rgbMatrix;
  }

  public Pixel(Color colour) {
    int[][] temp = {{0}, {0}, {0}};
    temp[0][0] = colour.getRed();
    temp[1][0] = colour.getGreen();
    temp[2][0] = colour.getBlue();

    this.rgbMatrix = temp;
  }

  public int[][] getRgbMatrix() {
    return rgbMatrix;
  }

  public int getRedValue() {
    return rgbMatrix[0][0];
  }

  public int getGreenValue() {
    return rgbMatrix[1][0];
  }

  public int getBlueValue() {
    return rgbMatrix[2][0];
  }

}
*/

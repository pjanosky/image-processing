package model;

import java.util.Objects;

/**
 * Represents the color of one pixel in an image composed of 3 8-bit color channels.
 */
public class Pixel {

  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructs a new Pixel from red, green, and blue color values. Any color value outside the
   * allowed range of [0, MAX_VALUE] are clamped. Clamping sets values higher than MAX_VALUE
   * to MAX_VALUE and sets values below 0 to 0;
   *
   * @param red   the red value for the pixel
   * @param green the green value for the pixel
   * @param blue  the blue value for the pixel
   */
  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
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

  public Pixel clamp(int min, int max) {
    int red = Math.max(min, Math.min(max, getRedValue()));
    int green = Math.max(min, Math.min(max, getRedValue()));
    int blue = Math.max(min, Math.min(max, getRedValue()));
    return new Pixel(red, green, blue);
  }

  public int maxValue() {
    return Math.max(getRedValue(), Math.max(getGreenValue(), getBlueValue()));
  }

  public int minValue() {
    return Math.min(getRedValue(), Math.min(getGreenValue(), getBlueValue()));
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

package model;

import java.util.Objects;

/**
 * Represents the color of one pixel in an image composed of 3 8-bit color channels.
 */
public class RgbPixel implements Pixel {

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
  public RgbPixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getRedValue() {
    return red;
  }

  @Override
  public int getGreenValue() {
    return green;
  }

  @Override
  public int getBlueValue() {
    return blue;
  }

  @Override
  public double[] getRgbVector() {
    return new double[]{
        (double) getRedValue(),
        (double) getGreenValue(),
        (double) getBlueValue()};
  }

  @Override
  public Pixel clamp(int min, int max) {
    int red = Math.max(min, Math.min(max, getRedValue()));
    int green = Math.max(min, Math.min(max, getGreenValue()));
    int blue = Math.max(min, Math.min(max, getBlueValue()));
    return new RgbPixel(red, green, blue);
  }

  @Override
  public Pixel fromRGB(int red, int green, int blue) {
    return new RgbPixel(red, green, blue);
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

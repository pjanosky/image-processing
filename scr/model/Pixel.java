package model;

/**
 * Represents one pixel in an image.
 *
 * <p>Different pixel implementations should be immutable from a client's perspective.
 *
 * <p>Different Implementation of Pixel should work together by using {@code getRedValue()}, {@code
 * getGreenValue()}, and {@code getBlueValue()} for the equals and hashCode methods.
 */
public interface Pixel {

  /**
   * Gets the red color channel value for this pixel.
   *
   * @return the red color channel value.
   */
  int getRedValue();

  /**
   * Gets the green color channel value for this pixel.
   *
   * @return the green color channel value.
   */
  int getGreenValue();

  /**
   * Gets the blue color channel value for this pixel.
   *
   * @return the blue color channel value;
   */
  int getBlueValue();

  /**
   * Generates a vector (represented as an array) containing the red, green, and blue color values
   * for this pixel in their respective order. The color channel values are stored as doubles.
   *
   * @return the vector containing red, green, and blue values for this pixel.
   */
  double[] getRgbVector();

  /**
   * Generates a new pixel equivalent to this one except with its color values clamped to the given
   * range [min, max]. Values greater than max are set to max, and values less than min are set to
   * min.
   *
   * @param min the minimum color value for the new pixel
   * @param max the maximum color value for the new pixel
   * @return the generated pixel with values clamped to the given range
   */
  Pixel clamp(int min, int max);

  /**
   * Generates a Pixel object as determined by concrete implementations.
   *
   * @param red   the red color value of the pixel
   * @param green the green color value of the pixel
   * @param blue  the blue color value so the pixel
   * @return the new Pixel
   */
  Pixel fromRGB(int red, int green, int blue);
}

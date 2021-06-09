package model;

/**
 * Represents the color of one pixel in an image with RGB values
 */
public class Pixel {

  private final int red;
  private final int green;
  private final int blue;

  // the maximum value for any color in the pixel
  public static final int MAX_VALUE = 255;

  /**
   * Constructs a new Pixel from the given red, green, and blue values.
   * @param red the red value of the pixel
   * @param green the green value of the pixel
   * @param blue the blue value of the pixel
   */
  public Pixel(int red, int green, int blue) {
    if (!validValue(red) || !validValue(green) || !validValue(blue)) {
      throw new IllegalArgumentException("Color values must be in the range [0, 255]");
    }

    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public int getRed() {
    return this.red;
  }

  public int getGreen() {
    return this.green;
  }

  public int getBlue() {
    return this.blue;
  }

  private static boolean validValue(int value) {
    return value > 0 && value < MAX_VALUE;
  }
}

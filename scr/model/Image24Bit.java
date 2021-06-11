package model;

/**
 * Represents a 24-bit image consisting of 3 8-bit red, green, and blue, color channels.
 */
public class Image24Bit extends AbstractImage {

  public static final int MIN_VALUE = 0;
  public static final int MAX_VALUE = 255;

  public Image24Bit(Pixel[][] pixels, boolean clamp) throws IllegalArgumentException {
    super(pixels, clamp);
  }


  public Image24Bit(Pixel[][] pixels) throws IllegalArgumentException {
    this(pixels, false);
  }


  @Override
  public Image fromPixels(Pixel[][] pixels, boolean clamp) {
    return new Image24Bit(pixels, clamp);
  }

  @Override
  public int minValue() {
    return MIN_VALUE;
  }

  @Override
  public int maxValue() {
    return MAX_VALUE;
  }
}
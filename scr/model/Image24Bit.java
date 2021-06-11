package model;

/**
 * Represents a 24-bit image consisting of 3 8-bit red, green, and blue, color channels.
 * Image24Bits are immutable.
 */
public class Image24Bit extends AbstractImage {

  // The minimum value allowed for color values with pixel in this image
  public static final int MIN_VALUE = 0;

  // The maximum value allowed for color values with pixel in this image
  public static final int MAX_VALUE = 255;

  /**
   * Constructs a new Image24Bit from a 2-dimensional array of pixels.
   *
   * @param pixels a two-dimensional array of the pixels in the image where each inner array
   *               represents a row of pixel valuesRows are from top to bottom, and pixels * in a
   *               row are from left to right.
   * @param clamp  whether to clamp the pixel values to the allowed range. Values above the maximum
   *               value will be set to the maximum, values below the minimum value will be set to
   *               the minimum.
   * @throws IllegalArgumentException if any of the arguments are null, if each row in every color
   *                                  channel is not the same length, if the color channels are
   *                                  empty, or if color values are outside the valid range if
   *                                  clamping is false.
   */
  public Image24Bit(Pixel[][] pixels, boolean clamp) throws IllegalArgumentException {
    super(pixels, clamp);
  }


  /**
   * A convenience constructor for creating a new image without clamping. Constructs a new image
   * from a 2-dimensional array of pixels.
   *
   * @param pixels a two-dimensional array of the pixels in the image where each inner array *
   *               represents a row of pixel values. Rows are from top to bottom, and pixels in a
   *               row are from left to right.
   * @throws IllegalArgumentException if the pixel color values are outside the allowed range for
   *                                  this image.
   */
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
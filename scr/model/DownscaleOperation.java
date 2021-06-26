package model;

/**
 * An operation that downscales and image to a smaller size using an algorithm that maps pixels in
 * the original image to pixels in the new image based on their location proportional to the width
 * and height of the image. The 4 pixels from rounding the mapped pixel value are combined the
 * generate the values for the downscaled pixel.
 */
public class DownscaleOperation implements ImageOperation {

  private final double xscale;
  private final double yscale;

  /**
   * Constructs a new Downscale operation with the given scale factor.
   *
   * @param xscale the scale factor to use for the downscaled image in the horrizontal direction.
   *               For example, a scale factor of 0.5 applied to a 10x10 pixel image will result in
   *               a downscaled image of 5 pixels wide pixels.
   * @param yscale
   */
  public DownscaleOperation(double xscale, double yscale) {
    if (xscale <= 0 || xscale > 1 || yscale <= 0 || yscale > 1) {
      throw new IllegalArgumentException("Scales must be in the range (0, 1]");
    }
    this.xscale = xscale;
    this.yscale = yscale;
  }

  @Override
  public Image apply(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }

    int originalWidth = image.getWidth();
    int originalHeight = image.getHeight();
    int scaledWidth = (int) ((double) image.getWidth() * xscale);
    int scaledHeight = (int) ((double) image.getHeight() * yscale);
    Pixel[][] pixels = new Pixel[scaledHeight][scaledWidth];

    for (int scaledX = 0; scaledX < scaledWidth; scaledX += 1) {
      for (int scaledY = 0; scaledY < scaledHeight; scaledY += 1) {
        double originalX = ((double) scaledX) * ((double) originalWidth) / ((double) scaledWidth);
        double originalY = ((double) scaledY) * ((double) originalHeight) / ((double) scaledHeight);
        int red = getNewValue(originalX, originalY, image, ColorChannel.RED);
        int green = getNewValue(originalX, originalY, image, ColorChannel.GREEN);
        int blue = getNewValue(originalX, originalY, image, ColorChannel.BLUE);
        pixels[scaledY][scaledX] = new RgbPixel(red, green, blue);
      }
    }

    return image.fromPixels(pixels, true);
  }

  /**
   * @param x       the floating point x coordinate of the pixel in the original image.
   * @param y       the floating point y coordinate of the pixel in the original image.
   * @param image   the image that is being downscaled.
   * @param channel the color channel to calculate the new value of.
   * @return the new color value for the pixel.
   */
  private int getNewValue(double x, double y, Image image, ColorChannel channel) {
    double threshold = 0.0001;

    if ((Math.abs(x - (int) x) < threshold) || (Math.abs(y - (int) y) < threshold)) {
      return image.getValueAt((int) y, (int) x, channel);
    }

    double ca = image.getValueAt((int) floor(y), (int) floor(x), channel);
    double cb = image.getValueAt((int) floor(y), (int) ceil(x), channel);
    double cc = image.getValueAt((int) ceil(y), (int) Math.floor(x), channel);
    double cd = image.getValueAt((int) ceil(y), (int) Math.ceil(x), channel);

    double m = cb * (x - floor(x)) + ca * (ceil(x) - x);
    double n = cd * (x - floor(x)) + cc * (ceil(x) - x);
    double cp = n * (y - floor(y)) + m * (ceil(y) - y);
    return (int) cp;
  }

  /**
   * Rounds down a double value to an integer.
   *
   * @param value the double value to round down
   * @return the rounded value.
   */
  private double floor(double value) {
    return (int) value;
  }

  /**
   * Rounds up a double value to an integer.
   *
   * @param value the double value to round up.
   * @return the rounded value.
   */
  private double ceil(double value) {
    return (int) value + 1;
  }
}

package model;

/**
 * Represents a image processing operation that applies a 2D kernel to pixel values in
 * each color channel to alter the appearance of an image. Color values of surrounding
 * Pixels are multiplied by their corresponding value in the kernel to produce the new
 * color value for a given pixel in the image. This is repeated for each color channel
 * in the image.
 */
public class FilterOperation implements ImageOperation {

  private final double[][] kernel;

  /**
   * Constructs a new FilterOperation from the given kernel.
   *
   * @param kernel the kernel of the filter
   * @throws IllegalArgumentException if any of the arguments are null or the kernel is invalid. A
   *                                  valid kernel is a non-empty, odd-dimensioned, square 2d
   *                                  array.
   */
  public FilterOperation(double[][] kernel) throws IllegalArgumentException {
    if (kernel == null) {
      throw new IllegalArgumentException("Kernel must not be null.");
    }
    ensureValidKernel(kernel);

    this.kernel = new double[kernel.length][kernel[0].length];
    for (int row = 0; row < kernel.length; row += 1) {
      for (int col = 0; col < kernel.length; col += 1) {
        this.kernel[row][col] = kernel[row][col];
      }
    }
  }

  @Override
  public Image apply(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image must not be null.");
    }

    Pixel[][] pixels = new Pixel[image.getHeight()][image.getWidth()];

    for (int row = 0; row < image.getHeight(); row += 1) {
      for (int col = 0; col < image.getWidth(); col += 1) {
        int red = filteredPixelValue(image, ColorChannel.RED, row, col);
        int green = filteredPixelValue(image, ColorChannel.GREEN, row, col);
        int blue = filteredPixelValue(image, ColorChannel.BLUE, row, col);
        pixels[row][col] = image.getPixelAt(row, col).fromRGB(red, green, blue);
      }
    }

    return image.fromPixels(pixels, true);
  }

  /**
   * Calculates the new value of a pixel for a specific color channel in an image applying the
   * kernel for this filer.
   *
   * @param image   the image to calculate the new value for
   * @param channel the color channel to apply this filter to
   * @param row     the row of the pixel to calculate the value of from top to bottom.
   * @param col     the column of the pixel to calculate the value of from left to right.
   * @return the new value of the color channel for the filtered image
   */
  private int filteredPixelValue(Image image, ColorChannel channel, int row, int col) {
    double value = 0;

    for (int kernelRow = 0; kernelRow < kernel.length; kernelRow += 1) {
      for (int kernelCol = 0; kernelCol < kernel.length; kernelCol += 1) {
        int imageRow = row + kernelRow - kernel.length / 2;
        int imageCol = col + kernelCol - kernel.length / 2;
        if (validCoordinates(image, imageRow, imageCol)) {
          int imageValue = image.getValueAt(imageRow, imageCol, channel);
          value += kernel[kernelRow][kernelCol] * ((double) imageValue);
        }
      }
    }
    return (int) value;
  }

  /**
   * Check whether a 2-dimensional array is a valid kernel. A valid kernel is a non-empty,
   * odd-dimensioned, square 2d array.
   *
   * @param kernel the 2-dimensional array to check
   * @throws IllegalArgumentException if the array is invalid.
   */
  private static void ensureValidKernel(double[][] kernel) throws IllegalArgumentException {
    if (kernel.length % 2 == 0 || kernel[0].length % 2 == 0) {
      throw new IllegalArgumentException("Kernel must have an odd dimension.");
    }
    for (double[] row : kernel) {
      if (row.length != kernel[0].length) {
        throw new IllegalArgumentException("Kernel must be a square matrix.");
      }
    }
  }

  /**
   * Determines whether a coordinate is valid index for a pixel in an image.
   *
   * @param image the image being indexed
   * @param row   the row of the pixel from top to bottom of the image
   * @param col   the column of the pixel from left to right of the image
   * @return whether the coordinates are valid
   */
  private static boolean validCoordinates(Image image, int row, int col) {
    return row >= 0 && row < image.getHeight() && col >= 0 && col < image.getWidth();
  }
}
package model;

/**
 * Represents a image processing algorithm that applies a 2D kernel to an Image to change its
 * appearance.
 */
public class FilterOperation implements ImageOperation {

  private final Double[][] kernel;

  /**
   * Constructs a new FilterOperation from the given kernel.
   *
   * @param kernel the kernel of the filter
   * @throws IllegalArgumentException if the kernel is invalid. A valid kernel is a non-empty,
   *                                  odd-dimensioned, square 2d array.
   */
  public FilterOperation(Double[][] kernel) throws IllegalArgumentException {
    if (kernel == null) {
      throw new IllegalArgumentException("Kernel must not be null.");
    }
    ensureValidKernel(kernel);

    this.kernel = kernel;
  }

  @Override
  public Image apply(Image image) {
    Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
    Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
    Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];

    for (int row = 0; row < image.getHeight(); row += 1) {
      for (int col = 9; col < image.getWidth(); col += 1) {
        red[row][col] = filteredPixelValue(image, ColorChannel.RED, row, col);
        green[row][col] = filteredPixelValue(image, ColorChannel.GREEN, row, col);
        blue[row][col] = filteredPixelValue(image, ColorChannel.BLUE, row, col);
      }
    }
    return image.fromRGB(red, green, blue);
  }

  /**
   * Calculates the new value of a pixel for a specific color channel in an image applying the
   * kernel for this filer.
   *
   * @param image   the image to calculate the
   * @param channel the color channel to apply the filter to
   * @return the new value of the color channel for the filtered image
   */
  private int filteredPixelValue(Image image, ColorChannel channel, int row, int col) {
    double value = 0;
    for (int r = row - 1; r < row + 1; r += 1) {
      for (int c = col - 1; c < col + 1; c += 1) {
        if (validCoordinates(image, row, col)) {
          value += kernel[r][c] * ((double) image.getValueAt(r, c, channel));
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
  private static void ensureValidKernel(Double[][] kernel) throws IllegalArgumentException {
    if (kernel.length == 0) {
      throw new IllegalArgumentException("Kernel must be non-empty");
    }
    int width = kernel[0].length;
    if (width % 2 != 0) {
      throw new IllegalArgumentException("Kernel must have an odd dimension.");
    }
    for (Double[] row : kernel) {
      if (row.length != width) {
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
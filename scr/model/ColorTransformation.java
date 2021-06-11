package model;

import java.util.Arrays;

public class ColorTransformation implements ImageOperation {

  private final double[][] matrix;

  public ColorTransformation(double[][] matrix) throws IllegalArgumentException {
    if (matrix == null) {
      throw new IllegalArgumentException("The matrix cannot be null!");
    }
    if (!validMatrix(matrix)) {
      throw new IllegalArgumentException("Matrix must be 3-by-3 in size");
    }

    this.matrix = new double[3][3];
    for (int row = 0; row < 3; row += 1) {
      for (int col = 0; col < 3; col += 1) {
        this.matrix[row][col] = matrix[row][col];
      }
    }
  }

  @Override
  public Image apply(Image image) {
    Pixel[][] pixels = new Pixel[image.getHeight()][image.getWidth()];

    for (int row = 0; row < image.getHeight(); row += 1) {
      for (int col = 0; col < image.getWidth(); col += 1) {
        double[] rgbVector = image.getPixelAt(row, col).getRgbVector();
        int red = (int) dotProduct(rgbVector, matrix[0]);
        int green = (int) dotProduct(rgbVector, matrix[1]);
        int blue = (int) dotProduct(rgbVector, matrix[2]);
        pixels[row][col] = new Pixel(red, green, blue);
      }
    }
    return image.fromPixels(pixels, true);
  }

  /**
   * Takes the dot product of two vectors represented as arrays.
   *
   * @param v1 the first vector
   * @param v2 the second vector
   * @return the dot product of the two vectors.
   * @throws IllegalArgumentException if the arrays are not the same length;
   */
  private static double dotProduct(double[] v1, double[] v2) throws IllegalArgumentException {
    if (v1.length != v2.length) {
      throw new IllegalArgumentException("Arrays must be the same length.");
    }
    double result = 0;
    for (int index = 0; index < v1.length; index += 1) {
      result += v1[index] * v2[index];
    }
    return result;
  }


  /**
   * Check whether a 2-dimensional array is a valid matrix for a color transformation. A valid
   * matrix is 3 by 3.
   *
   * @param matrix the 2-dimensional array to check
   * @return whether the matrix is valid
   */
  private static boolean validMatrix(double[][] matrix) {
    return matrix.length == 3
        && matrix[0].length == 3
        && matrix[1].length == 3
        && matrix[2].length == 3;
  }
}
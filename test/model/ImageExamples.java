package model;

/**
 * A class for generating different types of example images for testing.
 */
public class ImageExamples {

  /**
   * Generates a checkerboard image.
   * @param numRows the number of row in the checkerboard
   * @param numCols the number of cols in the checkerboard
   * @param width the width of each rectangle in pixels
   * @param height the height of each rectangle in pixels
   * @param color1 a Pixel representing the color of half of the rectangles
   * @param color2 a Pixel representing the color of the other half of the rectangles
   * @return the checkerboard image
   */
  public static Image checkerboard(int numRows, int numCols, int width, int height,
      Pixel color1, Pixel color2) {
    Pixel[][] pixels = new Pixel[numRows * height][numCols * width];
    for (int r = 0; r < numRows * height; r += 1) {
      for (int c = 0; c < numCols * width; c += 1) {
        int rowNum = r / height;
        int colNum = c / width;
        if ((rowNum + colNum) % 2 == 0) {
          pixels[r][c] = color1;
        } else {
          pixels[r][c] = color2;
        }
      }
    }
    return new Image24Bit(pixels);
  }

  /**
   * Generates a rainbow image
   * @param width the width of the image in pixels
   * @param stripeHeight the height of each color stripe in the rainbow
   * @return the rainbow image
   */
  public static Image rainbow(int width, int stripeHeight) {
    Pixel[] colors = {
        new Pixel(255, 0, 0),
        new Pixel(255, 100, 0),
        new Pixel(255, 255, 0),
        new Pixel(0, 255, 0),
        new Pixel(0, 0, 255),
        new Pixel(200, 0, 255),
    };

    Pixel[][] pixels = new Pixel[stripeHeight * colors.length][width];
    for (int r = 0; r < stripeHeight * colors.length; r += 1) {
      for (int c = 0; c < width; c += 1) {
        pixels[r][c] = colors[r / stripeHeight];
      }
    }
    return new Image24Bit(pixels);
  }
}

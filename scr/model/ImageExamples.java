package model;

import model.Image;
import model.Image24Bit;
import model.Pixel;
import model.RgbPixel;

/**
 * A class for generating different types of example images for testing.
 */
public class ImageExamples {

  /**
   * Generates a checkerboard image.
   *
   * @param numRows   the number of row in the checkerboard
   * @param numCols   the number of cols in the checkerboard
   * @param recWidth  the width of each rectangle in pixels
   * @param recHeight the height of each rectangle in pixels
   * @param color1    a Pixel representing the color of half of the rectangles
   * @param color2    a Pixel representing the color of the other half of the rectangles
   * @return the checkerboard image
   */
  public static Image checkerboard(int numRows, int numCols, int recWidth, int recHeight,
      Pixel color1, Pixel color2) {
    Pixel[][] pixels = new Pixel[numRows * recHeight][numCols * recWidth];
    for (int r = 0; r < numRows * recHeight; r += 1) {
      for (int c = 0; c < numCols * recWidth; c += 1) {
        int rowNum = r / recHeight;
        int colNum = c / recWidth;
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
   * Generates a rainbow image.
   *
   * @param width  the width of the image in pixels
   * @param height the height of the entire image. The strips will be rouble height / 6 pixels tall,
   *               except the last strip which my be cropped to fit the specified height.
   * @return the rainbow image
   */
  public static Image rainbow(int width, int height) {
    Pixel[] colors = {
        new RgbPixel(200, 0, 0),
        new RgbPixel(200, 75, 0),
        new RgbPixel(200, 200, 0),
        new RgbPixel(0, 200, 0),
        new RgbPixel(0, 0, 200),
        new RgbPixel(160, 0, 200),
    };

    int stripeHeight = height / 6;
    Pixel[][] pixels = new Pixel[stripeHeight * colors.length][width];
    for (int r = 0; r < height; r += 1) {
      for (int c = 0; c < width; c += 1) {
        pixels[r][c] = colors[r / stripeHeight];
      }
    }
    return new Image24Bit(pixels);
  }
}

import java.io.FileOutputStream;
import java.io.IOException;
import model.Image;
import model.Image24Bit;
import model.Pixel;
import model.PpmImportExporter;
import model.RgbPixel;

/**
 * A class for generating different types of example images for testing.
 */
public class ImageExamples {

  /**
   * Generates a checkerboard image.
   * @param numRows the number of row in the checkerboard
   * @param numCols the number of cols in the checkerboard
   * @param recWidth the width of each rectangle in pixels
   * @param recHeight the height of each rectangle in pixels
   * @param color1 a Pixel representing the color of half of the rectangles
   * @param color2 a Pixel representing the color of the other half of the rectangles
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
   * Generates a rainbow image
   * @param width the width of the image in pixels
   * @param stripeHeight the height of each color stripe in the rainbow
   * @return the rainbow image
   */
  public static Image rainbow(int width, int stripeHeight) {
    Pixel[] colors = {
        new RgbPixel(200, 0, 0),
        new RgbPixel(200, 75, 0),
        new RgbPixel(200, 200, 0),
        new RgbPixel(0, 200, 0),
        new RgbPixel(0, 0, 200),
        new RgbPixel(160, 0, 200),
    };

    Pixel[][] pixels = new Pixel[stripeHeight * colors.length][width];
    for (int r = 0; r < stripeHeight * colors.length; r += 1) {
      for (int c = 0; c < width; c += 1) {
        pixels[r][c] = colors[r / stripeHeight];
      }
    }
    return new Image24Bit(pixels);
  }

  // Example for how to create checkerboard images (DELETE LATER)
  public static void main(String[] args) {
//    Image image = checkerboard(5, 4, 5, 5,
//        new Pixel(0, 0, 0),
//        new Pixel(255, 255, 255));
    Image image = rainbow(10, 2);

    try {
      new PpmImportExporter().saveImage(new FileOutputStream("image.ppm"), image);
    } catch (IOException e) {
      System.out.println("Error saving image");
    }
  }
}

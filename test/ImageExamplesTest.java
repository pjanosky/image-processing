import static org.junit.Assert.assertEquals;

import model.Image;
import model.ImageExamples;
import model.Pixel;
import model.RgbPixel;
import org.junit.Test;

/**
 * Tests the ImageExamplesTest class.
 */
public class ImageExamplesTest {

  private final Pixel pixel1;
  private final Pixel pixel2;
  private final Pixel pixel3;

  private final Pixel red;
  private final Pixel orange;
  private final Pixel yellow;
  private final Pixel green;
  private final Pixel blue;
  private final Pixel purple;

  /**
   * Constructs a new ImageExamplesTest initializing all example test data.
   */
  public ImageExamplesTest() {
    pixel1 = new RgbPixel(23, 56, 195);
    pixel2 = new RgbPixel(205, 200, 34);
    pixel3 = new RgbPixel(57, 189, 120);

    red = new RgbPixel(200, 0, 0);
    orange = new RgbPixel(200, 75, 0);
    yellow = new RgbPixel(200, 200, 0);
    green = new RgbPixel(0, 200, 0);
    blue = new RgbPixel(0, 0, 200);
    purple = new RgbPixel(160, 0, 200);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckerBoardZeroNumRows() {
    ImageExamples.checkerboard(0, 1, 1, 1,
        pixel1, pixel2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckerBoardNegativeNumRows() {
    ImageExamples.checkerboard(-3, 1, 1, 1,
        pixel1, pixel2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckerBoardZeroNumCols() {
    ImageExamples.checkerboard(1, 0, 1, 1,
        pixel1, pixel2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckerBoardNegativeNumCols() {
    ImageExamples.checkerboard(1, -1, 1, 1,
        pixel1, pixel2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckerBoardZeroRecWidth() {
    ImageExamples.checkerboard(1, 1, 0, 1,
        pixel1, pixel2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckerBoardNegativeRecWidth() {
    ImageExamples.checkerboard(1, 1, -1, 1,
        pixel1, pixel2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckerBoardZeroRecHeight() {
    ImageExamples.checkerboard(1, 1, 1, 0,
        pixel1, pixel2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckerBoardNegativeRecHeight() {
    ImageExamples.checkerboard(1, 1, 1, -1,
        pixel1, pixel2);
  }

  @Test
  public void testCheckerboardSmallRectangles() {
    Image image = ImageExamples.checkerboard(3, 3, 1, 1,
        pixel1, pixel2);

    assertEquals(3, image.getHeight());
    assertEquals(3, image.getWidth());

    assertEquals(pixel1, image.getPixelAt(0, 0));
    assertEquals(pixel2, image.getPixelAt(0, 1));
    assertEquals(pixel1, image.getPixelAt(0, 2));
    assertEquals(pixel2, image.getPixelAt(1, 0));
    assertEquals(pixel1, image.getPixelAt(1, 1));
    assertEquals(pixel2, image.getPixelAt(1, 2));
    assertEquals(pixel1, image.getPixelAt(2, 0));
    assertEquals(pixel2, image.getPixelAt(2, 1));
    assertEquals(pixel1, image.getPixelAt(2, 2));
  }

  @Test
  public void testCheckerboardLargeRectangles() {
    Image image = ImageExamples.checkerboard(1, 2, 2, 3,
        pixel3, pixel1);

    assertEquals(3, image.getHeight());
    assertEquals(4, image.getWidth());
    assertEquals(pixel3, image.getPixelAt(0, 0));
    assertEquals(pixel3, image.getPixelAt(0, 1));
    assertEquals(pixel1, image.getPixelAt(0, 2));
    assertEquals(pixel1, image.getPixelAt(0, 3));
    assertEquals(pixel3, image.getPixelAt(1, 0));
    assertEquals(pixel3, image.getPixelAt(1, 1));
    assertEquals(pixel1, image.getPixelAt(1, 2));
    assertEquals(pixel1, image.getPixelAt(1, 3));
    assertEquals(pixel3, image.getPixelAt(2, 0));
    assertEquals(pixel3, image.getPixelAt(2, 1));
    assertEquals(pixel1, image.getPixelAt(2, 2));
    assertEquals(pixel1, image.getPixelAt(2, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRainbowZeroWidth() {
    ImageExamples.rainbow(0, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRainbowNegativeWidth() {
    ImageExamples.rainbow(-2, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRainbowZeroHeight() {
    ImageExamples.rainbow(6, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRainbowNegativeHeight() {
    ImageExamples.rainbow(6, -6);
  }

  @Test
  public void testRainbowMultipleOf6() {
    Image image = ImageExamples.rainbow(2, 6);

    assertEquals(2, image.getWidth());
    assertEquals(6, image.getHeight());

    assertEquals(red, image.getPixelAt(0, 0));
    assertEquals(red, image.getPixelAt(0, 1));
    assertEquals(orange, image.getPixelAt(1, 0));
    assertEquals(orange, image.getPixelAt(1, 1));
    assertEquals(yellow, image.getPixelAt(2, 0));
    assertEquals(yellow, image.getPixelAt(2, 1));
    assertEquals(green, image.getPixelAt(3, 0));
    assertEquals(green, image.getPixelAt(3, 1));
    assertEquals(blue, image.getPixelAt(4, 0));
    assertEquals(blue, image.getPixelAt(4, 1));
    assertEquals(purple, image.getPixelAt(5, 0));
    assertEquals(purple, image.getPixelAt(5, 1));
  }

  @Test
  public void testRainbowNotMultipleOf6() {
    Image image = ImageExamples.rainbow(1, 10);

    assertEquals(1, image.getWidth());
    assertEquals(10, image.getHeight());

    assertEquals(red, image.getPixelAt(0, 0));
    assertEquals(orange, image.getPixelAt(1, 0));
    assertEquals(yellow, image.getPixelAt(2, 0));
    assertEquals(green, image.getPixelAt(3, 0));
    assertEquals(blue, image.getPixelAt(4, 0));
    assertEquals(purple, image.getPixelAt(5, 0));
    assertEquals(purple, image.getPixelAt(6, 0));
    assertEquals(purple, image.getPixelAt(7, 0));
    assertEquals(purple, image.getPixelAt(8, 0));
    assertEquals(purple, image.getPixelAt(9, 0));
  }
}
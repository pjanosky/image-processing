import static org.junit.Assert.assertEquals;

import model.DownscaleOperation;
import model.Image;
import model.ImageExamples;
import model.RgbPixel;
import org.junit.Test;

public class DownscaleOperationTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidHighXScale() {
    new DownscaleOperation(1.1, 0.5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidZeroXScale() {
    new DownscaleOperation(0, 0.5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidNegativeXScale() {
    new DownscaleOperation(-0.5, 0.5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidHighYScale() {
    new DownscaleOperation(0.5, 1.1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidZeroYScale() {
    new DownscaleOperation(0.5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidNegativeYScale() {
    new DownscaleOperation(0.5, -0.5);
  }

  @Test
  public void testDownscaleScale1() {
    Image image = ImageExamples.rainbow(10, 12);
    Image output = new DownscaleOperation(1, 1).apply(image);
    assertEquals(image.getWidth(), output.getWidth());
    assertEquals(image.getHeight(), output.getHeight());
    assertEquals(image, output);
  }

  @Test
  public void testDownscaleScaleSameAspectRatio() {
    Image image = ImageExamples.rainbow(5, 7);
    Image output = new DownscaleOperation(0.5, 0.5).apply(image);
    assertEquals(2, output.getWidth());
    assertEquals(3, output.getHeight());
    assertEquals(new RgbPixel(200, 0, 0), output.getPixelAt(0, 0));
    assertEquals(new RgbPixel(200, 200, 0), output.getPixelAt(1, 0));
    assertEquals(new RgbPixel(0, 0, 200), output.getPixelAt(2, 0));
    assertEquals(new RgbPixel(200, 0, 0), output.getPixelAt(0, 1));
    assertEquals(new RgbPixel(133, 200, 0), output.getPixelAt(1, 1));
    assertEquals(new RgbPixel(106, 0, 200), output.getPixelAt(2, 1));
  }

  @Test
  public void testDownScaleDifferentAspectRatio() {
    Image image = ImageExamples.checkerboard(4, 6,2, 2,
        new RgbPixel(3, 38, 199),
        new RgbPixel(190, 255, 87));
    Image output = new DownscaleOperation(0.25, 0.5).apply(image);
    assertEquals(3, output.getWidth());
    assertEquals(2, output.getHeight());

    assertEquals(new RgbPixel(3, 38, 199), output.getPixelAt(0, 0));
    assertEquals(new RgbPixel(3, 38, 199), output.getPixelAt(1, 0));
    assertEquals(new RgbPixel(3, 38, 199), output.getPixelAt(0, 1));
    assertEquals(new RgbPixel(3, 38, 199), output.getPixelAt(1, 1));
    assertEquals(new RgbPixel(3, 38, 199), output.getPixelAt(0, 2));
    assertEquals(new RgbPixel(3, 38, 199), output.getPixelAt(1, 2));
  }
}
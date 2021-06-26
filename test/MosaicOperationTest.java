import static org.junit.Assert.assertEquals;

import java.util.Random;
import model.Image;
import model.ImageExamples;
import model.MosaicOperation;
import model.RgbPixel;
import org.junit.Test;

/**
 * Tests teh MosaicOperation class.
 */
public class MosaicOperationTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1NullRandom() {
    new MosaicOperation(100, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1ZeroSeeds() {
    new MosaicOperation(0, new Random(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1NegativeSeeds() {
    new MosaicOperation(-3, new Random(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2ZeroSeeds() {
    new MosaicOperation(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2NegativeSeeds() {
    new MosaicOperation(-3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyNullImage() {
    new MosaicOperation(500, new Random(0)).apply(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyMoreSeedsThanPixels() {
    Image image = ImageExamples.rainbow(10 ,10);
    new MosaicOperation(1000, new Random(0)).apply(image);
  }

  @Test
  public void testApplyRainbow() {
    Image image = ImageExamples.rainbow(3, 3);
    Image mosaic = new MosaicOperation(2, new Random(3)).apply(image);

    assertEquals(image.getHeight(), mosaic.getHeight());
    assertEquals(image.getWidth(), mosaic.getWidth());

    assertEquals(new RgbPixel(200, 30, 0), mosaic.getPixelAt(0, 0));
    assertEquals(new RgbPixel(200, 30, 0), mosaic.getPixelAt(0, 1));
    assertEquals(new RgbPixel(200, 30, 0), mosaic.getPixelAt(0, 2));
    assertEquals(new RgbPixel(200, 30, 0), mosaic.getPixelAt(1, 0));
    assertEquals(new RgbPixel(200, 30, 0), mosaic.getPixelAt(1, 1));
    assertEquals(new RgbPixel(200, 168, 0), mosaic.getPixelAt(1, 2));
    assertEquals(new RgbPixel(200, 168, 0), mosaic.getPixelAt(2, 0));
    assertEquals(new RgbPixel(200, 168, 0), mosaic.getPixelAt(2, 1));
    assertEquals(new RgbPixel(200, 168, 0), mosaic.getPixelAt(2, 2));
  }

  @Test
  public void testApplyLargerRainbow() {
    Image image = ImageExamples.rainbow(60, 60);
    Image mosaic = new MosaicOperation(2, new Random(1)).apply(image);

    assertEquals(image.getHeight(), mosaic.getHeight());
    assertEquals(image.getWidth(), mosaic.getWidth());

    assertEquals(new RgbPixel(200, 76, 0), mosaic.getPixelAt(2, 4));
    assertEquals(new RgbPixel(69, 81, 118), mosaic.getPixelAt(34, 17));
    assertEquals(new RgbPixel(200, 76, 0), mosaic.getPixelAt(0, 2));
    assertEquals(new RgbPixel(200, 76, 0), mosaic.getPixelAt(3, 55));
    assertEquals(new RgbPixel(69, 81, 118), mosaic.getPixelAt(40, 46));
    assertEquals(new RgbPixel(69, 81, 118), mosaic.getPixelAt(51, 2));
    assertEquals(new RgbPixel(200, 76, 0), mosaic.getPixelAt(22, 14));
    assertEquals(new RgbPixel(200, 76, 0), mosaic.getPixelAt(1, 11));
    assertEquals(new RgbPixel(69, 81, 118), mosaic.getPixelAt(42, 22));
  }
}
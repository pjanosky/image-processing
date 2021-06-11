import static org.junit.Assert.assertEquals;

import model.FilterOperation;
import model.Image;
import model.Image24Bit;
import model.ImageOperation;
import model.ImageOperationCreator;
import model.ImageOperationCreator.IMGOperationType;
import model.Pixel;
import org.junit.Test;

/**
 * Tests for the FilterOperation class.
 */
public class FilterOperationTest {

  // Tests constructing a FilterOperation object with a null kernel
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullKernel() {
    new FilterOperation(null);
  }

  // Tests constructing a FilterOperation object with an empty kernel
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEmptyKernel() {
    new FilterOperation(new double[0][0]);
  }

  // Tests constructing a FilterOperation object with an even-dimensioned kernel
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEvenDimensionKernel() {
    new FilterOperation(new double[][]{
        {1, 0},
        {1, 0}});
  }

  // Tests constructing a FilterOperation object with an non-square kernel
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNonSquareKernel() {
    new FilterOperation(new double[][]{
        {1, 0, 3},
        {13, 1},
        {3, 4, 5}});
  }

  // Tests applying a 3x3 blur FilterOperation to a checkerboard image
  @Test
  public void testApplyBlurToCheckerBoard() {
    Image original = ImageExamples.checkerboard(2, 2, 2, 2,
        new Pixel(255, 0, 0),
        new Pixel(0, 0, 255));
    Image filtered = ImageOperationCreator.create(IMGOperationType.BLUR).apply(original);

    assertEquals(new Pixel(143, 0, 0), filtered.getPixelAt(0, 0));
    assertEquals(new Pixel(143, 0, 47), filtered.getPixelAt(0, 1));
    assertEquals(new Pixel(47, 0, 143), filtered.getPixelAt(0, 2));
    assertEquals(new Pixel(0, 0, 143), filtered.getPixelAt(0, 3));
    assertEquals(new Pixel(143, 0, 47), filtered.getPixelAt(1, 0));
    assertEquals(new Pixel(159, 0, 95), filtered.getPixelAt(1, 1));
    assertEquals(new Pixel(95, 0, 159), filtered.getPixelAt(1, 2));
    assertEquals(new Pixel(47, 0, 143), filtered.getPixelAt(1, 3));
  }

  // Tests applying a 5x5 sharpening FilterOperation to a rainbow image.
  @Test
  public void testApplySharpenRainbow() {
    Image original = ImageExamples.rainbow(10, 2);
    Image filtered = ImageOperationCreator.create(IMGOperationType.SHARPEN).apply(original);

    assertEquals(new Pixel(225, 0, 0), filtered.getPixelAt(0, 0));
    assertEquals(new Pixel(225, 0, 0), filtered.getPixelAt(0, 9));
    assertEquals(new Pixel(240, 0, 225), filtered.getPixelAt(11, 0));
    assertEquals(new Pixel(240, 0, 225), filtered.getPixelAt(11, 9));
    assertEquals(new Pixel(225, 255, 0), filtered.getPixelAt(5, 0));
    assertEquals(new Pixel(0, 225, 0), filtered.getPixelAt(7, 0));
    assertEquals(new Pixel(225, 255, 0), filtered.getPixelAt(5, 9));
    assertEquals(new Pixel(0, 225, 0), filtered.getPixelAt(7, 9));
    assertEquals(new Pixel(255, 0, 225), filtered.getPixelAt(11, 6));
    assertEquals(new Pixel(225, 0, 0), filtered.getPixelAt(0, 6));
    assertEquals(new Pixel(255, 215, 0), filtered.getPixelAt(4, 6));
    assertEquals(new Pixel(200, 106, 0), filtered.getPixelAt(3, 7));
  }

  // Tests the post-filter clamping with pixels with values that are greater than the maximum
  // allowed value
  @Test
  public void testApplyHighClamping() {
    Pixel[][] pixels = {
        {new Pixel(100, 30, 10)},
        {new Pixel(45, 1, 244)}};
    ImageOperation whiten = new FilterOperation(new double[][]{{10000}});

    Image original = new Image24Bit(pixels);
    Image filtered = whiten.apply(original);

    assertEquals(new Pixel(255, 255, 255), filtered.getPixelAt(0, 0));
    assertEquals(new Pixel(255, 255, 255), filtered.getPixelAt(1, 0));
  }

  // Tests the post-filter clamping with pixels with values that are less than the minimum
  // allowed value
  @Test
  public void testApplyLowClamping() {
    Pixel[][] pixels = {
        {new Pixel(100, 30, 10)},
        {new Pixel(45, 1, 244)}};
    ImageOperation whiten = new FilterOperation(new double[][]{{-10000}});

    Image original = new Image24Bit(pixels);
    Image filtered = whiten.apply(original);

    assertEquals(new Pixel(0, 0, 0), filtered.getPixelAt(0, 0));
    assertEquals(new Pixel(0, 0, 0), filtered.getPixelAt(1, 0));
  }


  // Tests applying a FilterOperation to a null image
  @Test(expected = IllegalArgumentException.class)
  public void testApplyNullImage() {
    ImageOperationCreator.create(IMGOperationType.BLUR).apply(null);
  }
}
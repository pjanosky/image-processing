import static org.junit.Assert.assertEquals;

import model.FilterOperation;
import model.Image;
import model.Image24Bit;
import model.ImageOperation;
import model.ImageOperationCreator;
import model.ImageOperationCreator.IMGOperationType;
import model.Pixel;
import model.RgbPixel;
import org.junit.Test;

/**
 * Tests for the FilterOperation class.
 */
public class FilterOperationTest {

  private final Image exampleImage1;
  private final Image exampleImage2;

  /**
   * Constructs a new FilterOperationTest object initializing all example data for testing.
   */
  public FilterOperationTest() {
    this.exampleImage1 = new Image24Bit(new Pixel[][]{
        {new RgbPixel(100, 30, 10)},
        {new RgbPixel(45, 1, 244)}});

    this.exampleImage2 = new Image24Bit(new Pixel[][]{
        {new RgbPixel(100, 30, 10), new RgbPixel(34, 255, 200)},
        {new RgbPixel(45, 1, 244), new RgbPixel(2, 245, 199)}});
  }


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
        new RgbPixel(255, 0, 0),
        new RgbPixel(0, 0, 255));
    Image filtered = ImageOperationCreator.create(IMGOperationType.BLUR).apply(original);

    assertEquals(new RgbPixel(143, 0, 0), filtered.getPixelAt(0, 0));
    assertEquals(new RgbPixel(143, 0, 47), filtered.getPixelAt(0, 1));
    assertEquals(new RgbPixel(47, 0, 143), filtered.getPixelAt(0, 2));
    assertEquals(new RgbPixel(0, 0, 143), filtered.getPixelAt(0, 3));
    assertEquals(new RgbPixel(143, 0, 47), filtered.getPixelAt(1, 0));
    assertEquals(new RgbPixel(159, 0, 95), filtered.getPixelAt(1, 1));
    assertEquals(new RgbPixel(95, 0, 159), filtered.getPixelAt(1, 2));
    assertEquals(new RgbPixel(47, 0, 143), filtered.getPixelAt(1, 3));
  }

  // Tests applying a 5x5 sharpening FilterOperation to a rainbow image.
  @Test
  public void testApplySharpenRainbow() {
    Image original = ImageExamples.rainbow(10, 2);
    Image filtered = ImageOperationCreator.create(IMGOperationType.SHARPEN).apply(original);

    assertEquals(new RgbPixel(225, 0, 0), filtered.getPixelAt(0, 0));
    assertEquals(new RgbPixel(225, 0, 0), filtered.getPixelAt(0, 9));
    assertEquals(new RgbPixel(240, 0, 225), filtered.getPixelAt(11, 0));
    assertEquals(new RgbPixel(240, 0, 225), filtered.getPixelAt(11, 9));
    assertEquals(new RgbPixel(225, 255, 0), filtered.getPixelAt(5, 0));
    assertEquals(new RgbPixel(0, 225, 0), filtered.getPixelAt(7, 0));
    assertEquals(new RgbPixel(225, 255, 0), filtered.getPixelAt(5, 9));
    assertEquals(new RgbPixel(0, 225, 0), filtered.getPixelAt(7, 9));
    assertEquals(new RgbPixel(255, 0, 225), filtered.getPixelAt(11, 6));
    assertEquals(new RgbPixel(225, 0, 0), filtered.getPixelAt(0, 6));
    assertEquals(new RgbPixel(255, 215, 0), filtered.getPixelAt(4, 6));
    assertEquals(new RgbPixel(200, 106, 0), filtered.getPixelAt(3, 7));
  }

  // Tests the post-filter clamping with pixels with values that are greater than the maximum
  // allowed value
  @Test
  public void testApplyHighClamping() {
    ImageOperation whiten = new FilterOperation(new double[][]{{10000}});
    Image filtered = whiten.apply(exampleImage1);

    assertEquals(new RgbPixel(255, 255, 255), filtered.getPixelAt(0, 0));
    assertEquals(new RgbPixel(255, 255, 255), filtered.getPixelAt(1, 0));
  }

  // Tests the post-filter clamping with pixels with values that are less than the minimum
  // allowed value
  @Test
  public void testApplyLowClamping() {
    ImageOperation whiten = new FilterOperation(new double[][]{{-10000}});
    Image filtered = whiten.apply(exampleImage1);

    assertEquals(new RgbPixel(0, 0, 0), filtered.getPixelAt(0, 0));
    assertEquals(new RgbPixel(0, 0, 0), filtered.getPixelAt(1, 0));
  }

  // Test applying multiple operations to an image, in part to test the post-filter clamping
  // behavior for pixel color values
  @Test
  public void testApplyMultiOperation() {
    ImageOperation op1 = ImageOperationCreator.create(IMGOperationType.SHARPEN);
    ImageOperation op2 = new FilterOperation(new double[][]{{1.5}});
    ImageOperation op3 = new FilterOperation(new double[][]{{1.1}});
    ImageOperation op4 = ImageOperationCreator.create(IMGOperationType.BLUR);

    Image image1 = op1.apply(exampleImage2);
    Image image2 = op2.apply(image1);
    Image image3 = op3.apply(image2);
    Image image4 = op4.apply(image3);

    assertEquals(new RgbPixel(120, 155, 170), image1.getPixelAt(0, 0));
    assertEquals(new RgbPixel(70, 255, 255), image1.getPixelAt(0, 1));
    assertEquals(new RgbPixel(79, 133, 255), image1.getPixelAt(1, 0));
    assertEquals(new RgbPixel(46, 255, 255), image1.getPixelAt(1, 1));

    assertEquals(new RgbPixel(180, 232, 255), image2.getPixelAt(0, 0));
    assertEquals(new RgbPixel(105, 255, 255), image2.getPixelAt(0, 1));
    assertEquals(new RgbPixel(118, 199, 255), image2.getPixelAt(1, 0));
    assertEquals(new RgbPixel(69, 255, 255), image2.getPixelAt(1, 1));

    assertEquals(new RgbPixel(198, 255, 255), image3.getPixelAt(0, 0));
    assertEquals(new RgbPixel(115, 255, 255), image3.getPixelAt(0, 1));
    assertEquals(new RgbPixel(129, 218, 255), image3.getPixelAt(1, 0));
    assertEquals(new RgbPixel(75, 255, 255), image3.getPixelAt(1, 1));

    assertEquals(new RgbPixel(84, 138, 143), image4.getPixelAt(0, 0));
    assertEquals(new RgbPixel(70, 141, 143), image4.getPixelAt(0, 1));
    assertEquals(new RgbPixel(73, 134, 143), image4.getPixelAt(1, 0));
    assertEquals(new RgbPixel(61, 138, 143), image4.getPixelAt(1, 1));
  }

  // Test applying a filter that doesn't change an image with a large kernel matrix
  @Test
  public void testApplyIdentityOperation() {
    ImageOperation identity = new FilterOperation(new double[][]{
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0}});
    Image filtered = identity.apply(exampleImage2);
    assertEquals(exampleImage2, filtered);
  }


  // Tests applying a FilterOperation to a null image
  @Test(expected = IllegalArgumentException.class)
  public void testApplyNullImage() {
    ImageOperationCreator.create(IMGOperationType.BLUR).apply(null);
  }
}
import static org.junit.Assert.assertEquals;

import model.ColorTransformation;
import model.FilterOperation;
import model.Image;
import model.Image24Bit;
import model.ImageOperation;
import model.ImageOperationCreator;
import model.ImageOperationCreator.OperationType;
import model.Pixel;
import model.RgbPixel;
import org.junit.Test;

/**
 * Tests the ColorTransformation class.
 */
public class ColorTransformationTest {

  private final Image exampleImage1;
  private final Image exampleImage2;

  /**
   * Constructs a new ColorTransformationTest object initializing all example data for testing.
   */
  public ColorTransformationTest() {
    this.exampleImage1 = new Image24Bit(new Pixel[][]{
        {new RgbPixel(100, 30, 10)},
        {new RgbPixel(45, 1, 244)}});

    this.exampleImage2 = new Image24Bit(new Pixel[][]{
        {new RgbPixel(100, 30, 10), new RgbPixel(34, 255, 200)},
        {new RgbPixel(45, 1, 244), new RgbPixel(2, 245, 199)}});
  }

  // Test constructing a ColorTransformation object with null matrix
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullMatrix() {
    //null value for matrix
    new ColorTransformation(null);
  }

  // Test constructing a ColorTransformation object with matrix with an invalid number of columns
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidColumnsMatrix() {
    double[][] matrix = {
        {1, 1, 1},
        {1, 1},
        {1, 1, 1}};
    new ColorTransformation(matrix);
  }

  // Test constructing a ColorTransformation object with matrix with an invalid number of rows
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidRowsMatrix() {
    double[][] matrix = {
        {1, 1, 1},
        {1, 1, 1}};
    new ColorTransformation(matrix);
  }

  // Test constructing a ColorTransformation object with matrix with an invalid size
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidSizedMatrix() {
    double[][] matrix = {
        {1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1}};
    new ColorTransformation(matrix);
  }

  @Test(expected = IllegalArgumentException.class)
  public void applyColorTransformationNullImage() {
    ImageOperationCreator.create(OperationType.GREYSCALE).apply(null);
  }

  // Tests applying a greyscale transformation to a rainbow image
  @Test
  public void testApplyGreyscaleToRainbow() {
    Image original = ImageExamples.rainbow(1, 1);
    Image transformed = ImageOperationCreator.create(OperationType.GREYSCALE).apply(original);

    assertEquals(new RgbPixel(42, 42, 42),
        transformed.getPixelAt(0, 0));
    assertEquals(new RgbPixel(96, 96, 96),
        transformed.getPixelAt(1, 0));
    assertEquals(new RgbPixel(185, 185, 185),
        transformed.getPixelAt(2, 0));
    assertEquals(new RgbPixel(143, 143, 143),
        transformed.getPixelAt(3, 0));
    assertEquals(new RgbPixel(14, 14, 14),
        transformed.getPixelAt(4, 0));
    assertEquals(new RgbPixel(48, 48, 48),
        transformed.getPixelAt(5, 0));
  }

  // Tests applying a sepia transformation to a rainbow image
  @Test
  public void testApplySepiaToRainbow() {
    Image original = ImageExamples.rainbow(1, 1);
    Image transformed = ImageOperationCreator.create(OperationType.SEPIA).apply(original);

    assertEquals(new RgbPixel(78, 69, 54),
        transformed.getPixelAt(0, 0));
    assertEquals(new RgbPixel(136, 121, 94),
        transformed.getPixelAt(1, 0));
    assertEquals(new RgbPixel(232, 207, 161),
        transformed.getPixelAt(2, 0));
    assertEquals(new RgbPixel(153, 137, 106),
        transformed.getPixelAt(3, 0));
    assertEquals(new RgbPixel(37, 33, 26),
        transformed.getPixelAt(4, 0));
    assertEquals(new RgbPixel(100, 89, 69),
        transformed.getPixelAt(5, 0));
  }

  // test the post-transformation clamping with pixels that have values greater than the maximum
  // allowed value
  @Test
  public void testApplyHighClamping() {
    ImageOperation whiten = new FilterOperation(new double[][]{
        {1000, 1000, 1000},
        {1000, 1000, 1000},
        {1000, 1000, 1000}});
    Image filtered = whiten.apply(exampleImage1);

    assertEquals(new RgbPixel(255, 255, 255), filtered.getPixelAt(0, 0));
    assertEquals(new RgbPixel(255, 255, 255), filtered.getPixelAt(1, 0));
  }

  // test the post-transformation clamping with pixels that have values less than the minimum
  // allowed value
  @Test
  public void testApplyLowClamping() {
    ImageOperation whiten = new FilterOperation(new double[][]{
        {-1000, -1000, -1000},
        {-1000, -1000, -1000},
        {-1000, -1000, -1000}});
    Image filtered = whiten.apply(exampleImage1);

    assertEquals(new RgbPixel(0, 0, 0), filtered.getPixelAt(0, 0));
    assertEquals(new RgbPixel(0, 0, 0), filtered.getPixelAt(1, 0));
  }

  // Test applying multiple operations to an image, in part to test the post-transformation
  // clamping behavior for pixel color values
  @Test
  public void testApplyMultiOperation() {
    ImageOperation op1 = ImageOperationCreator.create(OperationType.SEPIA);
    ImageOperation op2 = new ColorTransformation(new double[][]{
        {1.2, 0.1, 0.1},
        {0.1, 1.2, 0.1},
        {0.1, 0.1, 1.2}});
    ImageOperation op3 = new ColorTransformation(new double[][]{
        {1.1, 0.1, 0.1},
        {0.1, 1.1, 0.1},
        {0.1, 0.1, 1.1}});
    ImageOperation op4 = ImageOperationCreator.create(OperationType.GREYSCALE);

    Image image1 = op1.apply(exampleImage2);
    Image image2 = op2.apply(image1);
    Image image3 = op3.apply(image2);
    Image image4 = op4.apply(image3);

    assertEquals(new RgbPixel(64, 57, 44), image1.getPixelAt(0, 0));
    assertEquals(new RgbPixel(247, 220, 171), image1.getPixelAt(0, 1));
    assertEquals(new RgbPixel(64, 57, 44), image1.getPixelAt(1, 0));
    assertEquals(new RgbPixel(226, 202, 157), image1.getPixelAt(1, 1));

    assertEquals(new RgbPixel(86, 79, 64), image2.getPixelAt(0, 0));
    assertEquals(new RgbPixel(255, 255, 251), image2.getPixelAt(0, 1));
    assertEquals(new RgbPixel(86, 79, 64), image2.getPixelAt(1, 0));
    assertEquals(new RgbPixel(255, 255, 231), image2.getPixelAt(1, 1));

    assertEquals(new RgbPixel(108, 101, 86), image3.getPixelAt(0, 0));
    assertEquals(new RgbPixel(255, 255, 255), image3.getPixelAt(0, 1));
    assertEquals(new RgbPixel(108, 101, 86), image3.getPixelAt(1, 0));
    assertEquals(new RgbPixel(255, 255, 255), image3.getPixelAt(1, 1));

    assertEquals(new RgbPixel(101, 101, 101), image4.getPixelAt(0, 0));
    assertEquals(new RgbPixel(254, 254, 254), image4.getPixelAt(0, 1));
    assertEquals(new RgbPixel(101, 101, 101), image4.getPixelAt(1, 0));
    assertEquals(new RgbPixel(254, 254, 254), image4.getPixelAt(1, 1));
  }

  // Test applying a transformation that doesn't change an image
  @Test
  public void testApplyIdentityOperation() {
    ImageOperation identity = new ColorTransformation(new double[][]{
        {1, 0, 0},
        {0, 1, 0},
        {0, 0, 1}});
    Image filtered = identity.apply(exampleImage2);
    assertEquals(exampleImage2, filtered);
  }
}
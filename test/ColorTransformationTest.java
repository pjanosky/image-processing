import static org.junit.Assert.assertEquals;

import model.ColorTransformation;
import model.FilterOperation;
import model.Image;
import model.Image24Bit;
import model.ImageOperation;
import model.ImageOperationCreator;
import model.ImageOperationCreator.IMGOperationType;
import model.Pixel;
import model.RgbPixel;
import org.junit.Test;

public class ColorTransformationTest {

  // Test constructing a ColorTransformation object with null matrix
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithInvalidMatrix() {
    //null value for matrix
    new ColorTransformation(null);

    //non 3 by 3 matrix in size:
    new ColorTransformation(new double[3][1]);
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
    ImageOperationCreator.create(IMGOperationType.GREYSCALE).apply(null);
  }

  // Tests applying a greyscale transformation to a rainbow image
  @Test
  public void testApplyGreyscaleToRainbow() {
    Image original = ImageExamples.rainbow(1, 1);
    Image transformed = ImageOperationCreator.create(IMGOperationType.GREYSCALE).apply(original);

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
    Image transformed = ImageOperationCreator.create(IMGOperationType.SEPIA).apply(original);

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
    Pixel[][] pixels = {
        {new RgbPixel(100, 30, 10)},
        {new RgbPixel(45, 1, 244)}};
    ImageOperation whiten = new FilterOperation(new double[][]{
        {1000, 1000, 1000},
        {1000, 1000, 1000},
        {1000, 1000, 1000}});

    Image original = new Image24Bit(pixels);
    Image filtered = whiten.apply(original);

    assertEquals(new RgbPixel(255, 255, 255), filtered.getPixelAt(0, 0));
    assertEquals(new RgbPixel(255, 255, 255), filtered.getPixelAt(1, 0));
  }

  // test the post-transformation clamping with pixels that have values less than the minimum
  // allowed value
  @Test
  public void testApplyLowClamping() {
    Pixel[][] pixels = {
        {new RgbPixel(100, 30, 10)},
        {new RgbPixel(45, 1, 244)}};
    ImageOperation whiten = new FilterOperation(new double[][]{
        {-1000, -1000, -1000},
        {-1000, -1000, -1000},
        {-1000, -1000, -1000}});

    Image original = new Image24Bit(pixels);
    Image filtered = whiten.apply(original);

    assertEquals(new RgbPixel(0, 0, 0), filtered.getPixelAt(0, 0));
    assertEquals(new RgbPixel(0, 0, 0), filtered.getPixelAt(1, 0));
  }
}
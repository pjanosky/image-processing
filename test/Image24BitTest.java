import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import model.ColorChannel;
import model.Image;
import model.Image24Bit;
import model.Pixel;
import model.RgbPixel;
import org.junit.Test;

/**
 * Tests for the Image24Bit class
 */
public class Image24BitTest {

  private final Image exampleImage;

  public Image24BitTest() {
    Pixel[][] examplePixelMatrix = new Pixel[][]{
        {new RgbPixel(10, 10, 10)},
        {new RgbPixel(12, 12, 12)},
        {new RgbPixel(13, 12, 13)}};
    this.exampleImage = new Image24Bit(examplePixelMatrix);
  }

  // Test constructing a matrix with a null matrix.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullParameter() {
    new Image24Bit(null);
  }

  // Tests constructing an image with an empty matrix.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEmptyMatrix() {
    Pixel[][] emptyPixelMatrix = new Pixel[0][0];
    new Image24Bit(emptyPixelMatrix);
  }

  // Tests constructing an image where one of the sub-matrices is empty.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEmptySubMatrix() {
    Pixel[][] emptySubMatrix = new Pixel[3][0];
    new Image24Bit(emptySubMatrix);
  }

  // Tests constructing an image were the rows of pixels don't have the same length
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNonRectangularMatrix() {
    Pixel[][] unequalSubMatrix = {{new RgbPixel(10, 10, 10)},
        {new RgbPixel(12, 12, 12), new RgbPixel(13, 12, 13)}, {}};
    new Image24Bit(unequalSubMatrix);
  }

  // Test the getWidth method.
  @Test
  public void testGetWidth() {
    assertEquals(1, exampleImage.getWidth());
  }

  // Test the getHeight method.
  @Test
  public void testGetHeight() {
    assertEquals(3, exampleImage.getHeight());
  }

  // Tests the getRedValue at method with a invalid negative row index.
  @Test(expected = IllegalArgumentException.class)
  public void testGetRedValueAtNegativeRow() {
    //negative row index:
    exampleImage.getRedValueAt(-1, 0);
  }

  // Tests the getRedValue at method with a invalid row index that is too large.
  @Test(expected = IllegalArgumentException.class)
  public void testGetRedValueAtLargeRow() {
    exampleImage.getRedValueAt(3, 0);
  }

  // Tests the getRedValue at method with a invalid negative column index.
  @Test(expected = IllegalArgumentException.class)
  public void testGetRedValueAtNegativeCol() {
    exampleImage.getRedValueAt(1, -1);
  }

  // Tests the getRedValue at method with a invalid column index that is too large.
  @Test(expected = IllegalArgumentException.class)
  public void testGetRedValueAtLargeCol() {
    exampleImage.getRedValueAt(1, 2);
  }

  // Tests the getRedValue at method with a valid row and column.
  @Test
  public void testGetRedValue() {
    assertEquals(12,
        exampleImage.getRedValueAt(1, 0));
  }

  // Tests the getGreenValueAt at method with a invalid negative row index.
  @Test(expected = IllegalArgumentException.class)
  public void testGetGreenValueAtNegativeRow() {
    //negative row index:
    exampleImage.getGreenValueAt(-1, 0);
  }

  // Tests the getGreenValueAt at method with a invalid row index that is too large.
  @Test(expected = IllegalArgumentException.class)
  public void testGetGreenValueAtLargeRow() {
    exampleImage.getGreenValueAt(3, 0);
  }

  // Tests the getGreenValueAt at method with a invalid negative column index.
  @Test(expected = IllegalArgumentException.class)
  public void testGetGreenValueAtNegativeCol() {
    exampleImage.getGreenValueAt(1, -1);
  }

  // Tests the getGreenValueAt at method with a invalid column index that is too large.
  @Test(expected = IllegalArgumentException.class)
  public void testGetGreenValueAtLargeCol() {
    exampleImage.getGreenValueAt(1, 2);
  }

  // Tests the getGreenValueAt at method with a valid row and column.
  @Test
  public void testGetGreenValue() {
    assertEquals(12,
        exampleImage.getGreenValueAt(1, 0));
  }

  // Tests the getBlueValueAt at method with a invalid negative row index.
  @Test(expected = IllegalArgumentException.class)
  public void testGetBlueValueAtNegativeRow() {
    //negative row index:
    exampleImage.getBlueValueAt(-1, 0);
  }

  // Tests the getBlueValueAt at method with a invalid row index that is too large.
  @Test(expected = IllegalArgumentException.class)
  public void testGetBlueValueAtLargeRow() {
    exampleImage.getBlueValueAt(3, 0);
  }

  // Tests the getBlueValueAt at method with a invalid negative column index.
  @Test(expected = IllegalArgumentException.class)
  public void testGetBlueValueAtNegativeCol() {
    exampleImage.getBlueValueAt(1, -1);
  }

  // Tests the getBlueValueAt at method with a invalid column index that is too large.
  @Test(expected = IllegalArgumentException.class)
  public void testGetBlueValueAtLargeCol() {
    exampleImage.getBlueValueAt(1, 2);
  }

  // Tests the getBlueValueAt at method with a valid row and column.
  @Test
  public void testGetBlueValue() {
    assertEquals(12,
        exampleImage.getBlueValueAt(1, 0));
  }

  // Tests the getValueAt method with a invalid negative row index.
  @Test(expected = IllegalArgumentException.class)
  public void testGetValueAtWithNegativeRow() {
    exampleImage.getValueAt(-1, 0, ColorChannel.RED);
  }

  // Tests the getValueAt method with a invalid row index that is too large.
  @Test(expected = IllegalArgumentException.class)
  public void testGetValueAtWithLargeRow() {
    exampleImage.getValueAt(3, 0, ColorChannel.RED);
  }

  // Tests the getValueAt method with a invalid negative column index.
  @Test(expected = IllegalArgumentException.class)
  public void testGetValueAtWithNegativeCol() {
    exampleImage.getValueAt(1, -1, ColorChannel.RED);
  }

  // Tests the getValueAt method with a invalid colum index that is too large.
  @Test(expected = IllegalArgumentException.class)
  public void testGetValueAtWithLargeCol() {
    exampleImage.getValueAt(1, 2, ColorChannel.RED);
  }

  // Tests the getValueAt method with a null color channel.
  @Test(expected = IllegalArgumentException.class)
  public void testGetValueAtWithNullColorChannel() {
    exampleImage.getValueAt(1, 1, null);
  }

  // Test the getValueAt method with valid arguments.
  @Test
  public void testGetValueAt() {
    // red value
    assertEquals(12, exampleImage.getValueAt(1, 0,
        ColorChannel.RED));

    // green value
    assertEquals(12, exampleImage.getValueAt(1, 0,
        ColorChannel.GREEN));

    // blue value
    assertEquals(12, exampleImage.getValueAt(1, 0,
        ColorChannel.BLUE));

  }

  // Test the equals method with the same image object.
  @Test
  public void testEqualsSameImage() {
    Image image = new Image24Bit(new Pixel[][]{
        {new RgbPixel(28, 199, 201), new RgbPixel(91, 49, 120)},
        {new RgbPixel(122, 255, 213), new RgbPixel(12, 1, 233)}});

    assertEquals(image, image);
  }

  // Test the equals method with images with the same pixel color values.
  @Test
  public void testEqualsEqualImages() {
    Image image1 = new Image24Bit(new Pixel[][]{
        {new RgbPixel(28, 199, 201), new RgbPixel(91, 49, 120)},
        {new RgbPixel(122, 255, 213), new RgbPixel(12, 1, 233)}});
    Image image2 = new Image24Bit(new Pixel[][]{
        {new RgbPixel(28, 199, 201), new RgbPixel(91, 49, 120)},
        {new RgbPixel(122, 255, 213), new RgbPixel(12, 1, 233)}});

    assertEquals(image1, image2);
    assertEquals(image2, image1);
  }

  // Tests the equals method with images with different pixel color values.
  @Test
  public void testEqualsDifferentImages() {
    Image image1 = new Image24Bit(new Pixel[][]{
        {new RgbPixel(28, 199, 201), new RgbPixel(91, 49, 120)},
        {new RgbPixel(122, 255, 0), new RgbPixel(12, 1, 233)}});
    Image image2 = new Image24Bit(new Pixel[][]{
        {new RgbPixel(28, 199, 201), new RgbPixel(91, 49, 120)},
        {new RgbPixel(122, 255, 213), new RgbPixel(12, 1, 233)}});

    assertNotEquals(image1, image2);
    assertNotEquals(image2, image1);
  }

  // Tests the equals method with images that have a different width or height.
  @Test
  public void testEqualsDifferentSizedImages() {
    Image image1 = new Image24Bit(new Pixel[][]{
        {new RgbPixel(28, 199, 201), new RgbPixel(91, 49, 120)},
        {new RgbPixel(122, 255, 0), new RgbPixel(12, 1, 233)}});
    Image image2 = new Image24Bit(new Pixel[][]{
        {new RgbPixel(28, 199, 201)},
        {new RgbPixel(122, 255, 213)}});

    assertNotEquals(image1, image2);
    assertNotEquals(image2, image1);
  }

  // Tests the hashCode method.
  @Test
  public void testHashCode() {
    Pixel[][] pixels1 = {
        {new RgbPixel(28, 199, 201), new RgbPixel(91, 49, 120)},
        {new RgbPixel(122, 255, 0), new RgbPixel(12, 1, 233)}};
    Pixel[][] pixels2 = {
        {new RgbPixel(28, 199, 201)},
        {new RgbPixel(122, 255, 213)}};
    Image image1 = new Image24Bit(pixels1);
    Image image2 = new Image24Bit(pixels2);

    assertEquals(Arrays.deepHashCode(pixels1), image1.hashCode());
    assertEquals(Arrays.deepHashCode(pixels2), image2.hashCode());
  }
}


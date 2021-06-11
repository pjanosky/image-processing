import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import model.ColorChannel;
import model.Image;
import model.Image24Bit;
import model.Pixel;
import org.junit.Test;

public class Image24BitTest {

  Pixel[][] examplePixelMatrix = {{new Pixel(10, 10, 10)},
      {new Pixel(12, 12, 12)}, {new Pixel(13, 12, 13)}};
  Image exampleImage = new Image24Bit(examplePixelMatrix);

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullParameter() {
    new Image24Bit(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithInvalidPixelMatrix() {
    //matrix is empty
    Pixel[][] emptyPixelMatrix = new Pixel[0][0];
    new Image24Bit(emptyPixelMatrix);

    //the submatrix is empty
    Pixel[][] emptySubMatrix = new Pixel[3][0];
    new Image24Bit(emptySubMatrix);

    //each submatrix has unequal length
    Pixel[][] unequalSubMatrix = {{new Pixel(10, 10, 10)},
        {new Pixel(12, 12, 12), new Pixel(13, 12, 13)}, {}};
    new Image24Bit(unequalSubMatrix);
  }

  @Test
  public void testGetWidth() {
    assertEquals(1, exampleImage.getWidth());
  }

  @Test
  public void testGetHeight() {
    assertEquals(3, exampleImage.getHeight());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetRedValueAtWithInvalidRowAndCol() {
    //negative row index:
    exampleImage.getRedValueAt(-1, 0);

    //row index out of bounds:
    exampleImage.getRedValueAt(3, 0);

    //negative column index:
    exampleImage.getRedValueAt(1, -1);

    //column index out of bounds:
    exampleImage.getRedValueAt(1, 2);
  }

  @Test
  public void testGetRedValue() {
    assertEquals(12,
        exampleImage.getRedValueAt(1, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetGreenValueAtWithInvalidRowAndCol() {
    //negative row index:
    exampleImage.getGreenValueAt(-1, 0);

    //row index out of bounds:
    exampleImage.getGreenValueAt(3, 0);

    //negative column index:
    exampleImage.getGreenValueAt(1, -1);

    //column index out of bounds:
    exampleImage.getGreenValueAt(1, 2);
  }

  @Test
  public void testGetGreenValue() {
    assertEquals(12,
        exampleImage.getGreenValueAt(1, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetBlueValueAtWithInvalidRowAndCol() {
    //negative row index:
    exampleImage.getBlueValueAt(-1, 0);

    //row index out of bounds:
    exampleImage.getBlueValueAt(3, 0);

    //negative column index:
    exampleImage.getBlueValueAt(1, -1);

    //column index out of bounds:
    exampleImage.getBlueValueAt(1, 2);
  }

  @Test
  public void testGetBlueValue() {
    assertEquals(12,
        exampleImage.getBlueValueAt(1, 0));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetValueAtWithInvalidRowsAndCols() {
    //negative row index:
    exampleImage.getValueAt(-1,0, ColorChannel.RED);

    //row index out of bounds:
    exampleImage.getValueAt(3,0, ColorChannel.RED);

    //negative column index:
    exampleImage.getValueAt(1,-1,ColorChannel.RED);

    //coloumn index out of bounds:
    exampleImage.getValueAt(1,2,ColorChannel.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueAtWithNullColorChannel() {
    exampleImage.getValueAt(1,1,null);
  }

  @Test
  public void testGetValueAt() {
    // red value
    assertEquals(12, exampleImage.getValueAt(1,0,
        ColorChannel.RED));

    // green value
    assertEquals(12, exampleImage.getValueAt(1,0,
        ColorChannel.GREEN));

    // blue value
    assertEquals(12, exampleImage.getValueAt(1,0,
        ColorChannel.BLUE));

  }

  @Test
  public void testEqualsSameImage() {
    Image image = new Image24Bit(new Pixel[][] {
        {new Pixel(28, 199, 201), new Pixel(91, 49, 120)},
        {new Pixel(122, 255, 213), new Pixel(12, 1, 233)}});

    assertEquals(image, image);
  }

  @Test
  public void testEqualsEqualImages() {
    Image image1 = new Image24Bit(new Pixel[][] {
        {new Pixel(28, 199, 201), new Pixel(91, 49, 120)},
        {new Pixel(122, 255, 213), new Pixel(12, 1, 233)}});
    Image image2 = new Image24Bit(new Pixel[][] {
        {new Pixel(28, 199, 201), new Pixel(91, 49, 120)},
        {new Pixel(122, 255, 213), new Pixel(12, 1, 233)}});

    assertEquals(image1, image2);
    assertEquals(image2, image1);
  }

  @Test public void testEqualsDifferentImages() {
    Image image1 = new Image24Bit(new Pixel[][] {
        {new Pixel(28, 199, 201), new Pixel(91, 49, 120)},
        {new Pixel(122, 255, 0), new Pixel(12, 1, 233)}});
    Image image2 = new Image24Bit(new Pixel[][] {
        {new Pixel(28, 199, 201), new Pixel(91, 49, 120)},
        {new Pixel(122, 255, 213), new Pixel(12, 1, 233)}});

    assertNotEquals(image1, image2);
    assertNotEquals(image2, image1);
  }

  @Test public void testEqualsDifferentSizedImages() {
    Image image1 = new Image24Bit(new Pixel[][] {
        {new Pixel(28, 199, 201), new Pixel(91, 49, 120)},
        {new Pixel(122, 255, 0), new Pixel(12, 1, 233)}});
    Image image2 = new Image24Bit(new Pixel[][] {
        {new Pixel(28, 199, 201)},
        {new Pixel(122, 255, 213)}});

    assertNotEquals(image1, image2);
    assertNotEquals(image2, image1);
  }

  @Test
  public void testHashCode() {
    Pixel[][] pixels1 = {
        {new Pixel(28, 199, 201), new Pixel(91, 49, 120)},
        {new Pixel(122, 255, 0), new Pixel(12, 1, 233)}};
    Pixel[][] pixels2 = {
        {new Pixel(28, 199, 201)},
        {new Pixel(122, 255, 213)}};
    Image image1 = new Image24Bit(pixels1);
    Image image2 = new Image24Bit(pixels2);

    assertEquals(Arrays.deepHashCode(pixels1), image1.hashCode());
    assertEquals(Arrays.deepHashCode(pixels2), image2.hashCode());
  }
}


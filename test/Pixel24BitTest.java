import static org.junit.Assert.assertEquals;

import model.Pixel24Bit;
import org.junit.Test;

/**
 * Tests for the Pixel24Bit class.
 */
public class Pixel24BitTest {

  // Tests constructing a pixel with an invalid negative red channel value
  @Test(expected = IllegalArgumentException.class)
  public void constructorNegativeRed() {
    new Pixel24Bit(-1, 4, 5);
  }

  // Tests constructing a pixel with an invalid red channel value that is too large
  @Test(expected = IllegalArgumentException.class)
  public void constructorLargeRed() {
    new Pixel24Bit(256, 69, 125);
  }

  // Tests constructing a pixel with an invalid negative green channel value
  @Test(expected = IllegalArgumentException.class)
  public void constructorNegativeGreen() {
    new Pixel24Bit(12, -2, 234);
  }

  // Tests constructing a pixel with an invalid green channel value that is too large
  @Test(expected = IllegalArgumentException.class)
  public void constructorLargeGreen() {
    new Pixel24Bit(95, 260, 91);
  }

  // Tests constructing a pixel with an invalid negative blue channel value
  @Test(expected = IllegalArgumentException.class)
  public void constructorNegativeBlue() {
    new Pixel24Bit(175, 56, -1);
  }

  // Tests constructing a pixel with an invalid blue channel value that is too large
  @Test(expected = IllegalArgumentException.class)
  public void constructorLargeBlue() {
    new Pixel24Bit(19, 200, 270);
  }

  // Tests constructing a valid Pixel24Bit and test the getRed, getGreen, and getBlue methods
  @Test
  public void testValidPixel() {
    Pixel24Bit pixel = new Pixel24Bit(24, 204, 39);
    assertEquals(24, pixel.getRed());
    assertEquals(204, pixel.getGreen());
    assertEquals(39, pixel.getBlue());
  }

  // Tests constructing a valid Pixel24Bit with the maximum allowed color values
  @Test
  public void testValidPixelMax() {
    int max = Pixel24Bit.MAX_VALUE;
    Pixel24Bit pixel = new Pixel24Bit(max, max, max);
    assertEquals(max, pixel.getRed());
    assertEquals(max, pixel.getGreen());
    assertEquals(max, pixel.getBlue());
  }

  // Tests constructing a valid Pixel24Bit with the minimum allowed color values
  @Test
  public void testValidPixelMin() {
    int min = 0;
    Pixel24Bit pixel = new Pixel24Bit(min, min, min);
    assertEquals(min, pixel.getRed());
    assertEquals(min, pixel.getGreen());
    assertEquals(min, pixel.getBlue());
  }
}
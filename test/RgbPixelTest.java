import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Objects;
import model.Pixel;
import model.RgbPixel;
import org.junit.Test;

/**
 * Tests the Pixel class.
 */
public class RgbPixelTest {

  // Tests constructing new RgbPixels.
  @Test
  public void testConstructor() {
    //normally constructed Pixel
    Pixel magentaPixel = new RgbPixel(125, 0, 125);
    assertEquals(new RgbPixel(125, 0, 125), magentaPixel);
  }

  // Tests clamping values that are greater than the allowed maximum.
  @Test
  public void testClampMaxValues() {
    //red to be clamped:
    Pixel pinkRedPixel = new RgbPixel(256, 0, 125).clamp(0, 255);
    assertEquals(new RgbPixel(255, 0, 125), pinkRedPixel);

    //green to be clamped:
    Pixel brightGreenPixel = new RgbPixel(0, 256, 125).clamp(0, 255);
    assertEquals(new RgbPixel(0, 255, 125), brightGreenPixel);

    //blue to be clamped:
    Pixel lavenderPixel = new RgbPixel(125, 125, 256).clamp(0, 255);
    assertEquals(new RgbPixel(125, 125, 255), lavenderPixel);
  }

  // Tests clamping values that are less than the allowed minimum.
  @Test
  public void testClampMinValues() {
    //red to be clamped:
    Pixel brightGreenPixel = new RgbPixel(-1, 256, 125).clamp(0, 255);
    assertEquals(new RgbPixel(0, 255, 125), brightGreenPixel);

    //green to be clamped:
    Pixel pinkRedPixel = new RgbPixel(256, -1, 125).clamp(0, 255);
    assertEquals(new RgbPixel(255, 0, 125), pinkRedPixel);

    //blue to be clamped:
    Pixel grassGreenPixel = new RgbPixel(125, 125, -1).clamp(0, 255);
    assertEquals(new RgbPixel(125, 125, 0), grassGreenPixel);
  }

  // Tests the getRedValue method.
  @Test
  public void testGetRedValue() {
    Pixel greenPixel = new RgbPixel(0, 200, 23);
    Pixel yellowPixel = new RgbPixel(201, 199, 0);
    Pixel purplePixel = new RgbPixel(255, 0, 210);

    assertEquals(0, greenPixel.getRedValue());
    assertEquals(201, yellowPixel.getRedValue());
    assertEquals(255, purplePixel.getRedValue());
  }

  // Tests the getGreenValue method.
  @Test
  public void testGetGreenValue() {
    Pixel greenPixel = new RgbPixel(0, 200, 23);
    Pixel yellowPixel = new RgbPixel(201, 199, 0);
    Pixel purplePixel = new RgbPixel(255, 0, 210);

    assertEquals(200, greenPixel.getGreenValue());
    assertEquals(199, yellowPixel.getGreenValue());
    assertEquals(0, purplePixel.getGreenValue());
  }

  // Tests the getBlueValue method.
  @Test
  public void testGetBlueValue() {
    Pixel greenPixel = new RgbPixel(0, 200, 23);
    Pixel yellowPixel = new RgbPixel(201, 199, 0);
    Pixel purplePixel = new RgbPixel(255, 0, 210);

    assertEquals(23, greenPixel.getBlueValue());
    assertEquals(0, yellowPixel.getBlueValue());
    assertEquals(210, purplePixel.getBlueValue());
  }

  // Tests the getRgbVector method.
  @Test
  public void testGetRgbVector() {
    Pixel greenPixel = new RgbPixel(0, 200, 23);
    Pixel yellowPixel = new RgbPixel(201, 199, 0);
    Pixel purplePixel = new RgbPixel(255, 0, 210);

    assertTrue(Arrays.equals(new double[]{0, 200, 23}, greenPixel.getRgbVector()));
    assertTrue(Arrays.equals(new double[]{201, 199, 0}, yellowPixel.getRgbVector()));
    assertTrue(Arrays.equals(new double[]{255, 0, 210}, purplePixel.getRgbVector()));
  }


  // Test the toString method.
  @Test
  public void testToString() {
    Pixel greenPixel = new RgbPixel(10, 200, 23);
    Pixel yellowPixel = new RgbPixel(156, 199, 2);
    Pixel purplePixel = new RgbPixel(200, 15, 210);

    assertEquals("10 200 23", greenPixel.toString());
    assertEquals("156 199 2", yellowPixel.toString());
    assertEquals("200 15 210", purplePixel.toString());
  }

  // Tests the equals method.
  @Test
  public void testEquals() {
    Pixel pixel1 = new RgbPixel(10, 200, 23);
    Pixel pixel2 = new RgbPixel(10, 200, 23);
    Pixel pixelDiffRed = new RgbPixel(15, 200, 23);
    Pixel pixelDiffGreen = new RgbPixel(10, 201, 23);
    Pixel pixelDiffBlue = new RgbPixel(10, 200, 0);
    Pixel diffPixel = new RgbPixel(36, 204, 132);

    assertEquals(pixel1, pixel1);
    assertEquals(pixel1, pixel2);
    assertNotEquals(pixel1, pixelDiffRed);
    assertNotEquals(pixel1, pixelDiffGreen);
    assertNotEquals(pixel1, pixelDiffBlue);
    assertNotEquals(pixel1, diffPixel);
  }

  // Tests the hashCode method.
  @Test
  public void testHashCode() {
    Pixel greenPixel = new RgbPixel(10, 200, 23);
    Pixel yellowPixel = new RgbPixel(156, 199, 2);
    Pixel purplePixel = new RgbPixel(200, 15, 210);

    assertEquals(Objects.hash(10, 200, 23), greenPixel.hashCode());
    assertEquals(Objects.hash(156, 199, 2), yellowPixel.hashCode());
    assertEquals(Objects.hash(200, 15, 210), purplePixel.hashCode());
  }
}

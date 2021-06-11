import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Objects;
import model.Pixel;
import model.RgbPixel;
import org.junit.Test;

/**
 * Tests the Pixel class.
 */
public class RgbPixelTest {

  @Test
  public void testConstructor() {
    //normally constructed Pixel
    Pixel magentaPixel = new RgbPixel(125, 0, 125);
    assertEquals(new RgbPixel(125, 0, 125), magentaPixel);
  }

  @Test
  public void testClampingMaxValues() {
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

  @Test
  public void testClampingMinValues() {
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

  @Test
  public void testToString() {
    Pixel greenPixel = new RgbPixel(10, 200, 23);
    Pixel yellowPixel = new RgbPixel(156, 199, 2);
    Pixel purplePixel = new RgbPixel(200, 15 ,210);

    assertEquals("10 200 23", greenPixel.toString());
    assertEquals("156 199 2", yellowPixel.toString());
    assertEquals("200 15 210", purplePixel.toString());
  }

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

  @Test
  public void testHashCode() {
    Pixel greenPixel = new RgbPixel(10, 200, 23);
    Pixel yellowPixel = new RgbPixel(156, 199, 2);
    Pixel purplePixel = new RgbPixel(200, 15 ,210);

    assertEquals(Objects.hash(10, 200, 23), greenPixel.hashCode());
    assertEquals(Objects.hash(156, 199, 2), yellowPixel.hashCode());
    assertEquals(Objects.hash(200, 15, 210), purplePixel.hashCode());
  }
}

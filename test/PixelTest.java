import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Objects;
import model.Pixel;
import org.junit.Test;

/**
 * Tests the Pixel class.
 */
public class PixelTest {

  @Test
  public void testConstructor() {
    //normally constructed Pixel
    Pixel magentaPixel = new Pixel(125, 0, 125);
    assertEquals(new Pixel(125, 0, 125), magentaPixel);
  }

  @Test
  public void testConstructorClampingMaxValues() {
    //red to be clamped:
    Pixel pinkRedPixel = new Pixel(256, 0, 125);
    assertEquals(new Pixel(255, 0, 125), pinkRedPixel);

    //green to be clamped:
    Pixel brightGreenPixel = new Pixel(0, 256, 125);
    assertEquals(new Pixel(0, 255, 125), brightGreenPixel);

    //blue to be clamped:
    Pixel lavenderPixel = new Pixel(125, 125, 256);
    assertEquals(new Pixel(125, 125, 255), lavenderPixel);
  }

  @Test
  public void testConstructorClampingMinValues() {
    //red to be clamped:
    Pixel brightGreenPixel = new Pixel(-1, 256, 125);
    assertEquals(new Pixel(0, 255, 125), brightGreenPixel);

    //green to be clamped:
    Pixel pinkRedPixel = new Pixel(256, -1, 125);
    assertEquals(new Pixel(255, 0, 125), pinkRedPixel);

    //blue to be clamped:
    Pixel grassGreenPixel = new Pixel(125, 125, -1);
    assertEquals(new Pixel(125, 125, 0), grassGreenPixel);
  }

  @Test
  public void testToString() {
    Pixel greenPixel = new Pixel(10, 200, 23);
    Pixel yellowPixel = new Pixel(156, 199, 2);
    Pixel purplePixel = new Pixel(200, 15 ,210);

    assertEquals("10 200 23", greenPixel.toString());
    assertEquals("156 199 2", yellowPixel.toString());
    assertEquals("200 15 210", purplePixel.toString());
  }

  @Test
  public void testEquals() {
    Pixel pixel1 = new Pixel(10, 200, 23);
    Pixel pixel2 = new Pixel(10, 200, 23);
    Pixel pixelDiffRed = new Pixel(15, 200, 23);
    Pixel pixelDiffGreen = new Pixel(10, 201, 23);
    Pixel pixelDiffBlue = new Pixel(10, 200, 0);
    Pixel diffPixel = new Pixel(36, 204, 132);

    assertEquals(pixel1, pixel1);
    assertEquals(pixel1, pixel2);
    assertNotEquals(pixel1, pixelDiffRed);
    assertNotEquals(pixel1, pixelDiffGreen);
    assertNotEquals(pixel1, pixelDiffBlue);
    assertNotEquals(pixel1, diffPixel);
  }

  @Test
  public void testHashCode() {
    Pixel greenPixel = new Pixel(10, 200, 23);
    Pixel yellowPixel = new Pixel(156, 199, 2);
    Pixel purplePixel = new Pixel(200, 15 ,210);

    assertEquals(Objects.hash(10, 200, 23), greenPixel.hashCode());
    assertEquals(Objects.hash(156, 199, 2), yellowPixel.hashCode());
    assertEquals(Objects.hash(200, 15, 210), purplePixel.hashCode());
  }
}

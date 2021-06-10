package model;

import static org.junit.Assert.*;

import org.junit.Test;

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


}

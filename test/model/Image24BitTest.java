package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class Image24BitTest {

  Pixel[][] examplePixelMatrix = {{new Pixel(10,10,10)},
      {new Pixel(12,12,12)}, {new Pixel(13,12,13)}};
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
    Pixel[][] unequalSubMatrix = {{new Pixel(10,10,10)},
        {new Pixel(12,12,12), new Pixel(13,12,13)}, {}};
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
    exampleImage.getRedValueAt(-1,0);

    //row index out of bounds:
    exampleImage.getRedValueAt(3, 0);

    //negative column index:
    exampleImage.getRedValueAt(1,-1);

    //column index out of bounds:
    exampleImage.getRedValueAt(1,2);
  }

  @Test
  public void testGetRedValue() {
    assertEquals(12,
        exampleImage.getRedValueAt(1,0));
  }
}
package model;

import static org.junit.Assert.*;

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
    exampleImage.getValueAt(-1,0,ColorChannel.RED);

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

}


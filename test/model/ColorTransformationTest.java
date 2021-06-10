package model;

import static org.junit.Assert.*;

import model.ImageOperationCreator.IMGOperationType;
import org.junit.Test;

public class ColorTransformationTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithInvalidMatrix() {
    //null value for matrix
    new ColorTransformation(null);

    //non 3 by 3 matrix in size:
    new ColorTransformation(new double[3][1]);
  }

  @Test
  public void testApply() {
    Pixel[][] examplepixel = new Pixel[][]{
        {new Pixel(255, 0, 0),
            new Pixel(255, 100, 0),
            new Pixel(255, 255, 0)},
        {new Pixel(0, 255, 0),
            new Pixel(0, 0, 255),
            new Pixel(200, 0, 255)}};
    Image exampleimg = new Image24Bit(examplepixel);

    Image newexampleimg = new ImageOperationCreator().create(IMGOperationType.GREYSCALE)
        .apply(exampleimg);

    assertEquals(new Pixel(16, 32, 16), //<-- fix
        newexampleimg.getPixelAt(0,0));
  }

}
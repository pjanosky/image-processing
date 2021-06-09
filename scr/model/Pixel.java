package model;

import java.awt.Color;

public class Pixel {

  private int[][] rgbMatrix;

  public Pixel(int[][] rgbMatrix) throws IllegalArgumentException {
    if (rgbMatrix == null) {
      throw new IllegalArgumentException("The given matrix cannot be null");
    }
    if (rgbMatrix.length != 3) {
      throw new IllegalArgumentException("Invalid matrix!");
    }
    this.rgbMatrix = rgbMatrix;
  }

  public Pixel(Color colour) {
    int[][] temp = {{0}, {0}, {0}};
    temp[0][0] = colour.getRed();
    temp[1][0] = colour.getGreen();
    temp[2][0] = colour.getBlue();

    this.rgbMatrix = temp;
  }

  public int[][] getRgbMatrix() {
    return rgbMatrix;
  }

  public int getRedValue() {
    return rgbMatrix[0][0];
  }

  public int getGreenValue() {
    return rgbMatrix[1][0];
  }

  public int getBlueValue() {
    return rgbMatrix[2][0];
  }

}

package model;

public class ColorTransformation implements ImageOperation {

  private Double[][] matrix;

  public ColorTransformation(Double[][] matrix) throws IllegalArgumentException {
    if (matrix == null) {
      throw new IllegalArgumentException("The image cannot be processed with a null matrix!");
    }
    if (matrix.length != 3 || matrix[0].length != 1) {
      throw new IllegalArgumentException("Invalid matrix!");
    }
    this.matrix = matrix;
  }

  @Override
  public Image apply(Image image) {
    Image copyImage = new Image24Bit(image);
    for (int i = 0; i < copyImage.getHeight(); i++) {
      for (int j = 0; j < copyImage.getWidth(); j++) {
        this.transformColour(copyImage.getPixelAt(i, j));
      }
    }
    return copyImage;
  }

  private void transformColour(Pixel pixel) {
    for (int i = 0; i < 3; i++) {
      double temp = 0;
      for (int j = 0; j < 3; j++) {
        temp += matrix[i][j] * pixel.getRgbMatrix()[j][0];
      }
      pixel.getRgbMatrix()[i][0] = (int) temp;
    }
  }

}

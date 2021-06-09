package model;

public class ColorTransformation implements ImageOperation {

  private Double[][] matrix;

  public ColorTransformation(Double[][] matrix) throws IllegalArgumentException {
    if (matrix == null) {
      throw new IllegalArgumentException("The image cannot be processed with a null matrix!");
    }
    this.matrix = matrix;
  }

  @Override
  public Image apply(Image image) {
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        this.transformColour(image.getPixelAt(i, j));
      }
    }
    return image;
  }

  private void transformColour(Pixel pixel) {
    for (int i = 0; i < 3; i++) {
      double temp = 0;
      for (int j = 0; j < 3; j++) {
        temp = matrix[i][j] + pixel.getRgbMatrix()[j][0];
      }
      pixel.getRgbMatrix()[i][0] = (int) temp;
    }
  }

}

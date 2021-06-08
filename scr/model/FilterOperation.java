package model;

public class FilterOperation implements ImageOperation {

  private Integer[][] matrix;

  public FilterOperation(Integer[][] matrix) {
    this.matrix = matrix;
  }

  @Override
  public Image apply(Image image) {
    return null;
  }
}

// class ImageOperationCreator {...}
package model;

public interface ImageProcessingModel {

  void setCurrentImage(Image image);

  Image getCurrentImage();

  void applyOperation(ImageOperation operation);

  void exportCurrentImage(String filePath);

  void importImage(String filePath);
}

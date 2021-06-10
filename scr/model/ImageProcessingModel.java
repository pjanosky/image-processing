package model;

public interface ImageProcessingModel {

  void setCurrentImage(Image image);

  Image getCurrentImage();

  void applyOperation(ImageOperation operation);

  void exportCurrentImage(ImageImportExporter importExporter, String filePath);

  void importImage(ImageImportExporter importExporter, String filePath);
}

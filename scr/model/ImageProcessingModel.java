package model;

/**
 *
 */
public interface ImageProcessingModel extends ImageProcessingModelState {

  void setCurrentImage(Image image);

  void applyOperation(ImageOperation operation);

  void exportCurrentImage(ImageImportExporter importExporter, String filePath);

  void importImage(ImageImportExporter importExporter, String filePath);
}

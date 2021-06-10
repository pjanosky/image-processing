package model;

public interface ImageImportExporter {
  Image importImage(String filePath);

  void exportImage(String filePath, Image image);

}

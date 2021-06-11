package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageProcessingModelImpl implements ImageProcessingModel {

  private Image originalImage;
  private Image currentImage;

  public ImageProcessingModelImpl() {
    this.originalImage = null;
    this.currentImage = null;
  }

  @Override
  public void setImage(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("An image cannot be null!");
    }
    this.originalImage = image;
    this.currentImage = image;
  }

  @Override
  public void applyOperation(ImageOperation operation) {
    if (operation == null) {
      throw new IllegalArgumentException("An operation object cannot be null!");
    }
    currentImage = operation.apply(getCurrentImage());
  }

  @Override
  public void exportCurrentImage(ImageImportExporter importExporter, String filePath) {
    if (importExporter == null || filePath == null) {
      throw new IllegalArgumentException("The import-exporter or file path is null!");
    }
    File file = new File(filePath);
    try {
      if (file.exists()) {
        file.delete();
      }
      file.createNewFile();

      OutputStream output = new FileOutputStream(file);
      importExporter.saveImage(output, getCurrentImage());
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not create a new file.");
    }
  }

  @Override
  public void importImage(ImageImportExporter importExporter, String filePath) {
    if (importExporter == null || filePath == null) {
      throw new IllegalArgumentException("The import-exporter or file path is null!");
    }
    File file = new File(filePath);
    if (!file.exists()) {
      throw new IllegalArgumentException("File does not exist.");
    }
    try {
      InputStream input = new FileInputStream(file);
      setImage(importExporter.parseImage(input));
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to read file.");
    }
  }

  @Override
  public void revert() {
    currentImage = originalImage;
  }

  @Override
  public Image getCurrentImage() {
    return currentImage;
  }

  @Override
  public Image getOriginalImage() {
    return originalImage;
  }
}

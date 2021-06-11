package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A implementation of the image processing model interface. The model object stores an image as a
 * field for the current image. When first constructed, the {@code currentImage} field is initiated
 * as null until it is set to a legitimate image.
 */
public class ImageProcessingModelImpl implements ImageProcessingModel {

  private Image currentImage;

  /**
   * Constructs a {@code ImageProcessingModelImpl} object. The current image is initiated as a null
   * value.
   */
  public ImageProcessingModelImpl() {
    this.currentImage = null;
  }

  @Override
  public void setCurrentImage(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("An image cannot be null!");
    }
    this.currentImage = image;
  }

  @Override
  public void applyOperation(ImageOperation operation) {
    if (operation == null) {
      throw new IllegalArgumentException("An operation object cannot be null!");
    }
    setCurrentImage(operation.apply(getCurrentImage()));
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
      setCurrentImage(importExporter.parseImage(input));
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to read file.");
    }
  }

  @Override
  public Image getCurrentImage() {
    return currentImage;
  }
}

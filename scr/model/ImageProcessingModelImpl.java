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

  private Image originalImage;
  private Image currentImage;

  /**
   * Constructs a {@code ImageProcessingModelImpl} object. The current image is initiated as a null
   * value.
   */
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
  public void addLayer(String name) {

  }

  @Override
  public void setCurrentLayer(String name) {

  }

  @Override
  public void showLayer(String name, boolean isVisible) {

  }

  @Override
  public void applyOperation(String name, ImageOperation operation) {

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

//  @Override
//  public void revert() {
//    currentImage = originalImage;
//  }

  @Override
  public Image getCurrentImage() {
    // Images are immutable so it's ok to just return the field.
    return currentImage;
  }

  @Override
  public boolean isLayerVisible(String name) {
    return false;
  }

  @Override
  public Image getImage(String layerName) {
    return null;
  }

  @Override
  public String layerName(int index) {
    return null;
  }

  @Override
  public int getNumLayers() {
    return 0;
  }

  @Override
  public Image getOriginalImage() {
    // Images are immutable so it's ok to just return the field.
    return originalImage;
  }
}

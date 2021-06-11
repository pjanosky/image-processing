package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageProcessingModelImpl implements ImageProcessingModel {

  private Image currentImage;

  public ImageProcessingModelImpl() {
    this.currentImage = null;
  }

  @Override
  public void setCurrentImage(Image image) {
    this.currentImage = image;
  }

  @Override
  public void applyOperation(ImageOperation operation) {
    operation.apply(getCurrentImage());
  }

  @Override
  public void exportCurrentImage(ImageImportExporter importExporter, String filePath) {
    File file = new File(filePath);
    try {
      if (file.exists()) {
        file.delete();
      }
      file.createNewFile();

      OutputStream output = new FileOutputStream(file);
      importExporter.saveImage(output, getCurrentImage());
    } catch(IOException e) {
      throw new IllegalArgumentException("Could not create a new file.");
    }
  }

  @Override
  public void importImage(ImageImportExporter importExporter, String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
      throw new IllegalArgumentException("File does not exist.");
    }
    try {
      InputStream input = new FileInputStream(file);
      setCurrentImage(importExporter.parseImage(input));
    } catch(IOException e) {
      throw new IllegalArgumentException("Failed to read file.");
    }
  }

  @Override
  public Image getCurrentImage() {
    return currentImage;
  }
}

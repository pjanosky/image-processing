package controller.commands;

import controller.ControllerCommand;
import controller.ImageImportExporter;
import controller.ImportExporterCreator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import model.Image;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;

public class SaveLayersCommand implements ControllerCommand {

  private final String path;
  private final String name;
  private final String format;

  public SaveLayersCommand(String path, String name, String format) {
    if (path == null || name == null || format == null) {
      throw new IllegalArgumentException("Arguments must not be null");
    }

    File file = new File(path);
    if (path.isEmpty()) {
      this.path = name;
    } else {
      if (!file.isDirectory()) {
        throw new IllegalArgumentException("Saving all layers requires a path to a directory");
      }
      this.path = path + "/" + name;
    }
    this.name = name;
    this.format = format;
  }

  @Override
  public void go(ImageProcessingModel model) throws IllegalArgumentException {
    createDirectory();
    ImageImportExporter ie = ImportExporterCreator.create(format);
    StringBuilder text = new StringBuilder();

    // Save images
    for (int index = 0; index < model.numLayers(); index += 1) {
      String layerName = model.getLayerNameAt(index);
      String filePath = path + "/" + layerName + "." + format;
      Image image = model.getImageIn(layerName);
      if (image != null) {
        try {
          OutputStream output = new FileOutputStream(filePath);
          ie.saveImage(output, image);
        } catch (IOException e) {
          throw new IllegalArgumentException("Failed to save images. " + e.getMessage());
        }
      }

      text.append(layerName).append(' ');
      text.append(model.isVisible(layerName)).append(' ');
      if (image != null) {
        text.append(filePath);
      }
      text.append('\n');
    }

    // Save text file
    try {
      String filePath = path + "/" + name + ".txt";
      OutputStream output = new FileOutputStream(filePath);
      output.write(text.toString().getBytes());
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to save text info file.");
    }
  }

  /**
   * Creates a directory to house the image layers, deleting any files
   * in the directory.
   */
  private void createDirectory() {
    File directory = new File(path);
    if (directory.exists()) {
      for (File file : directory.listFiles()) {
        file.delete();
      }
    } else if (!directory.mkdir()) {
      throw new IllegalArgumentException("Could not create directory at " + path + ".");
    }
  }
}
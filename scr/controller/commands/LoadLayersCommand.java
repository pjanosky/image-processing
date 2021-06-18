package controller.commands;

import controller.ControllerCommand;
import controller.ImageImportExporter;
import controller.ImportExporterCreator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import model.Image;
import model.ImageProcessingModel;

public class LoadLayersCommand implements ControllerCommand {

  private final String path;
  private final String name;
  private final Scanner scan;


  public LoadLayersCommand(String path) {
    if (path == null) {
      throw new IllegalArgumentException("Arguments must not be null.");
    }

    File directory = new File(path);
    if (directory.isDirectory()) {
      this.path = path;
      this.name = directory.getName();
    } else {
      throw new IllegalArgumentException("Path must be a path to a directory.");
    }

    try {
      InputStream input = new FileInputStream(path + "/" + name + ".txt");
      this.scan = new Scanner(input);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to read text info file.");
    }
  }

  @Override
  public void go(ImageProcessingModel model) throws IllegalArgumentException {
    resetModel(model);

    while (scan.hasNextLine()) {
      String line = scan.nextLine();
      String[] args = line.split(" ");
      if (args.length != 2 && args.length != 3) {
        throw new IllegalArgumentException("Failed to parse text file.");
      }
      String layerName = args[0];
      boolean isVisible = Boolean.parseBoolean(args[1]);
      model.addLayer(layerName);
      model.show(layerName, isVisible);
      if (args.length == 3) {
        model.setLayerImage(layerName, loadImage(args[2]));
      }
    }
  }

  /**
   * Removes a layers from a model.
   *
   * @param model the model to remove layers from.
   */
  private void resetModel(ImageProcessingModel model) {
    for (int index = 0; index < model.numLayers(); index += 1) {
      String layerName = model.getLayerNameAt(index);
      model.removeLayer(layerName);
    }
  }

  /**
   * Loads an image from disk in the format specified by the file extension.
   *
   * @param filePath the path to the file on disk
   * @return the loaded image.
   * @throws IllegalArgumentException if the file path is invalid or if the image format isn't
   *                                  supported.
   */
  private Image loadImage(String filePath) throws IllegalArgumentException {
    int dotIndex = filePath.lastIndexOf('.');
    if (dotIndex >= filePath.length() + 1) {
      throw new IllegalArgumentException("Failed to load image " + filePath + ".");
    }
    String format = filePath.substring(dotIndex + 1);
    ImageImportExporter ie = ImportExporterCreator.create(format);
    try {
      return ie.parseImage(new FileInputStream(filePath));
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to load image " + filePath + ".");
    }
  }
}
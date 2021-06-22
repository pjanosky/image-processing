package controller.commands;

import controller.ImageImportExporter;
import controller.PngImportExporter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import model.Image;
import model.ImageProcessingModel;

/**
 * When the user calls the command "loadall", the controller loads (i.e., imports) all the layers
 * saved in the directory directed by the given path.
 */
public class LoadLayersCommand implements ControllerCommand {

  private final Scanner scan;
  private final ImageImportExporter ie;

  /**
   * Constructs a {@code LoadLayersCommand} object. A directory of layer files is retrieved via the
   * path from the user. Because layers are .png files, the controller will use the import-exporter
   * specifically made for .png files. The information of the retrieved directory is converted into
   * a text file.
   *
   * @param path the path to the directory with all layer files the user wants to load to the model
   * @throws IllegalArgumentException if the given path string is null, if the directed file is not
   *                                  a directory, or if the controller fails to read text
   *                                  information file.
   */
  public LoadLayersCommand(String path) throws IllegalArgumentException {
    if (path == null) {
      throw new IllegalArgumentException("Arguments must not be null.");
    }

    File directory = new File(path);
    if (!directory.isDirectory()) {
      throw new IllegalArgumentException("Path must be a path to a directory.");
    }

    try {
      InputStream input = new FileInputStream(path + "/info.txt");
      this.scan = new Scanner(input);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to read text info file. " + e.getMessage());
    }

    ie = new PngImportExporter();
  }

  @Override
  public void runCommand(ImageProcessingModel model)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    resetModel(model);

    while (scan.hasNextLine()) {
      String line = scan.nextLine();
      String[] args = line.split(" ");
      if (args.length != 2 && args.length != 3) {
        throw new IllegalStateException("Failed to parse text file.");
      }
      String layerName = args[0];
      boolean isVisible = Boolean.parseBoolean(args[1]);
      model.addLayer(layerName);
      model.showLayer(layerName, isVisible);
      if (args.length == 3) {
        model.setLayerImage(layerName, loadImage(args[2]));
      }
    }
  }

  /**
   * Removes layers from a model.
   *
   * @param model the model to remove layers from.
   */
  private void resetModel(ImageProcessingModel model) {
    for (int index = model.numLayers() - 1; index >= 0; index -= 1) {
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
    try {
      return ie.parseImage(new FileInputStream(filePath));
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to load image. " + e.getMessage());
    }
  }
}

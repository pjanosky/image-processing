package controller.commands;

import controller.ImageImportExporter;
import controller.PngImportExporter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import model.Image;
import model.ImageProcessingModel;
import view.ImageProcessingView;

/**
 * When the user calls the command "saveall", the controller saves (i.e., exports) all the layers as
 * .png files in a directory.
 */
public class SaveLayersCommand implements ControllerCommand {

  private final String path;
  private final ImageImportExporter ie;

  /**
   * Constructs a {@code SaveLayersCommand} object. The controller creates a directory at the given
   * path and renames the path to <i>path/name</i> to signify that the directory created has the
   * given name.
   *
   * @param path the file path to the directory where the layers will be saved.
   * @param name the name of the subdirectory to save the image and text file describing the
   *             layers.
   */
  public SaveLayersCommand(String path, String name) {
    if (path == null || name == null) {
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
    this.ie = new PngImportExporter();
  }

  @Override
  public void runCommand(ImageProcessingModel model, ImageProcessingView view)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    createDirectory();
    StringBuilder text = new StringBuilder();

    // Save images
    for (int index = 0; index < model.numLayers(); index += 1) {
      String layerName = model.getLayerNameAt(index);
      String filePath = path + "/" + layerName + ".png";
      Image image = model.getImageIn(layerName);
      if (image != null) {
        try {
          OutputStream output = new FileOutputStream(filePath);
          ie.saveImage(output, image);
        } catch (IOException e) {
          throw new IllegalStateException("Failed to save images. " + e.getMessage());
        }
      }

      text.append(layerName).append(' ');
      text.append(model.isVisible(layerName));
      if (image != null) {
        text.append(' ').append(filePath);
      }
      text.append('\n');
    }

    // Save text file
    try {
      String filePath = path + "/info.txt";
      OutputStream output = new FileOutputStream(filePath);
      output.write(text.toString().getBytes());
    } catch (IOException e) {
      throw new IllegalStateException("Failed to save text info file.");
    }
    view.renderMessage("Saved all layers to " + path + ".");
  }

  /**
   * Creates a directory to house the image layers, deleting any files in the directory.
   */
  private void createDirectory() {
    File directory = new File(path);
    if ((!directory.exists() || !directory.isDirectory()) && !directory.mkdir()) {
      throw new IllegalStateException("Could not create directory at " + path + ".");
    }
  }
}
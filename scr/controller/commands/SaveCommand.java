package controller.commands;

import controller.ImageImportExporter;
import controller.ImportExporterCreator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import model.Image;
import model.ImageProcessingModel;

/**
 * When the users class "save <i>filePath</i> <i>format</i>", the top-most visible layer is saved to
 * disk at file path in the specified image file format.
 */
public class SaveCommand implements ControllerCommand {

  private final ImageImportExporter ie;
  private final OutputStream output;

  /**
   * Constructs a new SaveCommand setup to save in a specific format an location on disk.
   *
   * @param filePath the path to the save destination of the file including the file name and
   *                 extension.
   * @param format   a String representing the informal name of a file format.
   * @throws IllegalArgumentException if either of the arguments are null, if the file format isn't
   *                                  supported, or if the image cannot be saved to the given file
   *                                  path.
   */
  public SaveCommand(String filePath, String format) throws IllegalArgumentException {
    if (filePath == null || format == null) {
      throw new IllegalArgumentException("Arguments must not be null");
    }

    ie = ImportExporterCreator.create(format);

    try {
      output = new FileOutputStream(filePath);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to save file. " + e.getMessage());
    }
  }

  @Override
  public void go(ImageProcessingModel model)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }

    Image image = null;
    for (int index = 0; index < model.numLayers(); index += 1) {
      String name = model.getLayerNameAt(index);
      if (model.isVisible(name) & model.getImageIn(name) != null) {
        image = model.getImageIn(name);
        break;
      }
    }

    if (image == null) {
      throw new IllegalStateException("No visible layers to export");
    }

    try {
      ie.saveImage(output, image);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to save image. " + e.getMessage());
    }
  }
}

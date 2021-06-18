package controller.commands;

import controller.ControllerCommand;
import controller.ImageImportExporter;
import controller.ImportExporterCreator;
import controller.JpegImportExporter;
import controller.PngImportExporter;
import controller.PpmImportExporter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import model.Image;
import model.ImageProcessingModel;

/**
 * A command that saves the image associated with the current layer to disk in a specified format
 * and location. The syntax of the command is: save *format* *file path*
 */
public class SaveCommand implements ControllerCommand {

  ImageImportExporter ie;
  OutputStream output;

  /**
   * Constructs a new SaveCommand setup to save in a specific format an location on disk.
   *
   * @param format   a String representing the informal name of a file format.
   * @param filePath the path to the save destination of the file including the file name and
   *                 extension.
   * @throws IllegalArgumentException if either of the arguments are null, if the file format isn't
   *                                  supported, or if the image cannot be saved to the given file
   *                                  path.
   */
  public SaveCommand(String format, String filePath) throws IllegalArgumentException {
    if (format == null || filePath == null) {
      throw new IllegalArgumentException("Arguments must not be null");
    }

    ie = ImportExporterCreator.create(format);

    try {
      output = new FileOutputStream(filePath);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to save file to " + filePath);
    }
  }

  @Override
  public void go(ImageProcessingModel model) throws IllegalArgumentException {
    Image image = model.getImageIn(model.getCurrentName());
    try {
      ie.saveImage(output, image);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to save the image.");
    }
  }
}

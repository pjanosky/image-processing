package controller.commands;

import controller.ImageImportExporter;
import controller.ImportExporterCreator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import model.Image;
import model.ImageProcessingModel;

public class LoadCommand implements ControllerCommand {

  private final ImageImportExporter ie;
  private final InputStream input;

  public LoadCommand(String filepath, String format) {
    if (filepath == null || format == null) {
      throw new IllegalArgumentException("The parameters cannot be null!");
    }

    ie = ImportExporterCreator.create(format);

    try {
      input = new FileInputStream(filepath);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to load file from " + filepath);
    }
  }

  @Override
  public void go(ImageProcessingModel model)
      throws IllegalStateException, IllegalArgumentException {
    Image parsedImage;
    try {
      parsedImage = ie.parseImage(input);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to save the image.");
    }

    model.setCurrentLayerImage(parsedImage);
  }
}

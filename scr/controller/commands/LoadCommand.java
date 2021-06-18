package controller.commands;

import controller.ControllerCommand;
import controller.ImageImportExporter;
import controller.ImportExporterCreator;
import controller.JpegImportExporter;
import controller.PngImportExporter;
import controller.PpmImportExporter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import model.Image;
import model.ImageProcessingModel;

public class LoadCommand implements ControllerCommand {

  String layerName;
  ImageImportExporter ie;
  InputStream input;

  public LoadCommand(String layerName, String format, String filepath) {
    if (layerName == null || format == null || filepath == null) {
      throw new IllegalArgumentException("The parameters cannot be null!");
    }

    this.layerName = layerName;

    ie = ImportExporterCreator.create(format);

    try {
      input = new FileInputStream(filepath);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to load file from " + filepath);
    }
  }

  @Override
  public void go(ImageProcessingModel model) throws IllegalArgumentException {
    Image parsedImage;
    try {
      parsedImage = ie.parseImage(input);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to save the image.");
    }

    if (layerName == "current") {
      model.setCurrentLayerImage(parsedImage);
    } else {
      model.setLayerImage(layerName, parsedImage);
    }
  }
}

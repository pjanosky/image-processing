package controller.commands;

import controller.ImageImportExporter;
import controller.ImportExporterCreator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import model.Image;
import model.ImageProcessingModel;
import view.ImageProcessingView;

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

    for (int index = 0; index < model.numLayers(); index += 1) {
      Image image = model.getImageIn(model.getLayerNameAt(index));
      if (image != null && (image.getWidth() != parsedImage.getWidth()
          || image.getHeight() != parsedImage.getHeight())) {
        throw new IllegalArgumentException("Images in all layers must have the same dimensions of "
            + image.getWidth() + "x" + image.getHeight() + ".");
      }
    }



    String current = model.getCurrentName();
    if (current != null) {
      model.setLayerImage(current, parsedImage);
    } else {
      throw new IllegalArgumentException("No current layer set");
    }
  }
}

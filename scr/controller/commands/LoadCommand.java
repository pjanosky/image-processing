package controller.commands;

import controller.ImageImportExporter;
import controller.ImportExporterCreator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import model.Image;
import model.ImageProcessingModel;
import view.ImageProcessingView;

/**
 * When the user calls the command "load <i>file-path</i> <i>format-of-file</i>", it loads (i.e.,
 * imports) the file via the given filepath. The format of the file can be found at the end of the
 * file name as a suffix.
 */
public class LoadCommand implements ControllerCommand {

  private final ImageImportExporter ie;
  private final InputStream input;

  /**
   * Constructs a {@code LoadCommand} object. The appropriate image import-exporter will be created
   * and initiated to the field from the file format that the user has typed in.
   *
   * @param filepath the filepath to the image file the user wants to load (import) scanned as a
   *                 string
   * @param format   the format of the image file the user wants to import scanned as a string
   * @throws IllegalArgumentException if either parameter is null or the loading of the file has
   *                                  failed
   */
  public LoadCommand(String filepath, String format) throws IllegalArgumentException {
    if (filepath == null || format == null) {
      throw new IllegalArgumentException("The parameters cannot be null!");
    }

    ie = ImportExporterCreator.create(format);

    try {
      input = new FileInputStream(filepath);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to load file. " + e.getMessage());
    }
  }

  @Override
  public void runCommand(ImageProcessingModel model, ImageProcessingView view)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    Image parsedImage;
    try {
      parsedImage = ie.parseImage(input);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to save the image. " + e.getMessage());
    }

    String current = model.getCurrentName();
    if (current != null) {
      model.setLayerImage(current, parsedImage);
    } else {
      throw new IllegalStateException("No current layer set");
    }

  }
}

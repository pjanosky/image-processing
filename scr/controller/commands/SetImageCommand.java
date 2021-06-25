package controller.commands;

import model.Image;
import model.ImageExamples;
import model.ImageProcessingModel;
import model.RgbPixel;
import view.ImageProcessingView;

/**
 * When the user calls the command "set" followed by a series of arguments. A new image (created
 * programmatically) specified by the arguments is set to the current layers. Currently supports
 * rainbows and checkerboards.
 */
public class SetImageCommand implements ControllerCommand {

  private final String type;
  private final String[] args;

  /**
   * Constructs a new SetImageCommand.
   *
   * @param type the type of programmatically created image to set.
   * @param args the parameters for creating the image programmatically.
   * @throws IllegalArgumentException if any of the arguments are null or type of image isn't
   *                                  supported.
   */
  public SetImageCommand(String type, String[] args) throws IllegalArgumentException {
    if (type == null || args == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    for (String arg : args) {
      if (arg == null) {
        throw new IllegalArgumentException("Arguments cannot be null.");
      }
    }

    this.type = type;
    this.args = args;
  }


  @Override
  public void runCommand(ImageProcessingModel model, ImageProcessingView view)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }

    switch (type) {
      case "rainbow":
        rainbow(model);
        break;
      case "checkerboard":
        checkerBoard(model);
        break;
      default:
        throw new IllegalArgumentException("Unknown image type.");
    }
  }


  /**
   * Sets the image of the current layers to a rainbow image, parsing the parameters from the user
   * input. Rainbow image commands take the form "rainbow width stripHeight".
   *
   * @param model the model to set the current layers of.
   * @throws IllegalArgumentException if the parameters for the image are wrong.
   * @throws IllegalStateException    if there is no current layers set in the model.
   */
  void rainbow(ImageProcessingModel model) {
    if (args.length != 2) {
      throw new IllegalArgumentException(
          "Rainbow images need 2 parameters: width and stripe height.");
    }

    int width;
    int stripeHeight;
    try {
      width = Integer.parseInt(args[0]);
      stripeHeight = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Could not parse arguments for rainbow.");
    }

    Image image = ImageExamples.rainbow(width, stripeHeight);
    setImage(image, model);
  }

  /**
   * Sets the image of the current layers to a checkerboard image, parsing the parameters from the
   * user input. Checkerboard images have the form "checkerboard numRows numCols squareSize".
   *
   * @param model the model to set the current layers of.
   * @throws IllegalArgumentException if the parameters for the image are wrong.
   * @throws IllegalStateException    if there is no current layers set in the model.
   */
  void checkerBoard(ImageProcessingModel model)
      throws IllegalArgumentException, IllegalStateException {
    if (args.length != 3) {
      throw new IllegalArgumentException(
          "Checkerboard images need 2 parameters: number of horizontal squares, "
              + "number of vertical squares, and the size of each square in pixels.");
    }

    int cols;
    int rows;
    int size;
    try {
      cols = Integer.parseInt(args[0]);
      rows = Integer.parseInt(args[1]);
      size = Integer.parseInt(args[2]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Could not parse arguments for checkerboard.");
    }

    Image image = ImageExamples.checkerboard(rows, cols, size, size,
        new RgbPixel(0, 0, 0),
        new RgbPixel(255, 255, 255));
    setImage(image, model);
  }

  /**
   * Sets an image to the current layer in the model.
   *
   * @param image the image to set to the layers.
   * @param model the model to set the current layer of.
   * @throws IllegalStateException if there is no current layer set.
   */
  private void setImage(Image image, ImageProcessingModel model) throws IllegalStateException {
    String current = model.getCurrentName();
    if (current != null) {
      model.setLayerImage(current, image);
    } else {
      throw new IllegalStateException("No current layer set");
    }
  }
}

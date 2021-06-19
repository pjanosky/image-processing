package controller.commands;

import model.ImageOperation;
import model.ImageProcessingModel;

/**
 * When the user calls one of these commands—"blur", "sharpen", "sepia", "greyscale"—the controller
 * applies the appropriate image operation on the current layer.
 */
public class ImageProcessCommand implements ControllerCommand {

  private final ImageOperation operation;

  /**
   * Constructs a {@code ImageProcessCommand} object.
   *
   * @param operation the image operation that is called and is available in the model
   * @throws IllegalArgumentException if the given operation object is null
   */
  public ImageProcessCommand(ImageOperation operation) throws IllegalArgumentException {
    if (operation == null) {
      throw new IllegalArgumentException("The parameters cannot be null!");
    }
    this.operation = operation;
  }

  @Override
  public void go(ImageProcessingModel model)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null!");
    }

    String current = model.getCurrentName();
    if (current != null) {
      model.applyOperation(current, operation);
    } else {
      throw new IllegalArgumentException("No current layer set");
    }
  }
}

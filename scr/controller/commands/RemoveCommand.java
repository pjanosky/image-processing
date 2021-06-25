package controller.commands;

import model.ImageProcessingModel;
import view.ImageProcessingView;

/**
 * When the user calls the command "remove", the controller removes the current layer.
 */
public class RemoveCommand implements ControllerCommand {

  @Override
  public void runCommand(ImageProcessingModel model, ImageProcessingView view)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    String current = model.getCurrentName();
    if (current != null) {
      model.removeLayer(current);
    } else {
      throw new IllegalStateException("No current layer set");
    }

  }
}

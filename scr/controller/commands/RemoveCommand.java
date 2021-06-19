package controller.commands;

import model.ImageProcessingModel;
import view.ImageProcessingView;

public class RemoveCommand implements ControllerCommand {

  @Override
  public void go(ImageProcessingModel model)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    String current = model.getCurrentName();
    if (current != null) {
      model.removeLayer(current);
    } else {
      throw new IllegalArgumentException("No current layer set");
    }

  }
}

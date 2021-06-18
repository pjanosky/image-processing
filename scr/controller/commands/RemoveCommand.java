package controller.commands;

import model.ImageProcessingModel;

public class RemoveCommand implements ControllerCommand {

  @Override
  public void go(ImageProcessingModel model)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    model.removeLayer(model.getCurrentName());
  }
}

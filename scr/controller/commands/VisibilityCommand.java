package controller.commands;

import model.ImageProcessingModel;
import view.ImageProcessingView;

public class VisibilityCommand implements ControllerCommand {

  boolean isVisible;

  public VisibilityCommand(boolean isVisible) {
    this.isVisible = isVisible;
  }

  @Override
  public void go(ImageProcessingModel model)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null!");
    }
    String current = model.getCurrentName();
    if (current != null) {
      model.showLayer(current, isVisible);
    } else {
      throw new IllegalArgumentException("No current layer set");
    }

  }
}

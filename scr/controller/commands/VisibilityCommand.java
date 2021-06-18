package controller.commands;

import model.ImageProcessingModel;

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

    model.showCurrent(isVisible);
  }
}

package controller.commands;

import controller.ControllerCommand;
import model.ImageProcessingModel;

public class VisibilityCommand implements ControllerCommand {

  String layerName;
  boolean isVisible;

  public VisibilityCommand(String layerName, boolean isVisible) {
    if (layerName == null) {
      throw new IllegalArgumentException("The layer name cannot be null!");
    }
    this.layerName = layerName;
    this.isVisible = isVisible;
  }

  @Override
  public void go(ImageProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null!");
    }

    if (layerName == "current") {
      model.showCurrent(isVisible);
    } else {
      model.show(layerName, isVisible);
    }
  }
}

package controller.commands;

import controller.ControllerCommand;
import model.ImageProcessingModel;

public class HideCommand implements ControllerCommand {

  String layerName;

  public HideCommand(String layerName) {
    if (layerName == null) {
      throw new IllegalArgumentException("The layer name cannot be null!");
    }
    this.layerName = layerName;
  }

  @Override
  public void go(ImageProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null!");
    }

    if (layerName == "current") {
      model.showCurrent(false);
    } else {
      model.show(layerName, false);
    }
  }
}

package controller.commands;

import controller.ControllerCommand;
import model.ImageProcessingModel;

public class ShowCommand implements ControllerCommand {

  String layerName;
  public ShowCommand(String layerName) {
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
      model.showCurrent(true);
    } else {
      model.show(layerName, true);
    }
  }

}

package controller.commands;

import controller.ControllerCommand;
import model.ImageOperation;
import model.ImageProcessingModel;

public class ImageProcessCommand implements ControllerCommand {

  String layerName;
  ImageOperation operation;
  public ImageProcessCommand(String layerName, ImageOperation operation) {
    if (layerName == null || operation == null) {
      throw new IllegalArgumentException("The parameters cannot be null!");
    }
    this.layerName = layerName;
    this.operation = operation;
  }

  @Override
  public void go(ImageProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null!");
    }

    if (layerName == "current") {
      model.applyOperationCurrent(operation);
    } else {
      model.applyOperation(layerName, operation);
    }

  }
}

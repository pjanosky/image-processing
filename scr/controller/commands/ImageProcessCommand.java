package controller.commands;

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
  public void go(ImageProcessingModel model)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null!");
    }

    model.applyOperationCurrent(operation);

  }
}

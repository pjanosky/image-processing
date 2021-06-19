package controller.commands;

import java.io.IOException;
import model.ImageOperation;
import model.ImageProcessingModel;
import view.ImageProcessingView;

public class ImageProcessCommand implements ControllerCommand {

  ImageOperation operation;
  public ImageProcessCommand(ImageOperation operation) {
    if (operation == null) {
      throw new IllegalArgumentException("The parameters cannot be null!");
    }
    this.operation = operation;
  }

  @Override
  public void go(ImageProcessingModel model)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null!");
    }

    String current = model.getCurrentName();
    if (current != null) {
      model.applyOperation(current, operation);
    } else {
      throw new IllegalArgumentException("No current layer set");
    }
  }
}

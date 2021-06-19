package controller.commands;

import controller.commands.ControllerCommand;
import model.ImageProcessingModel;
import view.ImageProcessingView;

public class MoveCommand implements ControllerCommand {

  private final int index;

  public MoveCommand(int index) {
    this.index = index;
  }

  @Override
  public void go(ImageProcessingModel model)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    String current = model.getCurrentName();
    if (current != null) {
      model.reorderLayer(current, index - 1);
    } else {
      throw new IllegalArgumentException("No current layer set");
    }

  }
}

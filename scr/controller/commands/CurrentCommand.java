package controller.commands;

import controller.commands.ControllerCommand;
import model.ImageProcessingModel;
import view.ImageProcessingView;

public class CurrentCommand implements ControllerCommand {

  private final String name;

  public CurrentCommand(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    this.name = name;
  }

  @Override
  public void go(ImageProcessingModel model)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    model.setCurrentLayer(name);
  }
}

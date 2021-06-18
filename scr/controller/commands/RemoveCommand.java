package controller.commands;

import controller.ControllerCommand;
import model.ImageProcessingModel;

public class RemoveCommand implements ControllerCommand {

  private final String name;

  public RemoveCommand(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Arguments must not be null.");
    }
    this.name = name;
  }

  @Override
  public void go(ImageProcessingModel model) throws IllegalArgumentException {
    model.removeLayer(name);
  }
}

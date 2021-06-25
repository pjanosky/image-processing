package controller.commands;

import model.ImageProcessingModel;
import model.ImageProcessingViewModel;
import view.ImageProcessingView;

/**
 * When the user calls the command "current <i>layer-name</i>", the controller sets the layer with
 * the given name as the current layer.
 */
public class CurrentCommand implements ControllerCommand {

  private final String name;

  /**
   * Constructs a {@code CurrentCommand} object.
   *
   * @param name the name of the layer the user wants to set as the current
   * @throws IllegalArgumentException if the given name is null
   */
  public CurrentCommand(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    this.name = name;
  }

  @Override
  public void runCommand(ImageProcessingModel model, ImageProcessingView view)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    model.setCurrentLayer(name);
  }
}

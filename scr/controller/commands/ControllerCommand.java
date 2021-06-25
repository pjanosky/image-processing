package controller.commands;

import model.ImageProcessingModel;
import view.ImageProcessingView;

/**
 * Represents a command that can be executed in response to user input in the controller.
 */
public interface ControllerCommand {

  /**
   * Executes the command.
   *
   * @param model the model representing the state of the image processing program. The command may
   *              query the state of the model or mutate it.
   * @param view
   * @throws IllegalArgumentException if illegal arguments are passed to the command.
   * @throws IllegalStateException    if the command fails for any other reason
   */
  void runCommand(ImageProcessingModel model, ImageProcessingView view)
      throws IllegalStateException, IllegalArgumentException;
}

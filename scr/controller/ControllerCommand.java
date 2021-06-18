package controller;

import model.ImageProcessingModel;

/**
 * Represents a command that can be executed in response to user input in the controller.
 */
public interface ControllerCommand {

  /**
   * Executes the command.
   *
   * @param model the model representing the state of the image processing program.
   *              The command may query the state of the model or mutate it.
   * @throws IllegalArgumentException if the command fails for any reason.
   */
  void go(ImageProcessingModel model) throws IllegalArgumentException;
}

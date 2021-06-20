package controller.commands;

import model.ImageProcessingModel;

/**
 * When the user calls the command "add <i>layer-name</i>", the controller adds a layer to be stored
 * in the model.
 */
public class AddCommand implements ControllerCommand {

  private final String name;

  /**
   * Constructs a {@code AddCommand} object.
   *
   * @param name the name the user wants to give to the to-be-added layer
   * @throws IllegalArgumentException if the given name is invalid. That is if the name contains
   *                                  symbols or whitespace.
   */
  public AddCommand(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Arguments must not be null");
    }
    if (!validName(name)) {
      throw new IllegalArgumentException("Invalid layer name. "
          + "Valid names are are non-empty alpha numeric strings without whitespace");
    }
    this.name = name;
  }

  @Override
  public void go(ImageProcessingModel model)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    model.addLayer(name);
  }

  /**
   * Determines whether a given string is a valid name for a layer. Valid layer names have not any
   * whitespace or special characters.
   *
   * @param name the file name to check
   * @return whether the name is valid
   */
  static boolean validName(String name) {
    return name.matches("^(\\w)+$");
  }
}

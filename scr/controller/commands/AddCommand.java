package controller.commands;

import controller.ControllerCommand;
import model.ImageProcessingModel;

public class AddCommand implements ControllerCommand {

  private final String name;

  public AddCommand(String name) {
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
  public void go(ImageProcessingModel model) throws IllegalArgumentException {
    model.addLayer(name);
  }

  /**
   * Determines whether a given string is a valid name for a layer. Valid layer names have not
   * whitespace or special characters
   *
   * @param name the file name to check
   * @return whether the name is valid
   */
  static boolean validName(String name) {
    return name.matches("^(\\w)+$");
  }
}

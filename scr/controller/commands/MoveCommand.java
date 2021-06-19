package controller.commands;

import model.ImageProcessingModel;

/**
 * When the user calls the command "move <i>number-to-move-the-current-layer-to</i>", the controller
 * moves the current layer to the designated index number.
 */
public class MoveCommand implements ControllerCommand {

  private final int index;

  /**
   * Constructs a {@code MoveCommand} object.
   *
   * @param index the index where the user wants the current layer to be moved
   */
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

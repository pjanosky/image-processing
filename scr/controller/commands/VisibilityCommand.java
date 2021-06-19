package controller.commands;

import model.ImageProcessingModel;

/**
 * When the user calls either of these commands—"show" or "hide"—the controller shows or hides the
 * current layer.
 */
public class VisibilityCommand implements ControllerCommand {

  private boolean isVisible;

  /**
   * Constructs a {@code VisibilityCommand} object. For "show" command, {@code isVisible} is true.
   * For "hide" command, {@code isVisible} is false.
   *
   * @param isVisible whether or not the user wants the current layer to be visible or not.
   */
  public VisibilityCommand(boolean isVisible) {
    this.isVisible = isVisible;
  }

  @Override
  public void go(ImageProcessingModel model)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null!");
    }
    String current = model.getCurrentName();
    if (current != null) {
      model.showLayer(current, isVisible);
    } else {
      throw new IllegalArgumentException("No current layer set");
    }

  }
}

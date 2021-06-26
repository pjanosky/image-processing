package view;

import controller.CommandListener;

/**
 * Represents a interactive graphical view to display program data.
 */
public interface GuiImageProcessingView extends ImageProcessingView {

  /**
   * Adds a listener to respond to user input events in the view. When the user triggers and action,
   * the appropriate method in the listener is called with the data the user has inputted.
   *
   * @param listener the listener to set that will respond to user input.
   */
  void setCommandListener(CommandListener listener);

  /**
   * Sets the visibility status of the view. Specifically, hides and shows the view.
   *
   * @param visible whether the view should be made visible.
   */
  void setVisible(boolean visible);
}
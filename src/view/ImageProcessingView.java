package view;

import model.ImageProcessingModelState;

/**
 * Represents the view of the image processor.
 */
public interface ImageProcessingView {

  /**
   * Renders all the layers.<br> The view shows the index, name, and visibility of each layer. If
   * the layer is set as the current layer, the view indicates so by the keyword "(current)".
   *
   * @param model the model whose data to render.
   * @throws IllegalStateException if the view fails to render the message.
   */
  void renderLayers(ImageProcessingModelState model) throws IllegalStateException;

  /**
   * Renders the given message.
   *
   * @param message the message to be rendered.
   * @throws IllegalStateException if the view fails to render the message.
   */
  void renderMessage(String message) throws IllegalStateException;

  /**
   * Renders the given error.
   *
   * @param message the message to be rendered.
   * @throws IllegalStateException if the view fails to render the error message.
   */
  void renderError(String message) throws IllegalStateException;

}

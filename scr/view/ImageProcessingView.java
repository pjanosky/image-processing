package view;

import java.io.IOException;

/**
 * Represents the view of the image processor.
 */
public interface ImageProcessingView {

  /**
   * Renders all the layers.<br> The view shows the index, name, and visibility of each layer. If
   * the layer is set as the current layer, the view indicates so by the keyword "(current)".
   *
   * @throws IOException if the view fails to append the output
   */
  void renderLayers() throws IOException;

  /**
   * Renders the given message.
   *
   * @param message the message to be rendered.
   * @throws IOException if the view fails to append the output
   */
  void renderMessage(String message) throws IOException;

}

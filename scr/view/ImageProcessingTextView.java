package view;

import java.io.IOException;
import model.ImageProcessingModelState;

/**
 * Displays data about the state of an the image processing program. Capable of rendering a simple
 * textual view of the different image layers as well as well as displaying textual messages.
 */
public class ImageProcessingTextView implements ImageProcessingView {

  private final ImageProcessingModelState model;
  private final Appendable output;

  /**
   * Constructs a new ImageProcessingTextView to display data from a model to a given output.
   *
   * @param model  the model to display the state of.
   * @param output the output to which text is rendered to.
   * @throws IllegalArgumentException if the model or output is null.
   */
  public ImageProcessingTextView(ImageProcessingModelState model, Appendable output)
      throws IllegalArgumentException {
    if (model == null || output == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    this.model = model;
    this.output = output;
  }

  @Override
  public void renderLayers() throws IOException {
    StringBuilder result = new StringBuilder();
    result.append("Layers:");
    for (int index = 0; index < model.numLayers(); index += 1) {
      result.append(System.lineSeparator());
      String name = model.getLayerNameAt(index);
      result.append(index + 1).append(". ").append(name).append(" (");
      if (model.isVisible(name)) {
        result.append('V');
      } else {
        result.append(' ');
      }
      result.append(')');
      if (name.equals(model.getCurrentName())) {
        result.append(" (current)");
      }
    }
    output.append(result.toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (message != null) {
      output.append(message);
    }
  }
}
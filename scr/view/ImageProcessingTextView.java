package view;

import java.io.IOException;
import model.ImageProcessingModelState;

/**
 * Displays data about the state of an the image processing program. Capable of rendering a simple
 * textual view of the different image layers as well as well as displaying textual messages.
 */
public class ImageProcessingTextView implements ImageProcessingView {

  private final Appendable output;

  /**
   * Constructs a new ImageProcessingTextView to display data to a given output.
   *
   * @param output the output to which text is rendered to.
   * @throws IllegalArgumentException if the output is null.
   */
  public ImageProcessingTextView(Appendable output)
      throws IllegalArgumentException {
    if (output == null) {
      throw new IllegalArgumentException("Output cannot be null.");
    }
    this.output = output;
  }

  @Override
  public void renderLayers(ImageProcessingModelState model) throws IllegalStateException {
    try {
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
      result.append(System.lineSeparator());
      output.append(result.toString());
    } catch (IOException e) {
      throw new IllegalStateException("Failed to render layers.");
    }
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {
    if (message != null) {
      try {
        output.append(message);
        output.append(System.lineSeparator());
      } catch (IOException e) {
        throw new IllegalStateException("Failed to render message.");
      }
    }
  }
}
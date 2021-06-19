package view;

import java.io.IOException;
import model.ImageProcessingModelState;

/**
 * Renders
 */
public class ImageProcessingTextView implements ImageProcessingView {

  private final ImageProcessingModelState model;
  private final Appendable output;

  public ImageProcessingTextView(ImageProcessingModelState model, Appendable output) {
    this.model = model;
    this.output = output;
  }

  @Override
  public void renderLayers() throws IOException {
    StringBuilder result = new StringBuilder();
    result.append("Layers:").append(System.lineSeparator());
    for (int index = 0; index < model.numLayers(); index += 1) {
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
      if (index < model.numLayers() - 1) {
        result.append(System.lineSeparator());
      }
    }
    output.append(result.toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    output.append(message);
  }
}


// Current Style Guide:

/*
1. Background (V) Current
2. Foreground ( )
3. Subject (V)
 */
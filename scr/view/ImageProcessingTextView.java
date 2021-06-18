package view;

import java.io.OutputStream;
import java.io.PrintStream;
import model.ImageProcessingModelState;

public class ImageProcessingTextView implements ImageProcessingView {

  private final ImageProcessingModelState model;
  private final PrintStream out;

  public ImageProcessingTextView(ImageProcessingModelState model, OutputStream output) {
    this.model = model;
    this.out = new PrintStream(output);
  }

  @Override
  public void renderLayers() {
    StringBuilder result = new StringBuilder();
    for (int index = 0; index < model.numLayers(); index += 1) {
      String name = model.getLayerNameAt(index);
      if (model.getCurrentName().equals(name)) {
        result.append("-> ");
      } else {
        result.append("   ");
      }
      result.append(index).append(". ").append(name).append('(');
      if (model.isVisible(name)) {
        result.append('V');
      } else {
        result.append(' ');
      }
      result.append(')').append(System.lineSeparator());
    }
    result.deleteCharAt(result.length() - 1);
    out.print(result.toString());
  }

  @Override
  public void renderMessage(String message) {
    out.print(message);
  }
}


// Current Style Guide:

/*
-> 1. Background (V)
   2. Foreground ( )
   3. Subject (V)
 */
package view;

import java.io.IOException;

public interface ImageProcessingView {

  void renderLayers() throws IOException;

  void renderMessage(String message) throws IOException;

  String toString();
}

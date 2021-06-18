package view;

import java.io.IOException;

public interface ImageProcessingView {

  void renderLayers();

  void renderMessage(String message);

  String toString();
}

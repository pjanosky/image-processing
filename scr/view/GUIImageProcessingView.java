package view;

import controller.GuiProcessingController;

public interface GUIImageProcessingView extends ImageProcessingView {

  void setController(GuiProcessingController features);

  void setVisible(boolean visible);
}
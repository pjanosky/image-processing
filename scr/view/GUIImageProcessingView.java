package view;

import controller.GuiProcessingController;

public interface GUIImageProcessingView extends ImageProcessingView {

  void addFeatures(GuiProcessingController features);

  void clearInputString();

  void setVisible(boolean visible);
}
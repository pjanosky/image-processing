package view;

import controller.GuiControllerFeatures;

public interface GUIImageProcessingView extends ImageProcessingView {

  void addFeatures(GuiControllerFeatures features);

}
package view;

import controller.CommandListener;

public interface GUIImageProcessingView extends ImageProcessingView {

  void addCommandListener(CommandListener features);

  void setVisible(boolean visible);
}
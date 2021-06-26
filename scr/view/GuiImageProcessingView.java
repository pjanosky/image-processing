package view;

import controller.CommandListener;

public interface GuiImageProcessingView extends ImageProcessingView {

  void addCommandListener(CommandListener features);

  void setVisible(boolean visible);
}
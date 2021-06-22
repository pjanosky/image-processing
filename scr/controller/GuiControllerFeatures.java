package controller;

import controller.commands.ControllerCommand;

public interface GuiControllerFeatures {
  void runCommand(ControllerCommand command);
}

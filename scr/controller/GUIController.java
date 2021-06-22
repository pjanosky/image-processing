package controller;

import controller.commands.ControllerCommand;

public interface GuiController {
  void runCommand(ControllerCommand command);
}


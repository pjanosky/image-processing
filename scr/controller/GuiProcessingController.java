package controller;

import controller.commands.ControllerCommand;

public interface GuiProcessingController extends ImageProcessingController {
  void runCommand(ControllerCommand command);
}


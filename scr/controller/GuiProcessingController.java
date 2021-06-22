package controller;

import controller.commands.ControllerCommand;
import model.Image;

public interface GuiProcessingController extends ImageProcessingController {
  void runCommand(ControllerCommand command);
}


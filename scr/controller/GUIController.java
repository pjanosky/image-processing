package controller;

import controller.commands.ControllerCommand;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.ImageProcessingModel;
import view.GUIView;

public class GUIController implements GuiProcessingController {

  private ImageProcessingModel model;
  private GUIView view;

  public GUIController(ImageProcessingModel model, GUIView view) throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Parameters for the controller cannot be null.");
    }

    this.model = model;
    this.view = view;

  }

  @Override
  public void run() {

  }

//  @Override
//  public void actionPerformed(ActionEvent e) {
//    switch(e.getActionCommand()) {
//      case "Blur":
//    }
//  }

  @Override
  public void runCommand(ControllerCommand command) {
    command.runCommand(model);
  }
}
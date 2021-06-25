package controller;

import controller.commands.ControllerCommand;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.Image;
import model.ImageProcessingModel;
import model.ImageProcessingModelState;
import model.ImageProcessingViewModel;
import view.GUIImageProcessingView;
import javax.swing.JFrame;
import view.GUIView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUIController implements GuiProcessingController {

  private ImageProcessingModel model;
  private ImageProcessingModelState viewModel;
  private GUIImageProcessingView view;

  public GUIController(ImageProcessingModel model, GUIImageProcessingView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Parameters for the controller cannot be null.");
    }

    this.model = model;
    this.viewModel = new ImageProcessingViewModel(model);
    this.view = view;
    view.setController(this);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
        | IllegalAccessException e) {
      view.renderMessage("Could not set look and feel.");
    }

    view.setVisible(true);
  }

  @Override
  public void run() {

  }

  @Override
  public void runCommand(ControllerCommand command) {
    try {
      command.runCommand(model, view);
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderMessage(e.getMessage());
    }
  }
}

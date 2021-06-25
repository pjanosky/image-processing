package controller;

import controller.commands.ControllerCommand;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.Image;
import model.ImageProcessingModel;
import view.GUIImageProcessingView;
import javax.swing.JFrame;
import view.GUIView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUIController implements GuiProcessingController {

  private ImageProcessingModel model;
  private GUIImageProcessingView view;

  public GUIController(ImageProcessingModel model, GUIImageProcessingView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Parameters for the controller cannot be null.");
    }

    this.model = model;
    this.view = view;
    view.setController(this);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
        | IllegalAccessException e) {
      renderMessage("Could not set look and feel.");
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
      renderMessage(e.getMessage());
    }
  }




  /**
   * Renders a message to the view.
   *
   * @param message the message to render
   */
  private void renderMessage(String message) throws IllegalStateException {
    try {
      view.renderMessage(message + System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("Failed to render message to output.");
    }
  }

  /**
   * Renders the layers of the model to the view.
   *
   * @throws IllegalStateException if writing the the Appendable object fails.
   */
  private void renderLayers() throws IllegalStateException {
    try {
      view.renderLayers();
      view.renderMessage(System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("Failed to render image layers to output.");
    }
  }
}

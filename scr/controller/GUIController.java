package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.ImageProcessingModel;
import view.GUIView;

public class GUIController implements ImageProcessingController, ActionListener {

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

  @Override
  public void actionPerformed(ActionEvent e) {
    switch(e.getActionCommand()) {
      case "Blur":
    }
  }
}


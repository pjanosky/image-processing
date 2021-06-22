package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    switch (e.getActionCommand()) {
      case "Blur":break;
      case "Sepia":break;
      case "Sharpen":break;
      case "Greyscale": break;
      case "Load image":
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this.view);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          File f = chooser.getSelectedFile();
//          System.out.println("You chose to open this file: " +
//              chooser.getSelectedFile().getName());
        }
        break;
      case "Save layer":
        final JFileChooser fchooser = new JFileChooser(".");
        int retvalue = fchooser.showSaveDialog(this.view);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
//          fileSaveDisplay.setText(f.getAbsolutePath());
        }
        break;
    }
  }

}
package controller;

import controller.commands.AddCommand;
import controller.commands.ControllerCommand;
import controller.commands.CurrentCommand;
import controller.commands.ImageProcessCommand;
import controller.commands.LoadCommand;
import controller.commands.LoadLayersCommand;
import controller.commands.MoveCommand;
import controller.commands.RemoveCommand;
import controller.commands.SaveCommand;
import controller.commands.SaveLayersCommand;
import controller.commands.SetImageCommand;
import controller.commands.VisibilityCommand;
import java.io.File;
import java.io.StringReader;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.ImageOperation;
import model.ImageOperationCreator;
import model.ImageProcessingModel;
import view.GUIImageProcessingView;


public class GUIController extends SimpleImageProcessingController implements CommandListener {

  GUIImageProcessingView view;

  public GUIController(ImageProcessingModel model, GUIImageProcessingView view)
      throws IllegalArgumentException {
    super(model, new StringReader(""), new StringBuilder());
    if (view == null) {
      throw new IllegalArgumentException("Parameters for the controller cannot be null.");
    }

    this.view = view;
    view.addCommandListener(this);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
        | IllegalAccessException e) {
      view.renderMessage("Could not set look and feel.");
    }
  }

  @Override
  public void run() {
    view.setVisible(true);
  }

  @Override
  public void add(String name) {
    runCommand(new AddCommand(name));
  }

  @Override
  public void remove() {
    runCommand(new RemoveCommand());
  }

  @Override
  public void current(String layerName) {
    runCommand(new CurrentCommand(layerName));
  }

  @Override
  public void move(int index) {
    runCommand(new MoveCommand(index));
  }

  @Override
  public void imageProcess(ImageOperationCreator.OperationType type) {
    ImageOperation operation = ImageOperationCreator.create(type);
    runCommand(new ImageProcessCommand(operation));
  }

  @Override
  public void load(File file) {
    runCommand(new LoadCommand(file.getAbsolutePath(), parseExtension(file)));
  }

  @Override
  public void loadLayers(File file) {
    runCommand(new LoadLayersCommand(file.getAbsolutePath()));
  }

  @Override
  public void save(File file) {
    runCommand(new SaveCommand(file.getAbsolutePath(), parseExtension(file)));
  }

  @Override
  public void saveLayers(File file, String name) {
    runCommand(new SaveLayersCommand(file.getAbsolutePath(), name));
  }

  @Override
  public void setImage(String type, String... args) {
    runCommand(new SetImageCommand(type, args));
  }

  @Override
  public void visibility(boolean isVisible) {
    runCommand(new VisibilityCommand(isVisible));
  }

  @Override
  public void script(String filePath) {
    runCommand(new ScriptCommand(filePath));
  }

  /**
   * Runs a controller command and renders any error messages to the view.
   *
   * @param command the command to run.
   */
  private void runCommand(ControllerCommand command) {
    try {
      command.runCommand(model, view);
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  /**
   * Parses the file extension for a file.
   *
   * @param file the file to get the extension of.
   * @return the file extension or an empty string if no extension can be parsed.
   */
  private String parseExtension(File file) {
    int extensionIndex = file.getName().lastIndexOf('.');
    if (extensionIndex < 0 || extensionIndex + 1 >= file.getName().length()) {
      return "";
    }
    return file.getName().substring(extensionIndex + 1);
  }
}

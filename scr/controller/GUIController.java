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
    try {
      view.setVisible(true);
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void add(String name) {
    try {
      runCommand(new AddCommand(name));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void remove() {
    try {
      runCommand(new RemoveCommand());
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void current(String layerName) {
    try {
      runCommand(new CurrentCommand(layerName));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void move(int index) {
    try {
      runCommand(new MoveCommand(index));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void imageProcess(ImageOperationCreator.OperationType type) {
    try {
      ImageOperation operation = ImageOperationCreator.create(type);
      runCommand(new ImageProcessCommand(operation));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void load(File file) {
    if (file == null) {
      return;
    }
    try {
      runCommand(new LoadCommand(file.getAbsolutePath(), parseExtension(file)));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void loadLayers(File file) {
    if (file == null) {
      return;
    }
    try {
      runCommand(new LoadLayersCommand(file.getAbsolutePath()));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void save(File file) {
    if (file == null) {
      return;
    }
    try {
      runCommand(new SaveCommand(file.getAbsolutePath(), parseExtension(file)));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void saveLayers(File file, String name) {
    if (file == null) {
      return;
    }
    try {
      runCommand(new SaveLayersCommand(file.getAbsolutePath(), name));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void setImage(String type, String... args) {
    try {
      runCommand(new SetImageCommand(type, args));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void visibility(boolean isVisible) {
    try {
      runCommand(new VisibilityCommand(isVisible));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void script(String filePath) {
    try {
      runCommand(new ScriptCommand(filePath));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  /**
   * Runs a controller command and renders any error messages to the view.
   *
   * @param command the command to run.
   */
  private void runCommand(ControllerCommand command) {
    command.runCommand(model, view);
    view.renderLayers(model);
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

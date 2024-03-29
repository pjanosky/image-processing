package controller;

import controller.commands.AddCommand;
import controller.commands.ControllerCommand;
import controller.commands.CurrentCommand;
import controller.commands.ImageProcessAllCommand;
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
import model.ImageProcessingModel;
import view.GuiImageProcessingView;

/**
 * Runs the image processing program with an interactive graphical user interface. Receives input
 * from the view and executes the corresponding image progressing commands. Extends TextController
 * to help with running text commands from script files.
 */
public class GuiController extends TextController implements CommandListener {

  private final GuiImageProcessingView view;

  /**
   * Constructs a new GuiController object.
   *
   * @param model the model used to manage the program data.
   * @param view  the view to render output and detect user input.
   * @throws IllegalArgumentException if the model or view is null.
   */
  public GuiController(ImageProcessingModel model, GuiImageProcessingView view)
      throws IllegalArgumentException {
    super(model, new StringReader(""), new StringBuilder());
    if (view == null) {
      throw new IllegalArgumentException("Parameters for the controller cannot be null.");
    }

    this.view = view;
  }

  @Override
  public void run() {
    view.setCommandListener(this);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
        | IllegalAccessException e) {
      view.renderError("Could not set look and feel.");
    }

    view.setVisible(true);
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
  public void move(String index) {
    try {
      runCommand(new MoveCommand(Integer.parseInt(index)));
    } catch (NumberFormatException e) {
      view.renderError("Invalid index.");
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void imageProcess(ImageOperation operation) {
    try {
      runCommand(new ImageProcessCommand(operation));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void imageProcessAll(ImageOperation operation) {
    try {
      runCommand(new ImageProcessAllCommand(operation));
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
      runCommand(new LoadCommand(file.getPath(), parseExtension(file)));
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
      runCommand(new LoadLayersCommand(file.getPath()));
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
      runCommand(new SaveCommand(file.getPath(), parseExtension(file)));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void saveLayers(File file) {
    if (file == null) {
      return;
    }
    String name = file.getName();
    String absolutePath = file.getPath();
    String path;
    int endIndex = absolutePath.length() - name.length() - 1;
    if (endIndex < 0 || endIndex >= absolutePath.length()) {
      view.renderError("Invalid file path.");
      return;
    } else {
      path = absolutePath.substring(0, endIndex);
    }

    try {
      runCommand(new SaveLayersCommand(path, name));
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderError(e.getMessage());
    }
  }

  @Override
  public void setImage(String type, String args) {
    try {
      if (args != null) {
        runCommand(new SetImageCommand(type, args.split(" ")));
      } else {
        view.renderError("Invalid arguments for " + type + ".");
      }
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
    view.renderLayers(viewModel);
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

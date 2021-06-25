package controller;

import controller.commands.AddCommand;
import controller.commands.ControllerCommand;
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
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import controller.commands.CurrentCommand;
import model.DownscaleOperation;
import model.ImageOperationCreator;
import model.ImageOperationCreator.OperationType;
import model.ImageProcessingModel;
import model.ImageProcessingModelState;
import model.ImageProcessingViewModel;
import view.ImageProcessingTextView;
import view.ImageProcessingView;

/**
 * Runs a simple image processing program.
 */
public class SimpleImageProcessingController implements ImageProcessingController {

  protected final ImageProcessingModel model;
  protected final ImageProcessingModelState viewModel;
  protected final ImageProcessingView view;
  private final Readable input;
  private final Map<String, Function<Scanner, ControllerCommand>> commands;

  /**
   * Constructs a {@code SimpleImageProcessingController} object.
   *
   * @param model  the image processing model
   * @param input  a Readable that serves as the scannable array of command line parameters
   * @param output an Appendable object to append and output the view on in text format
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public SimpleImageProcessingController(ImageProcessingModel model, Readable input,
      Appendable output) throws IllegalArgumentException {
    // Check validity of arguments
    if (model == null || input == null || output == null) {
      throw new IllegalArgumentException("Arguments must not be null.");
    }

    // Initialize fields
    this.model = model;
    this.view = new ImageProcessingTextView(output);
    this.viewModel = new ImageProcessingViewModel(model);
    this.input = input;

    // Add all of the supported commands
    commands = new HashMap<>();
    commands.put("save", s -> new SaveCommand(s.next(), s.next()));
    commands.put("load", s -> new LoadCommand(s.next(), s.next()));
    commands.put("show", s -> new VisibilityCommand(true));
    commands.put("hide", s -> new VisibilityCommand(false));
    commands.put("saveall", s -> new SaveLayersCommand(s.next(), s.next()));
    commands.put("loadall", s -> new LoadLayersCommand(s.next()));
    commands.put("add", s -> new AddCommand(s.next()));
    commands.put("remove", s -> new RemoveCommand());
    commands.put("sharpen", s -> new ImageProcessCommand(
        ImageOperationCreator.create(OperationType.SHARPEN)));
    commands.put("blur", s -> new ImageProcessCommand(
        ImageOperationCreator.create(OperationType.BLUR)));
    commands.put("sepia", s -> new ImageProcessCommand(
        ImageOperationCreator.create(OperationType.SEPIA)));
    commands.put("greyscale", s -> new ImageProcessCommand(
        ImageOperationCreator.create(OperationType.GREYSCALE)));
    commands.put("script", s -> new ScriptCommand(s.next()));
    commands.put("current", s -> new CurrentCommand(s.next()));
    commands.put("move", s -> new MoveCommand(s.nextInt()));
    commands.put("set", new SetImageCommandCreator());
    commands.put("downscale", s-> new ImageProcessAllCommand(
        new DownscaleOperation(s.nextDouble())));
  }

  @Override
  public void run() {
    view.renderMessage("Enter a command");
    runCommands(input);
    view.renderMessage("Quitting.");
  }

  /**
   * A helper method that runs the commands according to the user's input.<br> If the read command
   * is "q", then the whole program quits. If the read command is one of those that are saved in the
   * controller, then it delegates to the appropriate command and runs its function. If the read
   * command is not a member of the list of commands, it quits the program. If null, then it asks
   * the user for another command.
   *
   * @param in the command from the user and that has been read.
   */
  private void runCommands(Readable in) {
    Scanner scan = new Scanner(in);
    while (scan.hasNextLine()) {
      String line = scan.nextLine();
      Scanner lineScanner = new Scanner(line);
      String commandName = lineScanner.next();
      if (commandName.equalsIgnoreCase("q")) {
        return;
      }
      Function<Scanner, ControllerCommand> command = commands.get(commandName);
      if (command == null) {
        view.renderMessage("Unknown Command");
        continue;
      }
      try {
        command.apply(lineScanner).runCommand(model, view);
      } catch (NoSuchElementException e) {
        view.renderMessage("Invalid number of arguments for " + commandName + ".");
      } catch (IllegalArgumentException | IllegalStateException e) {
        view.renderMessage(e.getMessage());
        continue;
      }
      view.renderLayers(viewModel);
    }
    throw new IllegalStateException(
        "Reached the end of the provided Readable object without quitting the program.");
  }

  /**
   * A command that runs a script of other commands.
   */
  protected class ScriptCommand implements ControllerCommand {

    private final Readable input;

    /**
     * Creates a new script controller with.
     *
     * @param filePath the path to the script file to execute.
     * @throws IllegalArgumentException if the filePath is null or is not a path to a valid script
     *                                  file.
     */
    public ScriptCommand(String filePath) throws IllegalArgumentException {
      if (filePath == null) {
        throw new IllegalArgumentException("Arguments must not be null");
      }
      try {
        this.input = new FileReader(filePath);
      } catch (IOException e) {
        throw new IllegalArgumentException("Failed to read from scrip: " + filePath + ".");
      }
    }

    @Override
    public void runCommand(ImageProcessingModel model, ImageProcessingView view)
        throws IllegalStateException, IllegalArgumentException {
      if (model == null) {
        throw new IllegalArgumentException("Model cannot be null.");
      }
      runCommands(input);
    }
  }

  /**
   * A function for creating a SetImageCommand from a scanner over a line of user input.
   */
  private static class SetImageCommandCreator implements Function<Scanner, ControllerCommand> {

    @Override
    public ControllerCommand apply(Scanner scanner) {
      String type = scanner.next();

      List<String> arguments = new ArrayList<>();
      while (scanner.hasNext()) {
        arguments.add(scanner.next());
      }

      String[] args = new String[arguments.size()];
      for (int index = 0; index < arguments.size(); index += 1) {
        args[index] = arguments.get(index);
      }
      return new SetImageCommand(type, args);
    }
  }
}

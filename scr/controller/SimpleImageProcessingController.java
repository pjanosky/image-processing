package controller;

import controller.commands.HideCommand;
import controller.commands.LoadCommand;
import controller.commands.LoadLayersCommand;
import controller.commands.SaveCommand;
import controller.commands.SaveLayersCommand;
import controller.commands.ShowCommand;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.ImageProcessingModel;
import model.ImageProcessingViewModel;
import view.ImageProcessingTextView;
import view.ImageProcessingView;

/**
 * Runs a simple image processing program.
 */
public class SimpleImageProcessingController implements ImageProcessingController {
  private final ImageProcessingModel model;
  private final ImageProcessingView view;
  private final Scanner scan;
  private final Map<String, Function<Scanner, ControllerCommand>> commands;

  /*
  should we add a int type token to make the command process easier
   */

  SimpleImageProcessingController(ImageProcessingModel model, Readable input, Appendable output) {
    // Check validity of arguments
    if (model == null || input == null || output == null) {
      throw new IllegalArgumentException("Arguments must not be null.");
    }

    // Initialize fields
    this.model = model;
    this.view = new ImageProcessingTextView(new ImageProcessingViewModel(model), output);
    this.scan = new Scanner(input);

    // Add all of the supported commands
    commands = new HashMap<>();
    commands.put("save", s->new SaveCommand(s.next(), s.next()));
    commands.put("load", s->new LoadCommand(s.next(), s.next(), s.next()));
    commands.put("show", s->new ShowCommand(s.next()));
    commands.put("hide", s->new HideCommand(s.next()));
    commands.put("saveall", s->new SaveLayersCommand(s.next(), s.next(), s.next()));
    commands.put("loadall", s->new LoadLayersCommand(s.next()));
  }

  @Override
  public void run() {
    while (scan.hasNext()) {
      String input = scan.next();
      if (input.toLowerCase().equals("q")) {
        return;
      }
      Function<Scanner, ControllerCommand> command = commands.get(input);
      if (command == null) {
        renderMessage("Unknown Command");
      }
      command.apply(scan).go(model);
    }
  }

  /*
  controller should be able to support the following commands:
  load/save images, add/remove layer, blur, sharpen, make the layer greyscale or sepia,
  saving all the layers, run a batch script file

   */

  /**
   * Renders a message to the view.
   *
   * @param message the message to render
   * @throws IllegalArgumentException if writing the the Appendable object fails.
   */
  private void renderMessage(String message) throws IllegalArgumentException {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to render message to output.");
    }
  }

  /**
   * Renders the layers of the model to the view.
   *
   * @throws IllegalArgumentException if writing the the Appendable object fails.
   */
  private void renderLayers() throws IllegalArgumentException {
    try {
      view.renderLayers();
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to render image layers to output.");
    }
  }
}

package controller;

import controller.commands.SaveCommand;
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

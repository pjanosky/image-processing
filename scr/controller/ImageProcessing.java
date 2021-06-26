package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import view.GuiImageProcessingView;
import view.GuiView;

/**
 * An entry point for running the Image Processing program.
 */
public class ImageProcessing {

  /**
   * Runs the image processing program. Creates a new controller, model and view, and runs the
   * controller.
   *
   * @param args the arguments for the program. Currently 2 arguments are supported: <ul>
   *             <li>-script path-to-script-file: runs batch commands in a script text file. Does
   *             not support interactive scripting.</li> <li>-text: run the image processing program
   *             with a text-based interface. Commands and output are entered and displayed on the
   *             command line. Supports interactive scripting.</li> <li>-interactive: runs the image
   *             processing program with a interactive graphical user interface. Supports
   *             interactive scripting. </li> <li>running the program with no arguments will default
   *             to the graphic user interface.</li> </ul>
   */
  public static void main(String[] args) {
    ImageProcessingModel model = new ImageProcessingModelImpl();

    if (args.length == 0) {
      runInteractive(model);
    } else {
      switch (args[0]) {
        case "-script":
          runScript(model, args);
          break;
        case "-text":
          runText(model);
          break;
        case "-interactive":
          runInteractive(model);
          break;
        default:
          System.out.println("Invalid command-line arguments.");
      }
    }
  }

  /**
   * Run the program in text mode.
   *
   * @param model the model object to use to manage the program's data.
   */
  public static void runText(ImageProcessingModel model) {
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    ImageProcessingController controller =
        new TextController(model, input, output);
    controller.run();
  }

  /**
   * Runs the program with a interactive graphical user interface.
   *
   * @param model the model object to use to manage the program's data.
   */
  public static void runInteractive(ImageProcessingModel model) {
    GuiImageProcessingView view = new GuiView();
    ImageProcessingController controller = new GuiController(model, view);
    controller.run();
  }

  /**
   * Runs the script.
   *
   * @param model the model object to use to manage the program's data.
   * @param args  the arguments identifying the script file
   */
  public static void runScript(ImageProcessingModel model, String[] args) {
    if (args.length < 2) {
      System.out.println("No path of script file.");
      return;
    }
    try {
      Readable input = new FileReader(args[1]);
      Appendable output = System.out;
      ImageProcessingController controller =
          new TextController(model, input, output);
      controller.run();
    } catch (FileNotFoundException e) {
      System.out.println("Fail to read script file. " + e.getMessage());
    }
  }
}

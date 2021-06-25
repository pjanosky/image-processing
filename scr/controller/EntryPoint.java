package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import view.GUIImageProcessingView;
import view.GUIView;
import view.ImageProcessingTextView;

/**
 * An entry point for running the program
 */
public class EntryPoint {

  /**
   * Runs the image processing program. Creates a new controller, model and view, and runs the
   * controller.
   *
   * @param args the arguments for the program. Currently being ignored.
   */
  public static void main(String[] args) {
    ImageProcessingModel model = new ImageProcessingModelImpl();
    ImageProcessingController controller;

    if (args.length == 0) {
      GUIImageProcessingView view = new GUIView(model);
      controller = new GUIController(model, view);
      controller.run();
    } else switch (args[0]) {
      case "-script":
        if (args.length < 2) {
          System.out.println("No path of script file.");
        }
        try {
          controller = new SimpleImageProcessingController(model,
              new FileReader(args[1]), System.out);
          controller.run();
        } catch (FileNotFoundException e) {
          System.out.println("Fail to read script file. " + e.getMessage());
        }
        break;
      case "-text":
        controller = new SimpleImageProcessingController(model,
            new InputStreamReader(System.in), System.out);
        controller.run();
        break;
      case "-interactive":
        GUIImageProcessingView view = new GUIView(model);
        controller = new GUIController(model, view);
        controller.run();
        break;
      default:
        System.out.println("Invalid command-line arguments.");
    }
  }
}

package controller;

import java.io.InputStreamReader;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;

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
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    ImageProcessingController controller =
        new SimpleImageProcessingController(model, input, output);
    controller.run();
  }
}

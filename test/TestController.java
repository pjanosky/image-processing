import controller.ImageProcessingController;
import controller.SimpleImageProcessingController;
import java.io.InputStreamReader;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;

/**
 * A simple controller for the sole purpose of producing images for testing. Produces sample images
 * using ImageProcessingModelImp.
 */
public class TestController {

  public static void main(String[] args) {
    ImageProcessingModel model = new ImageProcessingModelImpl();
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    ImageProcessingController controller =
        new SimpleImageProcessingController(model, input, output);
    controller.run();
  }
}

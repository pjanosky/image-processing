import java.util.Locale;
import model.ImageImportExporter;
import model.ImageOperation;
import model.ImageOperationCreator;
import model.ImageOperationCreator.IMGOperationType;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.PpmImportExporter;

/**
 * A simple controller for the sole purpose of producing images for testing. Produces sample images
 * using ImageProcessingModelImp.
 */
public class TestController {

  /**
   * Runs the TestController applying a list of operations to a list of images specified by file
   * paths.
   *
   * @param args the arguments for the program which are ignored.
   */
  public static void main(String[] args) {
    ImageProcessingModel model = new ImageProcessingModelImpl();
    ImageImportExporter importExporter = new PpmImportExporter();

    String[] imageFilePaths = {
        "res/flowers.ppm",
        "res/panda.ppm"
    };

    IMGOperationType[] operations = {
        IMGOperationType.BLUR,
        IMGOperationType.SHARPEN,
        IMGOperationType.GREYSCALE,
        IMGOperationType.SEPIA
    };

    for (String filePath : imageFilePaths) {
      for (IMGOperationType opType : operations) {
        model.importImage(importExporter, filePath);
        model.applyOperation(ImageOperationCreator.create(opType));
        String newPath = filePath.replace(".ppm",
            "_" + opType.name().toLowerCase() + ".ppm");
        model.exportCurrentImage(importExporter, newPath);
      }
    }
  }
}

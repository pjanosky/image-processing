import controller.ImageImportExporter;
import model.ImageOperationCreator.OperationType;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import controller.PpmImportExporter;

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

    OperationType[] operations = {
        OperationType.BLUR,
        OperationType.SHARPEN,
        OperationType.GREYSCALE,
        OperationType.SEPIA
    };

//    for (String filePath : imageFilePaths) {
//      model.importImage(importExporter, filePath);
//      for (OperationType opType : operations) {
//        model.applyOperation(ImageOperationCreator.create(opType));
//        String newPath = filePath.replace(".ppm",
//            "_" + opType.name().toLowerCase() + ".ppm");
//        model.exportCurrentImage(importExporter, newPath);
//        model.revert();
//      }
//    }
  }
}

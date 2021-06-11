package model;

/**
 * Encapsulates all different types of models. A model class that implements this interface imports,
 * exports, and processes different types of images. One example of the type of image file is .ppm
 * file.
 */
public interface ImageProcessingModel extends ImageProcessingModelState {


  /**
   * Sets the given image as the current image to be edited in the model.
   *
   * @param image the image to set as the current image as
   */
  void setImage(Image image);


  /**
   * Applies the given operation to the current image and sets the changed image as the current
   * image. One can blur, sharpen, change the image to greyscale or sepia, etc.
   *
   * @param operation the operation to be done on the current image
   */
  void applyOperation(ImageOperation operation);

  /**
   * Exports the current image of the model as a file.
   *
   * @param importExporter the appropriate import-exporter for the wanted file format
   * @param filePath       the file path for the to-be-exported file
   */
  void exportCurrentImage(ImageImportExporter importExporter, String filePath);

  /**
   * Imports the given file as an Image object and set as the current image of the model.
   *
   * @param importExporter the appropriate import-exporter for the given file
   * @param filePath       the file path to the to-be-imported file
   */
  void importImage(ImageImportExporter importExporter, String filePath);

  void revert();
}

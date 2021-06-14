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

  void addLayer(String name);

  void setCurrentLayer(String name);

//   add back if we need it
//   void setLayerName(String layerName, String newName);


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


/*
Add layerer support to ImageProcessingModel and ImageProcessingModelState
  IO operation should be moved to the controller

Add a controller interface and implementation (command design pattern)
  Process textual commands (from script or typed by the user)
  Exporting single layers (to disk)
  Exporting multiple layers (to disk)

View interface and textual view implementation

New JPEG and PNG ImageImportExporter classes

Make sure we can support blending (model or controller or layers???)
*/
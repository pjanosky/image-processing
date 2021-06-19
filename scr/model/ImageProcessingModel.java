package model;

/**
 * Encapsulates all different types of models. A model class that implements this interface imports,
 * exports, and processes different types of images. One example of the type of image file is .ppm
 * file.
 */
public interface ImageProcessingModel extends ImageProcessingModelState {

  void addLayer(String name) throws IllegalArgumentException;

  void setCurrentLayer(String name) throws IllegalArgumentException;

  void setLayerImage(String layerName, Image image) throws IllegalArgumentException;

  void showLayer(String layerName, boolean isVisible) throws IllegalArgumentException;

  void applyOperation(String layerName, ImageOperation operation) throws IllegalArgumentException;

  void removeLayer(String layerName) throws IllegalArgumentException;

  void reorderLayer(String layerName, int index) throws IllegalArgumentException;


//   add back if we need it
//   void setLayerName(String layerName, String newName);
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
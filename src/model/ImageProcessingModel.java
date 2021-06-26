package model;

/**
 * Encapsulates all different types of models. A model class that implements this interface imports,
 * exports, and processes different types of images. One example of the type of image file is .ppm
 * file.
 */
public interface ImageProcessingModel extends ImageProcessingModelState {

  /**
   * Adds a layer with the given name.
   *
   * @param name the name to name the newly added layer as
   * @throws IllegalArgumentException if the name is null or if there exists a layer with the same
   *                                  name
   */
  void addLayer(String name) throws IllegalArgumentException;

  /**
   * Changes the current layer to the given layer (indicated by the name of it).
   *
   * @param name the name of the layer the user wants to set the current layer as
   * @throws IllegalArgumentException if the name is null or if there does not exist a layer with
   *                                  the given name
   */
  void setCurrentLayer(String name) throws IllegalArgumentException;

  /**
   * Sets (or changes) the image of the given layer (indicated by the name of it given as the
   * parameter).
   *
   * @param layerName the name of the layer the user wants to set the image of
   * @param image     the image the user wants to set
   * @throws IllegalArgumentException if either parameters are null or if the given image does not
   *                                  match the dimensions of the images in other layers or if there
   *                                  is no layer with the given name
   */
  void setLayerImage(String layerName, Image image) throws IllegalArgumentException;

  /**
   * Change the visibility of the given layer (indicated by the name of it given as the parameter).
   *
   * @param layerName the name of the layer the user wants to change the visibility of
   * @param isVisible whether the user wants the layer visible or not
   * @throws IllegalArgumentException if the layer name is null or if there is no layer with the
   *                                  given name
   */
  void showLayer(String layerName, boolean isVisible) throws IllegalArgumentException;

  /**
   * Applies the image operation to the given image (indicated by the name of it given as the
   * parameter).
   *
   * @param layerName the name of the layer the user wants to apply the operation on
   * @param operation the image operation the user wants to apply
   * @throws IllegalArgumentException if either parameters are null or if the given image does not
   *                                  match the dimensions of the images in other layers or if there
   *                                  is no layer with the given name
   */
  void applyOperation(String layerName, ImageOperation operation) throws IllegalArgumentException;

  /**
   * Removes the given layer (indicated by the name of it given as the parameter).
   *
   * @param layerName the name of the layer the user wants to remove
   * @throws IllegalArgumentException if the name is null or if there is no layer with the given
   *                                  name
   */
  void removeLayer(String layerName) throws IllegalArgumentException;

  /**
   * Reorders the given layer (indicated by the name of it) to the place where it was indicated by
   * the index.
   *
   * @param layerName the name of the layer the user wants to reorder
   * @param index     the index where the user wants to reorder the layer to
   * @throws IllegalArgumentException if the name is null, if the index is out of bounds or
   *                                  negative, or if the given layer doesn't exist
   */
  void reorderLayer(String layerName, int index) throws IllegalArgumentException;

}

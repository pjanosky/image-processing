package model;

/**
 * Includes all the methods the model implementation should include in order to return the different
 * states of the given model. There are no mutable methods included, for it is only to return the
 * states of the model at any given point in time.
 */
public interface ImageProcessingModelState {

  /**
   * Gets the name of the current layer.
   *
   * @return the name of the current layer
   */
  String getCurrentName();

  /**
   * Returns whether the given layer (indicated by the name of it) is visible or not.
   *
   * @param layerName the name of the layer the user wants the visibility status of
   * @return true if visible, false if hidden.
   * @throws IllegalArgumentException if the name is null or if there is no layer with the given
   *                                  name
   */
  boolean isVisible(String layerName) throws IllegalArgumentException;

  /**
   * Gets the image of the given layer (indicated by the name of it).
   *
   * @param layerName the name of the layer the user wants the image of
   * @return the image stored in the given layer
   * @throws IllegalArgumentException if the name is null or if there is no layer with the given
   *                                  name
   */
  Image getImageIn(String layerName) throws IllegalArgumentException;

  /**
   * Gets the name of the layer at the given index.
   *
   * @param index the index of the layer
   * @return the name of the layer at the given index
   * @throws IllegalArgumentException if the given index is negative or out of bounds
   */
  String getLayerNameAt(int index) throws IllegalArgumentException;

  /**
   * Returns the number of layers.
   *
   * @return the number of layers
   */
  int numLayers();
}
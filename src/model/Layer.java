package model;

/**
 * Represents and encapsulates all types of layers.
 */
public interface Layer {

  /**
   * Sets the name of the layer with the given string.
   *
   * @param name the name of the layer
   * @throws IllegalArgumentException if the given name is null
   */
  void setName(String name) throws IllegalArgumentException;

  /**
   * Sets the image of the layer.
   *
   * @param image the image to set the layer with
   * @throws IllegalArgumentException if the image is null
   */
  void setImage(Image image) throws IllegalArgumentException;

  /**
   * Changes the visibility of the layer.
   *
   * @param isVisible true if visible, false if hidden.
   */
  void show(boolean isVisible);

  /**
   * Applies the given operation on the layer.
   *
   * @param operation the operation to apply on the layer
   * @throws IllegalStateException    if the image of the layer is null
   * @throws IllegalArgumentException if the operation is null
   */
  void apply(ImageOperation operation) throws IllegalStateException, IllegalArgumentException;

  /**
   * Gets the name of the layer.
   *
   * @return the name of the layer
   */
  String getName();

  /**
   * Gets the image of the layer.
   *
   * @return the image of the layer
   */
  Image getImage();

  /**
   * Returns the visibility state of the layer.
   *
   * @return true if visible, false if hidden
   */
  boolean isVisible();
}


package model;

/**
 * Includes all the methods the model implementation should include in order to return the different
 * states of the given model. There are no mutable methods included, for it is only to return the
 * states of the model at any given point in time.
 */
public interface ImageProcessingModelState {

  /**
   * Gets the current image saved in the model.
   *
   * @return the current image
   */
  Image getCurrentImage();

  Image getOriginalImage();
}

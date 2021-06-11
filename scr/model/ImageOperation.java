package model;

/**
 * Represents an operation that can be applied to an {code Image} to alter it's appearance.
 */
public interface ImageOperation {

  /**
   * Generates a new image that is the result of applying this operation to a given image to alter
   * its appearance.
   *
   * @param image the image to apply the operation to
   * @return the new image that results from applying the operation
   */
  Image apply(Image image);
}
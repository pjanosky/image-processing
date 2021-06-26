package model;


/**
 * A factory class for creating different default ImageOperation objects.
 */
public class ImageOperationCreator {

  /**
   * Constructs a ImageOperation object based on a given type.
   *
   * @param type the type of Image Operation to create
   * @return the ImageOperationObject
   * @throws IllegalArgumentException if the image operation type is null or invalid.
   */
  public static ImageOperation create(OperationType type) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("Image operation type must not be null");
    }

    switch (type) {
      case BLUR:
        return new FilterOperation(new double[][]{
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}});
      case SHARPEN:
        return new FilterOperation(new double[][]{
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}});
      case GREYSCALE:
        return new ColorTransformation(new double[][]{
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}});
      case SEPIA:
        return new ColorTransformation(new double[][]{
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}});
      default:
        throw new IllegalArgumentException("Unknown image operation type has been passed!");
    }
  }

  /**
   * Represents the different types of default ImageOperation that can be created with the {@link
   * ImageOperationCreator} class.
   */
  public enum OperationType {
    BLUR, SHARPEN, GREYSCALE, SEPIA;
  }
}
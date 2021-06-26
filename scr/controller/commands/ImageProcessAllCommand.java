package controller.commands;

import model.Image;
import model.ImageOperation;
import model.ImageProcessingModel;
import view.ImageProcessingView;

/**
 * Applies one image operation to every image stored in the models layers.
 */
public class ImageProcessAllCommand implements ControllerCommand {

  private final ImageOperation operation;

  /**
   * Constructs a {@code ImageProcessCommand} object.
   *
   * @param operation the image operation that is called and is available in the model
   * @throws IllegalArgumentException if the given operation object is null
   */
  public ImageProcessAllCommand(ImageOperation operation) throws IllegalArgumentException {
    if (operation == null) {
      throw new IllegalArgumentException("The parameters cannot be null!");
    }
    this.operation = operation;
  }

  @Override
  public void runCommand(ImageProcessingModel model, ImageProcessingView view)
      throws IllegalStateException, IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }

    for (int index = 0; index < model.numLayers(); index += 1) {
      String layerName = model.getLayerNameAt(index);
      Image image = model.getImageIn(layerName);
      if (image != null) {
        model.applyOperation(layerName, operation);
      }
    }
    view.renderMessage("Applied operation to all layers.");
  }
}

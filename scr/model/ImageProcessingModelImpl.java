package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A implementation of the image processing model interface. The model manages the different layers
 * and associated images.
 */
public class ImageProcessingModelImpl implements ImageProcessingModel {

  private final List<Layer> layers;
  private String current;

  private static int layerIndexNum = 1;

  /**
   * Constructs a {@code ImageProcessingModelImpl} object.<br> The current image is initialized as
   * null because when the model is first constructed, no image is passed to be set as the current
   * image. Due to the same reason, the list of layers is empty until the client creates a layer.
   */
  public ImageProcessingModelImpl() {
    this.layers = new ArrayList<>();
    this.current = null;
  }

  @Override
  public void addLayer(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Layer name must not be null.");
    }
    if (hasLayer(name)) {
      throw new IllegalArgumentException("Layer \"" + name + "\" already exists.");
    }

    if (name.isEmpty()) {
      name = "Layer" + layerIndexNum;
      layerIndexNum += 1;
    }
    Layer layer = new Layer24Bit(name);
    layers.add(layer);
    current = name;
  }

  @Override
  public void setCurrentLayer(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    if (!hasLayer(name)) {
      throw new IllegalArgumentException("No layer named " + name + ".");
    }
    current = name;
  }

  @Override
  public void setLayerImage(String layerName, Image image) {
    if (layerName == null || image == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    if (!hasLayer(layerName)) {
      throw new IllegalArgumentException("No layer named " + layerName + ".");
    }
    ensureImageSize(image);
    getLayer(layerName).setImage(image);
  }

  @Override
  public void showLayer(String layerName, boolean isVisible) {
    if (layerName == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    if (!hasLayer(layerName)) {
      throw new IllegalArgumentException("No layer named " + layerName + ".");
    }
    getLayer(layerName).show(isVisible);
  }

  @Override
  public void applyOperation(String layerName, ImageOperation operation) {
    if (layerName == null || operation == null) {
      throw new IllegalArgumentException("Parameters cannot be null.");
    }
    if (!hasLayer(layerName)) {
      throw new IllegalArgumentException("No layer named " + layerName + ".");
    }
    getLayer(layerName).apply(operation);
  }

  @Override
  public void removeLayer(String layerName) {
    if (layerName == null || !hasLayer(layerName)) {
      throw new IllegalArgumentException("Invalid layer name! The layer either does not exist or"
          + "the parameter is null");
    }
    layers.remove(getLayer(layerName));
    if (layerName.equals(current)) {
      current = null;
    }
  }

  @Override
  public void reorderLayer(String layerName, int index) {
    if (layerName == null ) {
      throw new IllegalArgumentException("Layer name cannot be null.");
    }
    if (index < 0 || index >= numLayers()) {
      throw new IllegalArgumentException("Invalid index.");
    }

    int layerIndex = -1;
    for (int i = 0; i < numLayers(); i += 1) {
      if (layers.get(i).getName().equals(layerName)) {
        layerIndex = i;
      }
    }
    if (layerIndex == -1) {
      throw new IllegalArgumentException("No layer named " + layerName + ".");
    }
    layers.add(index, layers.remove(layerIndex));
  }

  @Override
  public String getCurrentName() {
    return current;
  }

  @Override
  public boolean isVisible(String layerName) {
    if (layerName == null || !hasLayer(layerName)) {
      throw new IllegalArgumentException("Invalid layer name! The layer either does not exist or"
          + "the parameter is null");
    }
    return getLayer(layerName).isVisible();
  }

  @Override
  public Image getImageIn(String layerName) {
    if (layerName == null || !hasLayer(layerName)) {
      throw new IllegalArgumentException("Invalid layer name! The layer either does not exist or"
          + "the parameter is null");
    }
    return getLayer(layerName).getImage();
  }

  @Override
  public String getLayerNameAt(int index) {
    if (index < 0 || index >= numLayers()) {
      throw new IllegalArgumentException("Invalid index.");
    }
    return layers.get(index).getName();
  }

  @Override
  public int numLayers() {
    return layers.size();
  }

  /**
   * Determines whether the model has a layer with a given name.
   *
   * @param name the name of the layer to check for.
   * @return whether the model has a layer with the name.
   */
  private boolean hasLayer(String name) {
    for (Layer layer : layers) {
      if (layer.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Finds and returns the layer with the given name.
   *
   * @param name the name of the layer to be returned.
   * @return the layer with the given name.
   * @throws IllegalArgumentException if there is no layer that has the given name.
   */
  private Layer getLayer(String name) {
    for (Layer layer : layers) {
      if (layer.getName().equals(name)) {
        return layer;
      }
    }
    throw new IllegalArgumentException("No layer named " + name + ".");
  }

  /**
   * Ensures that an image is the same size as the images in the other layers if there are images in
   * the other layers.
   *
   * @param image the image to check the dimensions of.
   * @throws IllegalArgumentException if the image does not match the dimension of the images in
   *                                  other layers.
   */
  private void ensureImageSize(Image image) throws IllegalArgumentException {
    for (Layer layer : layers) {
      Image layerImage = layer.getImage();
      if (layerImage != null &&
          (layerImage.getWidth() != image.getWidth()
              || layerImage.getHeight() != image.getHeight())) {
        throw new IllegalArgumentException("All layers must have images of the same size: "
            + image.getWidth() + "x" + image.getHeight() + ".");
      }
    }
  }
}

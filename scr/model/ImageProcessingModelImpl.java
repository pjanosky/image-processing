package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A implementation of the image processing model interface. The model manages the different layers
 * and associated images.
 */
public class ImageProcessingModelImpl implements ImageProcessingModel {

  private final List<Layer> layers;
  private Layer current;

  private static int layerIndexNum = 0;

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
    current = layer;
  }

  @Override
  public void setCurrentLayer(String name) {
    current = getLayer(name);
  }

  @Override
  public void showCurrent(boolean isVisible) {
    checkCurrent();
    current.show(isVisible);
  }

  @Override
  public void show(String layerName, boolean isVisible) {
    if (layerName == null) {
      throw new IllegalArgumentException("The layer name cannot be null!");
    }
    getLayer(layerName).show(isVisible);
  }

  @Override
  public void applyOperationCurrent(ImageOperation operation) {
    checkCurrent();
    current.apply(operation);
  }

  @Override
  public void applyOperation(String layerName, ImageOperation operation) {
    if (layerName == null || operation == null) {
      throw new IllegalArgumentException("The parameters cannot be null!");
    }

    getLayer(layerName).apply(operation);

  }

  @Override
  public void removeLayer(String name) {
    layers.remove(getLayer(name));
  }

  @Override
  public String getCurrentName() {
    return current.getName();
  }

  @Override
  public boolean isVisible(String layerName) {
    return getLayer(layerName).isVisible();
  }

  @Override
  public Image getImageIn(String layerName) {
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
   * Checks whether there is a layer is stored as the current layer of the model.
   * @throws IllegalStateException if there is no layer (i.e., there is a null value).
   */
  private void checkCurrent() throws IllegalStateException {
    if (current == null) {
      throw new IllegalStateException("No current layer.");
    }
  }
}

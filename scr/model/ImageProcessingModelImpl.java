package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A implementation of the image processing model interface. The model manages the different
 * layers and associated images.
 */
public class ImageProcessingModelImpl implements ImageProcessingModel {

  private final List<Layer> layers;
  private Layer current;

  private static int layerIndexNum = 0;

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
  public void applyOperationCurrent(ImageOperation operation) {
    checkCurrent();
    current.apply(operation);
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

  private Layer getLayer(String name) {
    for (Layer layer : layers) {
      if (layer.getName().equals(name)) {
        return layer;
      }
    }
    throw new IllegalArgumentException("No layer named " + name + ".");
  }

  private void checkCurrent() throws IllegalStateException {
    if (current == null) {
      throw new IllegalStateException("No current layer.");
    }
  }
}

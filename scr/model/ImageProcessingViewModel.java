package model;

/**
 * An object adapter to hide mutating model methods from a view.
 */
public class ImageProcessingViewModel implements ImageProcessingModelState {
  private final ImageProcessingModelState delegate;

  public ImageProcessingViewModel(ImageProcessingModelState delegate) {
    this.delegate = delegate;
  }

  @Override
  public String getCurrentName() {
    return delegate.getCurrentName();
  }

  @Override
  public boolean isVisible(String layerName) {
    return delegate.isVisible(layerName);
  }

  @Override
  public Image getImageIn(String layerName) {
    return delegate.getImageIn(layerName);
  }

  @Override
  public String getLayerNameAt(int index) {
    return delegate.getLayerNameAt(index);
  }

  @Override
  public int numLayers() {
    return delegate.numLayers();
  }

  @Override
  public Image topVisibleImage() {
    return delegate.topVisibleImage();
  }
}

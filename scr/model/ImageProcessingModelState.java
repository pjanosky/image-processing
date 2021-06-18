package model;

/**
 * Includes all the methods the model implementation should include in order to return the different
 * states of the given model. There are no mutable methods included, for it is only to return the
 * states of the model at any given point in time.
 */
public interface ImageProcessingModelState {

  String getCurrentName();

  boolean isVisible(String layerName);

  Image getImageIn(String layerName);

  String getLayerNameAt(int index);

  int numLayers();
}

/*

Blur applied to layer first


1. first "manhattan.png" (visible) <-- we can change if we figure out a way to represent visibility
2. second
3. thrid



Controller
get of number of layers
for i = 0; i < num of layers; i ++ {
  get the name of layer i
  get the file name of layer i
  get the visibility of layer i
}
*/
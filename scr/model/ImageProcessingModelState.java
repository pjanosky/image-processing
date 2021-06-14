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



  // Option 1
  boolean isLayerVisible(String name);

  // gets an image from a specific layer
  Image getImage(String layerName);

  String layerName(int index);

  int getNumLayers();


  // get the combination of blending all the visible layers (for assignment 7)
//   Image ...();





  // Option 2
//  Layer getLayer(String name);
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
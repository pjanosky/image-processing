package controller;

import java.io.File;
import model.ImageOperation;

/**
 * A listener that responds to user input to execute image progressing commands. Methods are called
 * directly from the view, so implementations should not throw exceptions.
 */
public interface CommandListener {

  /**
   * Adds a new layer with the given name.
   *
   * @param name the name of the new layer.
   */
  void add(String name);

  /**
   * Removes the current layer.
   */
  void remove();

  /**
   * Set the current layer to the layer with the given name.
   *
   * @param layerName the name of the layer to make current.
   */
  void current(String layerName);

  /**
   * Moves the current layer to the specified index.
   *
   * @param index the index as a string.
   */
  void move(String index);

  /**
   * Applies an image processing operation to the current layer.
   *
   * @param operation the operation to apply to the image in the current layer.
   */
  void imageProcess(ImageOperation operation);

  /**
   * Applies an image processing operation to all images in all layers.
   *
   * @param operation the operation to apply to every image.
   */
  void imageProcessAll(ImageOperation operation);

  /**
   * Loads an imag from disk into the current layer.
   *
   * @param file the file representing the path to the image to load.
   */
  void load(File file);

  /**
   * load multiple layers and corresponding images from disk. Restores the saved state of the image
   * processing program including layers, images, and visibility statuses.
   *
   * @param file the file representing the path to the directory where the layers are saved.
   */
  void loadLayers(File file);

  /**
   * Saves the image in the current layer to disk.
   *
   * @param file the file representing the path where the current layer's image will be saved.
   */
  void save(File file);

  /**
   * Saves all the layers in the model to disk. Saves the entire state of the program including
   * layers, images, and visibility statuses.
   *
   * @param file  the file representing teh path to the directory where the layers will be saved.
   */
  void saveLayers(File file);

  /**
   * Set the image of the current layer to one of the programmatically created preset images.
   *
   * @param type a string representing the type of programmatic image to create.
   * @param args the parameters for creating the image dependent on which type of image is being
   *             created.
   */
  void setImage(String type, String args);

  /**
   * Sets the visibility status of the current layer. Specifically, hides or shows the current
   * layer.
   *
   * @param isVisible whether the current layer should be set to be shown.
   */
  void visibility(boolean isVisible);

  /**
   * Runs a scrip file containing textual batch commands at the specified file path on disk.
   *
   * @param filePath a string representing the file path of a script file on disk.
   */
  void script(String filePath);
}


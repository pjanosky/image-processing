package model;

/**
 * Represents the layer in 24 bits. Each layer, by default, follows the naming conventions of
 * "Layer <i>n</i>" when constructed, but the client always has the option to change the name of the
 * layer. The client is allowed to apply any available operation(s) onto this layer.
 */
public class Layer24Bit implements Layer {

  private String name;
  private Image image;
  private boolean isVisible;

  /**
   * Constructs a {@code Layer24Bit} object.<br> The name of the given layer follows the default
   * naming convention. The number passed as the parameter represents the index number of the
   * previous layer, which will be used to determine the layer number of this layer.
   *
   * @param layerIndexNum the index number of the previous layer
   */
  public Layer24Bit(int layerIndexNum) {
    if (layerIndexNum < 0) { //get the max number of layers in the model
      throw new IllegalArgumentException("The layer number cannot be out of bounds");
    }
    this.name = "Layer" + (layerIndexNum + 1);
    this.image = null;
    this.isVisible = true;
  }

  /**
   * Constructs and {@code Layer24Bit} object with a specific name.
   *
   * @param name the name of the layer.
   */
  public Layer24Bit(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    this.name = name;
    this.image = null;
    this.isVisible = true;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setImage(Image image) {
    this.image = image;
  }

  @Override
  public Image getImage() {
    return image;
  }

  @Override
  public void apply(ImageOperation operation) {
    if (image == null) {
      throw new IllegalStateException("The image right now is null!");
    }
    if (operation == null) {
      throw new IllegalArgumentException("Image operation cannot be null");
    }
    image = operation.apply(image);
  }

  @Override
  public void show(boolean isVisible) {
    this.isVisible = isVisible;
  }

  @Override
  public boolean isVisible() {
    return this.isVisible;
  }

  @Override
  public void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    this.name = name;
  }
}

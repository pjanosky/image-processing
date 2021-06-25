import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import model.Image;
import model.ImageExamples;
import model.ImageOperationCreator;
import model.ImageOperationCreator.OperationType;
import model.Layer;
import model.Layer24Bit;
import model.RgbPixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Layer24Bit class.
 */
public class Layer24BitTest {

  private Layer layer1;
  private Layer layer2;
  private Layer layer3;
  private final Image image1;
  private final Image image2;

  /**
   * Constructs a new Layer24BitTest initializing all example test data.
   */
  public Layer24BitTest() {
    layer1 = new Layer24Bit("layer1");
    layer2 = new Layer24Bit("layer2");
    layer3 = new Layer24Bit("layer3");
    image1 = ImageExamples.rainbow(1, 6);
    image2 = ImageExamples.checkerboard(2, 3, 1, 1,
        new RgbPixel(0, 0, 0),
        new RgbPixel(255, 255, 255));
    layer1.setImage(image1);
    layer2.setImage(image2);
  }

  @Before
  public void setup() {
    layer1 = new Layer24Bit("layer1");
    layer2 = new Layer24Bit("layer2");
    layer3 = new Layer24Bit("layer3");
    layer1.setImage(image1);
    layer2.setImage(image2);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullName() {
    new Layer24Bit(null);
  }

  @Test
  public void testConstructorValidName() {
    Layer first = new Layer24Bit("first");
    assertEquals("first", first.getName());
    assertTrue(first.isVisible());
    assertNull(first.getImage());
  }

  @Test
  public void testGetName() {
    assertEquals("layer1", layer1.getName());
    assertEquals("layer2", layer2.getName());
  }

  @Test
  public void testSetImage() {
    layer1.setImage(image1);
    assertEquals(image1, layer1.getImage());
    layer1.setImage(image2);
    assertEquals(image2, layer1.getImage());
  }

  @Test
  public void testGetImage() {
    assertEquals(image1, layer1.getImage());
    assertEquals(image2, layer2.getImage());
    assertNull(layer3.getImage());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyNullOperation() {
    layer1.apply(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyNullImage() {
    layer1.setImage(null);
    layer1.apply(ImageOperationCreator.create(OperationType.BLUR));
  }

  @Test
  public void testApplyBlurOperation() {
    assertEquals(image1, layer1.getImage());
    Image image1Blur = ImageOperationCreator.create(OperationType.BLUR).apply(image1);
    layer1.apply(ImageOperationCreator.create(OperationType.BLUR));
    assertEquals(image1Blur, layer1.getImage());
  }

  @Test
  public void testApplySepiaOperation() {
    assertEquals(image2, layer2.getImage());
    Image image2Blur = ImageOperationCreator.create(OperationType.SEPIA).apply(image2);
    layer2.apply(ImageOperationCreator.create(OperationType.SEPIA));
    assertEquals(image2Blur, layer2.getImage());
  }

  // Test the show and isVisible methods
  @Test
  public void testShowIsVisible() {
    assertTrue(layer1.isVisible());
    layer1.show(false);
    assertFalse(layer1.isVisible());
    layer1.show(true);
    assertTrue(layer1.isVisible());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNameNullName() {
    layer1.setName(null);
  }

  @Test
  public void testSetNameName() {
    layer1.setName("new name");
    assertEquals("new name", layer1.getName());
    layer1.setName("even newer name");
    assertEquals("even newer name", layer1.getName());
  }
}
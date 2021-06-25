import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import model.Image;
import model.ImageExamples;
import model.ImageOperationCreator;
import model.ImageOperationCreator.OperationType;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.RgbPixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests all the methods from ImageProcessingModelImpl class.
 */
public class ImageProcessingModelImplTest {
  private ImageProcessingModel model;
  private final Image image1;
  private final Image image2;
  private final Image image3;

  /**
   * Constructs a new ImageProcessingModelImpTest initializing all example test data.
   */
  public ImageProcessingModelImplTest() {
    model = new ImageProcessingModelImpl();
    image1 = ImageExamples.rainbow(2, 6);
    image2 = ImageExamples.rainbow(3, 6);
    image3 = ImageExamples.checkerboard(6, 2, 1, 1,
        new RgbPixel(0, 0, 0),
        new RgbPixel(255, 255, 255));
  }

  @Before
  public void setup() {
    model = new ImageProcessingModelImpl();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerNullName() {
    model.addLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerSameName() {
    model.addLayer("layer1");
    model.addLayer("layer1");
  }

  @Test
  public void testAddLayerNamed() {
    assertEquals(0, model.numLayers());

    model.addLayer("test");

    assertEquals(1, model.numLayers());
    assertEquals("test", model.getLayerNameAt(0));
    assertEquals("test", model.getCurrentName());
    assertTrue(model.isVisible("test"));
  }

  @Test
  public void testAddLayerUnnamed() {
    assertEquals(0, model.numLayers());

    model.addLayer("");
    model.addLayer("");
    model.addLayer("");

    assertEquals(3, model.numLayers());
    assertEquals("Layer1", model.getLayerNameAt(0));
    assertEquals("Layer2", model.getLayerNameAt(1));
    assertEquals("Layer3", model.getLayerNameAt(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentLayerNullName() {
    model.setCurrentLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentLayerInvalidName() {
    model.setCurrentLayer("invalid");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentLayerValidLayer() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    model.addLayer("layer3");
    model.setCurrentLayer("layer");
    assertEquals("layer3", model.getCurrentName());
    model.setCurrentLayer("layer2");
    assertEquals("layer2", model.getCurrentName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageNullName() {
    model.setLayerImage(null, image1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageNullImage() {
    model.setLayerImage("layer1", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageInvalidSize() {
    model.addLayer("first");
    model.addLayer("second");
    model.setLayerImage("first", image1);
    model.setLayerImage("second", image2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageInvalidName() {
    model.setLayerImage("invalid", image1);
  }

  @Test
  public void testSetLayerImageValid() {
    model.addLayer("first");
    model.addLayer("second");

    assertNull(model.getImageIn("first"));
    assertNull(model.getImageIn("second"));

    model.setLayerImage("first", image1);
    model.setLayerImage("second", image3);

    assertEquals(image1, model.getImageIn("first"));
    assertEquals(image3, model.getImageIn("second"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShowLayerNullName() {
    model.showLayer(null, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShowLayerInvalidName() {
    model.showLayer("no layer named this", true);
  }

  @Test
  public void testShowLayerNameTrue() {
    model.addLayer("first");
    model.addLayer("second");
    model.showLayer("first", true);
    assertTrue(model.isVisible("first"));
    model.showLayer("first", false);
    assertFalse(model.isVisible("first"));
    model.showLayer("first", true);
    assertTrue(model.isVisible("first"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyOperationNullName() {
    model.applyOperation(null, ImageOperationCreator.create(OperationType.BLUR));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyOperationNullOperation() {
    model.applyOperation("first", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyOperationInvalidName() {
    model.applyOperation("yay", ImageOperationCreator.create(OperationType.BLUR));
  }

  @Test
  public void testApplyBlurOperation() {
    model.addLayer("first");
    model.addLayer("second");
    model.setLayerImage("second", image1);

    assertEquals(image1, model.getImageIn("second"));

    model.applyOperation("second", ImageOperationCreator.create(OperationType.BLUR));

    Image blurredImage1 = ImageOperationCreator.create(OperationType.BLUR).apply(image1);
    assertEquals(blurredImage1, model.getImageIn("second"));
  }

  @Test
  public void testApplySepiaOperation() {
    model.addLayer("first");
    model.addLayer("second");
    model.setLayerImage("first", image2);

    assertEquals(image2, model.getImageIn("first"));

    model.applyOperation("first", ImageOperationCreator.create(OperationType.SEPIA));

    Image sepiaImage2 = ImageOperationCreator.create(OperationType.SEPIA).apply(image2);
    assertEquals(sepiaImage2, model.getImageIn("first"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerNullName() {
    model.removeLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerInvalidName() {
    model.removeLayer("hello");
  }

  @Test
  public void testRemoveLayerValid() {
    model.addLayer("first");
    model.addLayer("second");
    model.addLayer("third");
    model.setCurrentLayer("third");

    assertEquals(3, model.numLayers());
    assertEquals("third", model.getCurrentName());

    model.removeLayer("second");

    assertEquals(2, model.numLayers());
    assertEquals("first", model.getLayerNameAt(0));
    assertEquals("third", model.getLayerNameAt(1));
    assertEquals("third", model.getCurrentName());

    model.removeLayer("third");

    assertNull(model.getCurrentName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReorderLayerNullName() {
    model.reorderLayer(null, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReorderLayerLowIndex() {
    model.addLayer("first");
    model.addLayer("second");
    model.reorderLayer("first", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReorderLayerHighIndex() {
    model.addLayer("first");
    model.addLayer("second");
    model.reorderLayer("first", 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReorderLayerInvalidName() {
    model.addLayer("first");
    model.addLayer("second");
    model.reorderLayer("third", 1);
  }

  @Test
  public void testReorderLayerValid() {
    model.addLayer("first");
    model.addLayer("second");
    model.addLayer("third");

    assertEquals("first", model.getLayerNameAt(0));
    assertEquals("second", model.getLayerNameAt(1));
    assertEquals("third", model.getLayerNameAt(2));

    model.reorderLayer("third", 1);
    model.reorderLayer("first", 0);

    assertEquals("first", model.getLayerNameAt(0));
    assertEquals("third", model.getLayerNameAt(1));
    assertEquals("second", model.getLayerNameAt(2));
  }

  @Test
  public void testGetCurrentNameNull() {
    assertNull(model.getCurrentName());
  }

  @Test
  public void testGetCurrentNameNonNull() {
    model.addLayer("first");
    model.setCurrentLayer("first");
    assertEquals("first", model.getCurrentName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsVisibleNullName() {
    model.isVisible("null");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsVisibleInvalidName() {
    model.isVisible("world");
  }

  @Test
  public void testIsVisibleTrue() {
    model.addLayer("first");
    model.showLayer("first", true);
    assertTrue(model.isVisible("first"));
  }

  @Test
  public void testIsVisibleFalse() {
    model.addLayer("first");
    model.showLayer("first", false);
    assertFalse(model.isVisible("first"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetImageInNullName() {
    model.getImageIn(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetImageInInvalidName() {
    model.getImageIn("not a real name");
  }


  @Test
  public void testGetImageInValid() {
    model.addLayer("first");
    model.addLayer("second");
    model.setLayerImage("first", image1);
    model.setLayerImage("second", image3);

    assertEquals(image1, model.getImageIn("first"));
    assertEquals(image3, model.getImageIn("second"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLayerNameAtLowIndex() {
    model.addLayer("first");
    model.addLayer("second");
    model.getLayerNameAt(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLayerNameAtHighIndex() {
    model.addLayer("first");
    model.addLayer("second");
    model.getLayerNameAt(2);
  }

  @Test
  public void testGetLayerNameAtValid() {
    model.addLayer("first");
    model.addLayer("second");

    assertEquals("first", model.getLayerNameAt(0));
    assertEquals("second", model.getLayerNameAt(1));
  }

  @Test
  public void testNumLayers() {
    assertEquals(0, model.numLayers());
    model.addLayer("first");
    assertEquals(1, model.numLayers());
    model.addLayer("second");
    assertEquals(2, model.numLayers());
    model.addLayer("third");
    assertEquals(3, model.numLayers());
    model.removeLayer("first");
    assertEquals(2, model.numLayers());
  }
}

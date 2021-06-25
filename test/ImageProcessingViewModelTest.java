import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import model.Image;
import model.ImageExamples;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.ImageProcessingModelState;
import model.ImageProcessingViewModel;
import model.RgbPixel;
import org.junit.Test;

/**
 * Test for the ImageProcessingViewModel class.
 * Uses a mutable ImageProcessingModel delegate to setup example test data,
 * and runs test on a ImageProcessingViewModel.
 */
public class ImageProcessingViewModelTest {

  private ImageProcessingModel delegate;
  private ImageProcessingModelState model;
  private final Image image1;
  private final Image image3;

  /**
   * Create a new ImageProcessingViewModelTest initializing all example test data.
   */
  public ImageProcessingViewModelTest() {
    delegate = new ImageProcessingModelImpl();
    model = new ImageProcessingViewModel(delegate);

    image1 = ImageExamples.rainbow(2, 6);
    image3 = ImageExamples.checkerboard(6, 2, 1, 1,
        new RgbPixel(0, 0, 0),
        new RgbPixel(255, 255, 255));
  }


  @Test
  public void testGetCurrentNameNull() {
    delegate = new ImageProcessingModelImpl();
    model = new ImageProcessingViewModel(delegate);
    assertNull(model.getCurrentName());
  }

  @Test
  public void testGetCurrentNameNonNull() {
    delegate.addLayer("first");
    delegate.setCurrentLayer("first");
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
    delegate.addLayer("first");
    delegate.showLayer("first", true);
    assertTrue(model.isVisible("first"));
  }

  @Test
  public void testIsVisibleFalse() {
    delegate.addLayer("first");
    delegate.showLayer("first", false);
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
    delegate.addLayer("first");
    delegate.addLayer("second");
    delegate.setLayerImage("first", image1);
    delegate.setLayerImage("second", image3);

    assertEquals(image1, model.getImageIn("first"));
    assertEquals(image3, model.getImageIn("second"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLayerNameAtLowIndex() {
    delegate.addLayer("first");
    delegate.addLayer("second");
    model.getLayerNameAt(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLayerNameAtHighIndex() {
    delegate.addLayer("first");
    delegate.addLayer("second");
    model.getLayerNameAt(2);
  }

  @Test
  public void testGetLayerNameAtValid() {
    delegate.addLayer("first");
    delegate.addLayer("second");

    assertEquals("first", model.getLayerNameAt(0));
    assertEquals("second", model.getLayerNameAt(1));
  }

  @Test
  public void testNumLayers() {
    assertEquals(0, model.numLayers());
    delegate.addLayer("first");
    assertEquals(1, model.numLayers());
    delegate.addLayer("second");
    assertEquals(2, model.numLayers());
    delegate.addLayer("third");
    assertEquals(3, model.numLayers());
    delegate.removeLayer("first");
    assertEquals(2, model.numLayers());
  }
}
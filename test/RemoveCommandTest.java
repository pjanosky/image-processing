import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import controller.commands.RemoveCommand;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the RemoveCommand class.
 */
public class RemoveCommandTest {
  private ImageProcessingModel model;

  /**
   * Construct a new RemoveCommandTest initializing all example test data.
   */
  public RemoveCommandTest() {
    model = new ImageProcessingModelImpl();
  }

  @Before
  public void setup() {
    model = new ImageProcessingModelImpl();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullModel() {
    new RemoveCommand().go(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testGoNoCurrentLayerSet() {
    new RemoveCommand().go(model);
  }

  @Test
  public void testGoValid() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    model.addLayer("layer3");
    model.setCurrentLayer("layer2");

    assertEquals(3, model.numLayers());
    assertEquals("layer2", model.getCurrentName());

    new RemoveCommand().go(model);

    assertEquals(2, model.numLayers());
    assertNull(model.getCurrentName());
  }
}
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.commands.VisibilityCommand;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the VisibilityCommand class.
 */
public class VisibilityCommandTest {
  private ImageProcessingModel model;

  /**
   * Construct a new VisibilityCommandTest initializing all example test data.
   */
  public VisibilityCommandTest() {
    model = new ImageProcessingModelImpl();
  }

  @Before
  public void setup() {
    model = new ImageProcessingModelImpl();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullModel() {
    new VisibilityCommand(true).runCommand(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testGoNoCurrentLayerSet() {
    new VisibilityCommand(true).runCommand(model);
  }

  @Test
  public void testGoTrue() {
    model.addLayer("layer1");
    model.showLayer("layer1", false);
    assertFalse(model.isVisible("layer1"));
    new VisibilityCommand(true).runCommand(model);
    assertTrue(model.isVisible("layer1"));
  }

  @Test
  public void testGoFalse() {
    model.addLayer("layer1");
    model.showLayer("layer1", true);
    assertTrue(model.isVisible("layer1"));
    new VisibilityCommand(false).runCommand(model);
    assertFalse(model.isVisible("layer1"));
  }
}
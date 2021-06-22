import static org.junit.Assert.assertEquals;

import controller.commands.CurrentCommand;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the CurrentCommand class.
 */
public class CurrentCommandTest {

  private ImageProcessingModel model;

  /**
   * Construct a new CurrentCommandTest initializing all example test data.
   */
  public CurrentCommandTest() {
    model = new ImageProcessingModelImpl();
  }

  @Before
  public void setup() {
    model = new ImageProcessingModelImpl();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullName() {
    new CurrentCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullModel() {
    new CurrentCommand("layer1").runCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoInvalidName() {
    new CurrentCommand("layer1").runCommand(model);
  }

  @Test
  public void testGo() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    assertEquals("layer2", model.getCurrentName());

    new CurrentCommand("layer1").runCommand(model);

    assertEquals("layer1", model.getCurrentName());
  }
}
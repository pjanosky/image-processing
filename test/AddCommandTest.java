import static org.junit.Assert.assertEquals;

import controller.commands.AddCommand;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the AddCommand class.
 */
public class AddCommandTest {

  private ImageProcessingModel model;

  /**
   * Construct a new AddCommandTest initializing all example test data.
   */
  public AddCommandTest() {
    model = new ImageProcessingModelImpl();
  }

  @Before
  public void setup() {
    model = new ImageProcessingModelImpl();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullName() {
    new AddCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidNameWhitespace() {
    new AddCommand("hello world");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidNameSpecialChar() {
    new AddCommand("#$%^&*");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidNameEmpty() {
    new AddCommand("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoRepeatedName() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    model.addLayer("layer3");

    new AddCommand("layer2").runCommand(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullModel() {
    new AddCommand("layer1").runCommand(null);
  }

  @Test
  public void testGo() {
    assertEquals(0, model.numLayers());
    new AddCommand("layer1").runCommand(model);
    assertEquals(1, model.numLayers());
    assertEquals("layer1", model.getLayerNameAt(0));

    new AddCommand("layer2").runCommand(model);
    assertEquals(2, model.numLayers());
    assertEquals("layer2", model.getLayerNameAt(1));
  }
}
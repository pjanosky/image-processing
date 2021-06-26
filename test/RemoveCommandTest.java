import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import controller.commands.RemoveCommand;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Before;
import org.junit.Test;
import view.ImageProcessingTextView;
import view.ImageProcessingView;

/**
 * Tests the RemoveCommand class.
 */
public class RemoveCommandTest {
  private ImageProcessingModel model;
  private Appendable output;
  private ImageProcessingView view;

  /**
   * Construct a new RemoveCommandTest initializing all example test data.
   */
  public RemoveCommandTest() {
    model = new ImageProcessingModelImpl();
    output = new StringBuilder();
    view = new ImageProcessingTextView(output);
  }

  @Before
  public void setup() {
    model = new ImageProcessingModelImpl();
    output = new StringBuilder();
    view = new ImageProcessingTextView(output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullModel() {
    new RemoveCommand().runCommand(null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullView() {
    new RemoveCommand().runCommand( model, null);
  }

  @Test(expected = IllegalStateException.class)
  public void testGoNoCurrentLayerSet() {
    new RemoveCommand().runCommand(model, view);
  }

  @Test
  public void testGoValid() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    model.addLayer("layer3");
    model.setCurrentLayer("layer2");

    assertEquals(3, model.numLayers());
    assertEquals("layer2", model.getCurrentName());

    new RemoveCommand().runCommand(model, view);

    assertEquals(2, model.numLayers());
    assertNull(model.getCurrentName());

    String expected = "";
    assertEquals(expected, output.toString());
  }
}
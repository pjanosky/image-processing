import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.commands.VisibilityCommand;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Before;
import org.junit.Test;
import view.ImageProcessingTextView;
import view.ImageProcessingView;

/**
 * Test the VisibilityCommand class.
 */
public class VisibilityCommandTest {
  private ImageProcessingModel model;
  private Appendable output;
  private ImageProcessingView view;

  /**
   * Construct a new VisibilityCommandTest initializing all example test data.
   */
  public VisibilityCommandTest() {
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
    new VisibilityCommand(true).runCommand(null, view);
  }

  @Test(expected = IllegalStateException.class)
  public void testGoNoCurrentLayerSet() {
    new VisibilityCommand(true).runCommand(model, view);
  }

  @Test
  public void testGoTrue() {
    model.addLayer("layer1");
    model.showLayer("layer1", false);
    assertFalse(model.isVisible("layer1"));
    new VisibilityCommand(true).runCommand(model, view);
    assertTrue(model.isVisible("layer1"));
  }

  @Test
  public void testGoFalse() {
    model.addLayer("layer1");
    model.showLayer("layer1", true);
    assertTrue(model.isVisible("layer1"));
    new VisibilityCommand(false).runCommand(model, view);
    assertFalse(model.isVisible("layer1"));
  }
}
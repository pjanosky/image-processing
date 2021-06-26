import static org.junit.Assert.assertEquals;

import controller.commands.CurrentCommand;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Before;
import org.junit.Test;
import view.ImageProcessingTextView;
import view.ImageProcessingView;

/**
 * Tests the CurrentCommand class.
 */
public class CurrentCommandTest {

  private ImageProcessingModel model;
  private Appendable output;
  private ImageProcessingView view;

  /**
   * Construct a new CurrentCommandTest initializing all example test data.
   */
  public CurrentCommandTest() {
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
  public void testConstructorNullName() {
    new CurrentCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullModel() {
    new CurrentCommand("layer1").runCommand(null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullView() {
    new CurrentCommand("layer1").runCommand(model, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoInvalidName() {
    new CurrentCommand("layer1").runCommand(model, view);
  }

  @Test
  public void testGo() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    assertEquals("layer2", model.getCurrentName());

    new CurrentCommand("layer1").runCommand(model, view);

    assertEquals("layer1", model.getCurrentName());

    String expected = "";
    assertEquals(expected, output.toString());
  }
}
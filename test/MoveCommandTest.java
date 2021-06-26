import static org.junit.Assert.assertEquals;

import controller.commands.MoveCommand;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Before;
import org.junit.Test;
import view.ImageProcessingTextView;
import view.ImageProcessingView;

/**
 * Tess the MoveCommand class.
 */
public class MoveCommandTest {

  private ImageProcessingModel model;
  private Appendable output;
  private ImageProcessingView view;

  /**
   * Construct a new MoveCommandTest initializing all example test data.
   */
  public MoveCommandTest() {
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
    model.addLayer("layer1");
    new MoveCommand(0).runCommand(null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullView() {
    model.addLayer("layer1");
    new MoveCommand(0).runCommand(model, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoLowIndex() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    model.addLayer("layer3");

    new MoveCommand(0).runCommand(model, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoHighIndex() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    model.addLayer("layer3");

    new MoveCommand(4).runCommand(model, view);
  }

  @Test
  public void testGoValid() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    model.addLayer("layer3");
    assertEquals("layer1", model.getLayerNameAt(0));
    assertEquals("layer2", model.getLayerNameAt(1));
    assertEquals("layer3", model.getLayerNameAt(2));

    new MoveCommand(1).runCommand(model, view);
    assertEquals("layer3", model.getLayerNameAt(0));
    assertEquals("layer1", model.getLayerNameAt(1));
    assertEquals("layer2", model.getLayerNameAt(2));

    String expected = "";
    assertEquals(expected, output.toString());
  }
}
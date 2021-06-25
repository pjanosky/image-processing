import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Before;
import org.junit.Test;
import view.ImageProcessingTextView;
import view.ImageProcessingView;

/**
 * Tests the ImageProcessingTextView class.
 */
public class ImageProcessingTextViewTest {

  private ImageProcessingModel model;
  private Appendable output;
  private ImageProcessingView view;

  /**
   * Constructs a new ImageProcessingTextViewTest initializing all example test data.
   */
  public ImageProcessingTextViewTest() {
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

  // Tests constructing a new ImageProcessingTextView with a null Appendable object.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullAppendable() {
    new ImageProcessingTextView(null);
  }

  // Tests rendering a model with no layers.
  @Test
  public void testRenderLayersEmptyLayers() {
    view.renderLayers(model);
    String expected = "Layers:" + System.lineSeparator();
    assertEquals(expected, output.toString());
  }

  // Tests rendering a model with twp layers.
  @Test
  public void testRenderLayersTwoLayers() {
    model.addLayer("one");
    model.addLayer("two");
    model.setCurrentLayer("one");
    model.showLayer("one", false);
    view.renderLayers(model);
    String expected = "Layers:" + System.lineSeparator()
        + "1. one ( ) (current)" + System.lineSeparator()
        + "2. two (V)" + System.lineSeparator();
    assertEquals(expected, output.toString());
  }

  // Tests rendering a model with three layers.
  @Test
  public void testRenderLayersThreeLayers() {
    model.addLayer("first");
    model.addLayer("second");
    model.addLayer("third");
    model.showLayer("second", false);

    view.renderLayers(model);
    String expected = "Layers:" + System.lineSeparator()
        + "1. first (V)" + System.lineSeparator()
        + "2. second ( )" + System.lineSeparator()
        + "3. third (V) (current)" + System.lineSeparator();
    assertEquals(expected, output.toString());
  }

  // Test rendering layers where the Appendable object throws an exception.
  @Test(expected = IllegalStateException.class)
  public void testRenderLayersFailAppendable() {
    view = new ImageProcessingTextView(new FailAppendable());
    view.renderLayers(model);
  }

  // Test rendering a null message string.
  @Test
  public void testRenderMessageNullMessage() {
    view.renderMessage(null);
    assertEquals("", output.toString());
  }

  // Test rendering a empty message string.
  @Test
  public void testRenderMessageEmptyMessage() {
    view.renderMessage("");
    assertEquals(System.lineSeparator(), output.toString());
  }


  // Test rendering a multiple messages.
  @Test
  public void testRenderMessageMultipleMessages() {

    view.renderMessage("hello world. ");
    view.renderMessage("this is a message.\n ");
    view.renderMessage("yay!");
    String expected = "hello world. " + System.lineSeparator()
        + "this is a message.\n " + System.lineSeparator()
        + "yay!" + System.lineSeparator();
    assertEquals(expected, output.toString());
  }

  // Tests rendering a message when the Appendable object throws and exception.
  @Test(expected = IllegalStateException.class)
  public void testRenderMessageFailAppendable() {
    view = new ImageProcessingTextView(new FailAppendable());
    view.renderMessage("crash and burn");
  }
}
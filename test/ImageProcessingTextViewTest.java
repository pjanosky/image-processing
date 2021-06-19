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
    view = new ImageProcessingTextView(model, output);
  }

  @Before
  public void setup() {
    model = new ImageProcessingModelImpl();
    output = new StringBuilder();
    view = new ImageProcessingTextView(model, output);
  }

  // Tests constructing a new ImageProcessingTextView with a null model object.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullModel() {
    new ImageProcessingTextView(null, new StringBuilder());
  }

  // Tests constructing a new ImageProcessingTextView with a null Appendable object.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullAppendable() {
    new ImageProcessingTextView(model, null);
  }

  // Tests rendering a model with no layers.
  @Test
  public void testRenderLayersEmptyLayers() {
    try {
      view.renderLayers();
    } catch (IOException e) {
      fail(e.getMessage());
    }
    String expected = "Layers:";
    assertEquals(expected, output.toString());
  }

  // Tests rendering a model with twp layers.
  @Test
  public void testRenderLayersTwoLayers() {
    model.addLayer("one");
    model.addLayer("two");
    model.setCurrentLayer("one");
    model.showLayer("one", false);
    try {
      view.renderLayers();
    } catch (IOException e) {
      fail(e.getMessage());
    }
    String expected = "Layers:" + System.lineSeparator()
        + "1. one ( ) (current)" + System.lineSeparator()
        + "2. two (V)";
    assertEquals(expected, output.toString());
  }

  // Tests rendering a model with three layers.
  @Test
  public void testRenderLayersThreeLayers() {
    model.addLayer("first");
    model.addLayer("second");
    model.addLayer("third");
    model.showLayer("second", false);
    try {
      view.renderLayers();
    } catch (IOException e) {
      fail(e.getMessage());
    }
    String expected = "Layers:" + System.lineSeparator()
        + "1. first (V)" + System.lineSeparator()
        + "2. second ( )" + System.lineSeparator()
        + "3. third (V) (current)";
    assertEquals(expected, output.toString());
  }

  // Test rendering layers where the Appendable object throws an exception.
  @Test(expected = IOException.class)
  public void testRenderLayersFailAppendable() throws IOException {
    view = new ImageProcessingTextView(model, new FailAppendable());
    view.renderLayers();
  }

  // Test rendering a null message string.
  @Test
  public void testRenderMessageNullMessage() {
    try {
      view.renderMessage(null);
    } catch (IOException e) {
      fail(e.getMessage());
    }
    assertEquals("", output.toString());
  }

  // Test rendering a empty message string.
  @Test
  public void testRenderMessageEmptyMessage() {
    try {
      view.renderMessage("");
    } catch (IOException e) {
      fail(e.getMessage());
    }
    assertEquals("", output.toString());
  }


  // Test rendering a multiple messages.
  @Test
  public void testRenderMessageMultipleMessages() {
    try {
      view.renderMessage("hello world. ");
      view.renderMessage("this is a message.\n ");
      view.renderMessage("yay!");
    } catch (IOException e) {
      fail(e.getMessage());
    }
    assertEquals("hello world. this is a message.\n yay!", output.toString());
  }

  // Tests rendering a message when the Appendable object throws and exception.
  @Test(expected = IOException.class)
  public void testRenderMessageFailAppendable() throws IOException {
    view = new ImageProcessingTextView(model, new FailAppendable());
    view.renderMessage("crash and burn");
  }
}
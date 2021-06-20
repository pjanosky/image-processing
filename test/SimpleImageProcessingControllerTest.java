import static org.junit.Assert.*;

import controller.ImageProcessingController;
import controller.SimpleImageProcessingController;
import java.io.InputStreamReader;
import java.io.StringReader;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods and constructors of the controller.
 */
public class SimpleImageProcessingControllerTest {

  private ImageProcessingModel model;

  @Before
  public void setup() {
    model = new ImageProcessingModelImpl();
  }


  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullModel() {
    new SimpleImageProcessingController(null,
        new InputStreamReader(System.in), new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullReadable() {
    new SimpleImageProcessingController(new ImageProcessingModelImpl(),
        null, new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullAppendable() {
    new SimpleImageProcessingController(new ImageProcessingModelImpl(),
        new InputStreamReader(System.in), null);
  }

  @Test
  public void testRunAdd() {
    String output = runCommands(
        "add layer1",
        "q");

    System.out.println(output);
    assertEquals(1, model.numLayers());
  }


  private String runCommands(String... commands) {
    StringBuilder inputText = new StringBuilder();
    for (String command : commands) {
      inputText.append(command).append(System.lineSeparator());
    }

    Readable input = new StringReader(inputText.toString());
    Appendable output = new StringBuilder();
    ImageProcessingController controller =
        new SimpleImageProcessingController(model, input, output);

    controller.run();
    return output.toString();
  }
}
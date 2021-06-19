import static org.junit.Assert.*;

import controller.ImageProcessingController;
import controller.SimpleImageProcessingController;
import java.io.InputStreamReader;
import java.io.StringReader;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Test;

/**
 * Tests the methods and constructors of the controller.
 */
public class SimpleImageProcessingControllerTest {

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
    ImageProcessingModel model = new ImageProcessingModelImpl();
    ImageProcessingController controller = new SimpleImageProcessingController(
        model, new StringReader("add layer1"),
        new StringBuilder());

    assertEquals(1, model.numLayers());

  }
}
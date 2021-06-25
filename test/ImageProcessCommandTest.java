import static org.junit.Assert.assertEquals;

import controller.commands.ImageProcessCommand;
import model.Image;
import model.ImageExamples;
import model.ImageOperationCreator;
import model.ImageOperationCreator.OperationType;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Before;
import org.junit.Test;
import view.ImageProcessingTextView;
import view.ImageProcessingView;

/**
 * Test the ImageProcessCommand class.
 */
public class ImageProcessCommandTest {

  private ImageProcessingModel model;
  private Appendable output;
  private ImageProcessingView view;

  /**
   * Construct a new ImageProcessCommandTest initializing all example test data.
   */
  public ImageProcessCommandTest() {
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
  public void testConstructorNullOperation() {
    new ImageProcessCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullModel() {
    new ImageProcessCommand(ImageOperationCreator.create(OperationType.BLUR)).runCommand(null, view);
  }

  @Test(expected = IllegalStateException.class)
  public void testGoNoCurrentLayer() {
    new ImageProcessCommand(ImageOperationCreator.create(OperationType.BLUR)).runCommand(model, view);
  }

  @Test
  public void tstGoBlur() {
    Image image = ImageExamples.rainbow(10, 2);
    Image edited = ImageOperationCreator.create(OperationType.BLUR).apply(image);
    model.addLayer("layer1");
    model.setLayerImage("layer1", image);
    assertEquals(image, model.getImageIn("layer1"));
    new ImageProcessCommand(ImageOperationCreator.create(OperationType.BLUR)).runCommand(model, view);

    assertEquals(edited, model.getImageIn("layer1"));
  }

  @Test
  public void tstGoSepia() {
    Image image = ImageExamples.rainbow(10, 2);
    Image edited = ImageOperationCreator.create(OperationType.SEPIA).apply(image);
    model.addLayer("layer1");
    model.setLayerImage("layer1", image);
    assertEquals(image, model.getImageIn("layer1"));
    new ImageProcessCommand(ImageOperationCreator.create(OperationType.SEPIA)).runCommand(model, view);

    assertEquals(edited, model.getImageIn("layer1"));
  }
}
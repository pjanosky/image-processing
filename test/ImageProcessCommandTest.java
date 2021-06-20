import static org.junit.Assert.assertEquals;

import controller.commands.ImageProcessCommand;
import model.Image;
import model.ImageOperationCreator;
import model.ImageOperationCreator.OperationType;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the ImageProcessCommand class.
 */
public class ImageProcessCommandTest {

  private ImageProcessingModel model;

  /**
   * Construct a new ImageProcessCommandTest initializing all example test data.
   */
  public ImageProcessCommandTest() {
    model = new ImageProcessingModelImpl();
  }

  @Before
  public void setup() {
    model = new ImageProcessingModelImpl();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullOperation() {
    new ImageProcessCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullModel() {
    new ImageProcessCommand(ImageOperationCreator.create(OperationType.BLUR)).go(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testGoNoCurrentLayer() {
    new ImageProcessCommand(ImageOperationCreator.create(OperationType.BLUR)).go(model);
  }

  @Test
  public void tstGoBlur() {
    Image image = ImageExamples.rainbow(10, 2);
    Image edited = ImageOperationCreator.create(OperationType.BLUR).apply(image);
    model.addLayer("layer1");
    model.setLayerImage("layer1", image);
    assertEquals(image, model.getImageIn("layer1"));
    new ImageProcessCommand(ImageOperationCreator.create(OperationType.BLUR)).go(model);

    assertEquals(edited, model.getImageIn("layer1"));
  }

  @Test
  public void tstGoSepia() {
    Image image = ImageExamples.rainbow(10, 2);
    Image edited = ImageOperationCreator.create(OperationType.SEPIA).apply(image);
    model.addLayer("layer1");
    model.setLayerImage("layer1", image);
    assertEquals(image, model.getImageIn("layer1"));
    new ImageProcessCommand(ImageOperationCreator.create(OperationType.SEPIA)).go(model);

    assertEquals(edited, model.getImageIn("layer1"));
  }
}
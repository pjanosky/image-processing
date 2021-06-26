import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import controller.commands.ImageProcessAllCommand;
import model.Image;
import model.ImageExamples;
import model.ImageOperation;
import model.ImageOperationCreator;
import model.ImageOperationCreator.OperationType;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.RgbPixel;
import org.junit.Before;
import org.junit.Test;
import view.ImageProcessingTextView;
import view.ImageProcessingView;

/**
 * Tests the ImageProcessAllCommand class.
 */
public class ImageProcessAllCommandTest {
  private ImageProcessingModel model;
  private Appendable output;
  private ImageProcessingView view;

  /**
   * Construct a new ImageProcessAllCommandTest initializing all example test data.
   */
  public ImageProcessAllCommandTest() {
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
    new ImageProcessAllCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullModel() {
    ImageOperation operation = ImageOperationCreator.create(OperationType.BLUR);
    new ImageProcessAllCommand(operation).runCommand(null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullView() {
    ImageOperation operation = ImageOperationCreator.create(OperationType.BLUR);
    new ImageProcessAllCommand(operation).runCommand(model, null);
  }

  @Test
  public void testGoValidNoLayers() {
    assertEquals(0, model.numLayers());

    ImageOperation operation = ImageOperationCreator.create(OperationType.BLUR);
    new ImageProcessAllCommand(operation).runCommand(model, view);

    assertEquals(0, model.numLayers());
    String expected = "Applied operation to all layers." + System.lineSeparator();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testGoValidNoImagesInLayers() {
    assertEquals(0, model.numLayers());
    model.addLayer("layer1");
    model.addLayer("layer2");
    assertEquals(2, model.numLayers());
    assertNull(model.getImageIn("layer1"));
    assertNull(model.getImageIn("layer2"));

    ImageOperation operation = ImageOperationCreator.create(OperationType.BLUR);
    new ImageProcessAllCommand(operation).runCommand(model, view);

    assertEquals(2, model.numLayers());
    assertNull(model.getImageIn("layer1"));
    String expected = "Applied operation to all layers." + System.lineSeparator();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testGoValidOneImages() {
    Image image1 = ImageExamples.checkerboard(6, 2, 1, 2,
        new RgbPixel(0, 34, 244),
        new RgbPixel(190, 23, 89));
    Image image1Blur = ImageOperationCreator.create(OperationType.BLUR).apply(image1);
    assertEquals(0, model.numLayers());
    model.addLayer("layer1");

    model.setLayerImage("layer1", image1);

    assertEquals(image1, model.getImageIn("layer1"));

    ImageOperation operation = ImageOperationCreator.create(OperationType.BLUR);
    new ImageProcessAllCommand(operation).runCommand(model, view);

    assertEquals(image1Blur, model.getImageIn("layer1"));
    String expected = "Applied operation to all layers." + System.lineSeparator();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testGoValidThreeImages() {
    Image image1 = ImageExamples.checkerboard(6, 2, 1, 2,
        new RgbPixel(0, 34, 244),
        new RgbPixel(190, 23, 89));
    Image image2 = ImageExamples.rainbow(2, 12);
    Image image1Blur = ImageOperationCreator.create(OperationType.BLUR).apply(image1);
    Image image2Blur = ImageOperationCreator.create(OperationType.BLUR).apply(image2);
    assertEquals(0, model.numLayers());
    model.addLayer("layer1");
    model.addLayer("layer2");
    model.addLayer("layer3");
    model.setLayerImage("layer1", image1);
    model.setLayerImage("layer2", image2);
    model.setLayerImage("layer3", image2);

    assertEquals(image1, model.getImageIn("layer1"));
    assertEquals(image2, model.getImageIn("layer2"));
    assertEquals(image2, model.getImageIn("layer3"));

    ImageOperation operation = ImageOperationCreator.create(OperationType.BLUR);
    new ImageProcessAllCommand(operation).runCommand(model, view);

    assertEquals(image1Blur, model.getImageIn("layer1"));
    assertEquals(image2Blur, model.getImageIn("layer2"));
    assertEquals(image2Blur, model.getImageIn("layer3"));
    String expected = "Applied operation to all layers." + System.lineSeparator();
    assertEquals(expected, output.toString());
  }
}
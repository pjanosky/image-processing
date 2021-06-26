import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.CommandListener;
import controller.GuiController;
import controller.PngImportExporter;
import controller.PpmImportExporter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import model.DownscaleOperation;
import model.Image;
import model.ImageExamples;
import model.ImageOperationCreator;
import model.ImageOperationCreator.OperationType;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.ImageProcessingModelState;
import model.MosaicOperation;
import model.RgbPixel;
import org.junit.Before;
import org.junit.Test;
import view.GuiImageProcessingView;

/**
 * Test the GuiController class.
 */
public class GuiControllerTest {

  private ImageProcessingModel model;
  private Appendable output;
  private MockGuiView view;
  private GuiController controller;
  private final Image image1;
  private final Image image2;

  /**
   * Constructs a new GuiControllerTest initializing all example test data.
   */
  public GuiControllerTest() {
    model = new ImageProcessingModelImpl();
    output = new StringBuilder();
    view = new MockGuiView(output);
    controller = new GuiController(model, view);
    image1 = ImageExamples.rainbow(10, 12);
    image2 = ImageExamples.checkerboard(12, 10, 1, 1,
        new RgbPixel(0, 0, 0),
        new RgbPixel(255, 255, 255));
  }

  @Before
  public void setup() {
    model = new ImageProcessingModelImpl();
    output = new StringBuilder();
    view = new MockGuiView(output);
    controller = new GuiController(model, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullModel() {
    new GuiController(null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullView() {
    new GuiController(model, null);
  }

  // Tests that the controller makes the view visible.
  @Test
  public void testSetVisible() {
    assertFalse(view.isVisible());
    controller.run();
    assertTrue(view.isVisible());
  }

  // Tests that the controller properly sets the command listener in the view to itself.
  @Test
  public void testCommandListenerSet() {
    assertNull(view.getListener());
    controller.run();
    assertEquals(controller, view.getListener());
  }

  @Test
  public void testRunSave() {
    clean();

    model.addLayer("layer1");
    model.addLayer("layer2");
    model.setLayerImage("layer2", image2);
    model.showLayer("layer2", false);
    model.addLayer("layer3");
    model.setLayerImage("layer3", image1);

    String expected = concatenateLines(
        "message: Saved layer \"layer3\" to test/data/image.ppm.",
        "rendering layers",
        "message: Saved layer \"layer3\" to test/data/image.png.",
        "rendering layers"
    );

    controller.save(new File("test/data/image.ppm"));
    controller.save(new File("test/data/image.png"));
    try {
      assertEquals(image1,
          new PpmImportExporter().parseImage(new FileInputStream("test/data/image.ppm")));
      assertEquals(image1,
          new PngImportExporter().parseImage(new FileInputStream("test/data/image.png")));
    } catch (IOException e) {
      fail("Failed to parse saved images. " + e.getMessage());
    }
    assertEquals(expected, output.toString());

    clean();
  }

  @Test
  public void testSaveNullFile() {
    controller.save(null);
    assertEquals("", output.toString());
  }

  @Test
  public void testRunLoad() {
    clean();

    try {
      new PpmImportExporter().saveImage(
          new FileOutputStream("test/data/image.ppm"), image1);
      new PngImportExporter().saveImage(
          new FileOutputStream("test/data/image.png"), image1);
    } catch (IOException e) {
      fail("Failed to save test images. " + e.getMessage());
    }

    model.addLayer("layer1");
    model.addLayer("layer2");

    model.setCurrentLayer("layer1");
    controller.load(new File("test/data/image.ppm"));
    model.setCurrentLayer("layer2");
    controller.load(new File("test/data/image.png"));

    String expected = concatenateLines(
        "message: Loaded test/data/image.ppm into layer \"layer1\".",
        "rendering layers",
        "message: Loaded test/data/image.png into layer \"layer2\".",
        "rendering layers"
    );

    assertEquals(image1, model.getImageIn("layer1"));
    assertEquals(image1, model.getImageIn("layer2"));
    assertEquals(expected, output.toString());

    clean();
  }

  @Test
  public void testLoadNullFile() {
    controller.load(null);
    assertEquals("", output.toString());
  }

  @Test
  public void testRunShow() {
    model.addLayer("layer1");
    model.showLayer("layer1", false);

    assertFalse(model.isVisible("layer1"));

    controller.visibility(true);
    String expected = concatenateLines(
        "rendering layers"
    );

    assertTrue(model.isVisible("layer1"));
    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunHide() {
    model.addLayer("layer1");

    assertTrue(model.isVisible("layer1"));

    controller.visibility(false);

    String expected = concatenateLines(
        "rendering layers"
    );

    assertFalse(model.isVisible("layer1"));
    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunSaveall() {
    clean();

    model.addLayer("layer1");
    model.setLayerImage("layer1", image1);
    model.addLayer("layer2");
    model.showLayer("layer2", false);
    model.setLayerImage("layer2", image2);
    model.addLayer("layer3");

    controller.saveLayers(new File("test/data/layers"));
    String expected = concatenateLines(
        "message: Saved all layers to test/data/layers.",
        "rendering layers"
    );
    String textFile = "layer1 true test/data/layers/layer1.png\n"
        + "layer2 false test/data/layers/layer2.png\n"
        + "layer3 true\n";
    try {
      assertEquals(image1, new PngImportExporter()
          .parseImage(new FileInputStream("test/data/layers/layer1.png")));
      assertEquals(image2, new PngImportExporter()
          .parseImage(new FileInputStream("test/data/layers/layer2.png")));
      assertEquals(textFile,
          new String(new FileInputStream("test/data/layers/info.txt").readAllBytes()));
    } catch (IOException e) {
      fail("Failed to parse saved images. " + e.getMessage());
    }
    assertEquals(expected, output.toString());

    clean();
  }

  @Test
  public void testSaveAllNullFile() {
    controller.saveLayers(null);
    assertEquals("", output.toString());
  }

  @Test
  public void testRunLoadall() {
    clean();

    String textFile = "layer1 true test/data/layers/layer1.png\n"
        + "layer2 false test/data/layers/layer2.png\n"
        + "layer3 true\n";

    model.addLayer("other");
    try {
      new File("test/data/layers").mkdir();
      new PngImportExporter().saveImage(
          new FileOutputStream("test/data/layers/layer1.png"), image1);
      new PngImportExporter().saveImage(
          new FileOutputStream("test/data/layers/layer2.png"), image2);
      new FileOutputStream("test/data/layers/info.txt").write(textFile.getBytes());
    } catch (IOException e) {
      fail("Failed to save test images. " + e.getMessage());
    }

    controller.loadLayers(new File("test/data/layers"));
    String expected = concatenateLines(
        "message: Loaded layers from test/data/layers.",
        "rendering layers"
    );

    assertEquals(3, model.numLayers());

    assertEquals("layer1", model.getLayerNameAt(0));
    assertEquals(image1, model.getImageIn("layer1"));
    assertTrue(model.isVisible("layer1"));

    assertEquals("layer2", model.getLayerNameAt(1));
    assertEquals(image2, model.getImageIn("layer2"));
    assertFalse(model.isVisible("layer2"));

    assertEquals("layer3", model.getLayerNameAt(2));
    assertNull(model.getImageIn("layer3"));
    assertTrue(model.isVisible("layer3"));

    assertEquals(expected, output.toString());

    clean();
  }

  @Test
  public void testLoadAllNullFile() {
    controller.loadLayers(null);
    assertEquals("", output.toString());
  }

  @Test
  public void testRunAdd() {
    assertEquals(0, model.numLayers());

    controller.add("layer1");
    controller.add("layer2");
    String expected = concatenateLines(
        "rendering layers",
        "rendering layers"
    );

    assertEquals(2, model.numLayers());
    assertEquals("layer1", model.getLayerNameAt(0));
    assertEquals("layer2", model.getLayerNameAt(1));
    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunRemove() {
    model.addLayer("layer1");
    model.addLayer("layer2");

    assertEquals(2, model.numLayers());
    assertEquals("layer1", model.getLayerNameAt(0));
    assertEquals("layer2", model.getLayerNameAt(1));

    controller.remove();
    String expected = concatenateLines(
        "rendering layers"
    );

    assertEquals(1, model.numLayers());
    assertEquals("layer1", model.getLayerNameAt(0));
    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunSharpen() {
    model.addLayer("layer1");
    model.setLayerImage("layer1", image1);

    assertEquals(image1, model.getImageIn("layer1"));

    controller.imageProcess(ImageOperationCreator.create(OperationType.SHARPEN));
    String expected = concatenateLines(
        "message: Applied operation to layer \"layer1\".",
        "rendering layers"
    );

    Image image1Sharpen = ImageOperationCreator.create(OperationType.SHARPEN).apply(image1);
    assertEquals(image1Sharpen, model.getImageIn("layer1"));
    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunBlur() {
    model.addLayer("layer1");
    model.setLayerImage("layer1", image1);

    assertEquals(image1, model.getImageIn("layer1"));

    controller.imageProcess(ImageOperationCreator.create(OperationType.BLUR));
    String expected = concatenateLines(
        "message: Applied operation to layer \"layer1\".",
        "rendering layers"
    );

    Image image1Blur = ImageOperationCreator.create(OperationType.BLUR).apply(image1);
    assertEquals(image1Blur, model.getImageIn("layer1"));
    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunGreyscale() {
    model.addLayer("layer1");
    model.setLayerImage("layer1", image1);

    assertEquals(image1, model.getImageIn("layer1"));

    controller.imageProcess(ImageOperationCreator.create(OperationType.GREYSCALE));
    String expected = concatenateLines(
        "message: Applied operation to layer \"layer1\".",
        "rendering layers"
    );

    Image image1Greyscale = ImageOperationCreator.create(OperationType.GREYSCALE).apply(image1);
    assertEquals(image1Greyscale, model.getImageIn("layer1"));
    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunSepia() {
    model.addLayer("layer1");
    model.setLayerImage("layer1", image1);

    assertEquals(image1, model.getImageIn("layer1"));

    controller.imageProcess(ImageOperationCreator.create(OperationType.SEPIA));
    String expected = concatenateLines(
        "message: Applied operation to layer \"layer1\".",
        "rendering layers"
    );

    Image image1Sepia = ImageOperationCreator.create(OperationType.SEPIA).apply(image1);
    assertEquals(image1Sepia, model.getImageIn("layer1"));
    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunDownscale() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    model.setLayerImage("layer1", image1);
    model.setLayerImage("layer2", image2);

    assertEquals(image1, model.getImageIn("layer1"));
    assertEquals(image2, model.getImageIn("layer2"));

    controller.imageProcessAll(new DownscaleOperation(0.5, 0.75));
    String expected = concatenateLines(
        "message: Applied operation to all layers.",
        "rendering layers"
    );

    Image image1Down = new DownscaleOperation(0.5, 0.75).apply(image1);
    Image image2Down = new DownscaleOperation(0.5, 0.75).apply(image2);
    assertEquals(image1Down, model.getImageIn("layer1"));
    assertEquals(image2Down, model.getImageIn("layer2"));
    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunMosaic() {
    model.addLayer("layer1");
    model.setLayerImage("layer1", image1);

    assertEquals(image1, model.getImageIn("layer1"));

    controller.imageProcess(new MosaicOperation(50));
    String expected = concatenateLines(
        "message: Applied operation to layer \"layer1\".",
        "rendering layers"
    );

    Image image1Mosaic = new MosaicOperation(50).apply(image1);
    assertNotEquals(image1Mosaic, model.getImageIn("layer1"));
    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunScript() {
    assertEquals(0, model.numLayers());

    controller.script("test/data/scripts/script1.txt");
    String expected = concatenateLines(
        "rendering layers"
    );

    assertEquals(1, model.numLayers());
    assertEquals("layer1", model.getLayerNameAt(0));
    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunCurrent() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    assertEquals("layer2", model.getCurrentName());

    controller.current("layer1");
    String expected = concatenateLines(
        "rendering layers"
    );

    assertEquals("layer1", model.getCurrentName());
    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunMove() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    assertEquals("layer1", model.getLayerNameAt(0));
    assertEquals("layer2", model.getLayerNameAt(1));

    controller.move("1");
    String expected = concatenateLines(
        "rendering layers"
    );

    assertEquals("layer2", model.getLayerNameAt(0));
    assertEquals("layer1", model.getLayerNameAt(1));
    assertEquals(expected, output.toString());
  }

  @Test
  public void testMoveInvalidIndexNotInteger() {
    controller.move("not a number");

    String expected = concatenateLines(
        "error: Invalid index."
    );

    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunSet() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    assertEquals("layer1", model.getLayerNameAt(0));
    assertEquals("layer2", model.getLayerNameAt(1));

    controller.setImage("rainbow", "1 6");
    String expected = concatenateLines(
        "message: Successfully set image in layer \"layer2\".",
        "rendering layers"
    );

    assertEquals(ImageExamples.rainbow(1, 6),
        model.getImageIn("layer2"));
    assertEquals(expected, output.toString());
  }

  @Test
  public void testSetImageNullArguments() {
    controller.setImage("rainbow", null);
    String expected = concatenateLines(
        "error: Invalid arguments for rainbow."
    );

    assertEquals(expected, output.toString());
  }

  @Test
  public void testRunMultipleCommands() {
    assertEquals(0, model.numLayers());

    controller.add("layer1");
    controller.add("layer2");
    controller.setImage("rainbow", "10 12");
    controller.imageProcess(ImageOperationCreator.create(OperationType.SEPIA));
    controller.current("layer1");
    controller.remove();
    controller.current("layer2");
    controller.visibility(false);
    String expected = concatenateLines(
        "rendering layers",
        "rendering layers",
        "message: Successfully set image in layer \"layer2\".",
        "rendering layers",
        "message: Applied operation to layer \"layer2\".",
        "rendering layers",
        "rendering layers",
        "rendering layers",
        "rendering layers",
        "rendering layers"
    );

    Image edited = ImageOperationCreator.create(OperationType.SEPIA).apply(image1);

    assertEquals(1, model.numLayers());
    assertEquals("layer2", model.getLayerNameAt(0));
    assertEquals(edited, model.getImageIn("layer2"));
    assertFalse(model.isVisible("layer2"));
    assertEquals("layer2", model.getCurrentName());

    assertEquals(expected, output.toString());
  }

  /**
   * Tests inputting invalid arguments for a valid the controller that are processed in the Command
   * objects. Just test the controller's ability to render error messages produced by a
   * ControllerCommand implementation. The actual error behavior of specific invalid arguments is
   * tested for each ControllerCommand implementation individually.
   */
  @Test
  public void testInvalidCommandArgumentsCommandObjects() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    model.setLayerImage("layer1", image1);

    controller.current("name");
    controller.imageProcess(ImageOperationCreator.create(OperationType.SHARPEN));
    controller.move("-3");
    String expected = concatenateLines(
        "error: No layer named name.",
        "error: The image right now is null!",
        "error: Invalid index."
    );

    assertEquals(expected, output.toString());
  }

  /**
   * Concatenates a series of lines into one String with each line followed by a new line
   * character.
   *
   * @param commands the lines to concatenate.
   * @return the concatenated lines as a String.
   */
  private String concatenateLines(String... commands) {
    StringBuilder commandText = new StringBuilder();
    for (String command : commands) {
      commandText.append(command).append(System.lineSeparator());
    }
    return commandText.toString();
  }

  /**
   * Deletes files from test/data/layers and layers directory to make sure tests run independently.
   */
  private void clean() {
    File directory = new File("test/data/layers");
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        file.delete();
      }
    }
    directory.delete();

    files = new File("test/data").listFiles();
    if (files != null) {
      for (File file : files) {
        file.delete();
      }
    }
  }

  /**
   * A mock GuiImageProcessingView to test whether the controller displays the right messages,
   * error, and layers, and sets the right command listener and visibility.
   */
  private static class MockGuiView implements GuiImageProcessingView {

    private final Appendable output;
    private boolean visible;
    private CommandListener listener;

    /**
     * Construct a new MockGuiView to render output to the given Appendable object.
     *
     * @param output the Appendable object to render the view's output including layers, messages,
     *               and erros to.
     */
    public MockGuiView(Appendable output) {
      this.output = output;
      this.visible = false;
    }

    @Override
    public void setCommandListener(CommandListener listener) {
      this.listener = listener;
    }

    @Override
    public void setVisible(boolean visible) {
      this.visible = visible;
    }

    @Override
    public void renderLayers(ImageProcessingModelState model) throws IllegalStateException {
      try {
        output.append("rendering layers").append(System.lineSeparator());
      } catch (IOException exception) {
        throw new IllegalStateException("Failed to write to output.");
      }
    }

    @Override
    public void renderMessage(String message) throws IllegalStateException {
      try {
        output.append("message: ").append(message).append(System.lineSeparator());
      } catch (IOException exception) {
        throw new IllegalStateException("Failed to write to output.");
      }
    }

    @Override
    public void renderError(String message) throws IllegalStateException {
      try {
        output.append("error: ").append(message).append(System.lineSeparator());
      } catch (IOException exception) {
        throw new IllegalStateException("Failed to write to output.");
      }
    }

    /**
     * Determines if this view has been made visible by the controller. Used only for testing.
     *
     * @return whether the view has been made visible.
     */
    public boolean isVisible() {
      return this.visible;
    }

    /**
     * Returns the command listener that has been set by the controller. Used only for testing.
     *
     * @return the set command listener.
     */
    public CommandListener getListener() {
      return this.listener;
    }
  }
}
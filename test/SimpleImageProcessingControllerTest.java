import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.ImageProcessingController;
import controller.PngImportExporter;
import controller.PpmImportExporter;
import controller.SimpleImageProcessingController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import model.Image;
import model.ImageOperationCreator;
import model.ImageOperationCreator.OperationType;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.RgbPixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods and constructors of the controller.
 */
public class SimpleImageProcessingControllerTest {

  private ImageProcessingModel model;
  private final Image image1;
  private final Image image2;

  public SimpleImageProcessingControllerTest() {
    model = new ImageProcessingModelImpl();
    image1 = ImageExamples.rainbow(10, 2);
    image2 = ImageExamples.checkerboard(12, 10, 1, 1,
        new RgbPixel(0, 0, 0),
        new RgbPixel(255, 255, 255));
  }

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

  @Test(expected = IllegalStateException.class)
  public void testRunFailReadable() {
    Readable input = new FailReadable();
    Appendable output = new StringBuilder();
    new SimpleImageProcessingController(model, input, output).run();
  }

  @Test(expected = IllegalStateException.class)
  public void testRunFailAppendable() {
    Readable input = new StringReader("add layer1 q");
    Appendable output = new FailAppendable();
    new SimpleImageProcessingController(model, input, output).run();
  }

  @Test
  public void testRunSave() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    model.setLayerImage("layer2", image2);
    model.showLayer("layer2", false);
    model.addLayer("layer3");
    model.setLayerImage("layer3", image1);

    String output = runCommands(
        "save test/data/image.ppm ppm",
        "save test/data/image.png png",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 (V)",
        "2. layer2 ( )",
        "3. layer3 (V) (current)",
        "Layers:",
        "1. layer1 (V)",
        "2. layer2 ( )",
        "3. layer3 (V) (current)",
        "Quitting."
    );
    try {
      assertEquals(image1,
          new PpmImportExporter().parseImage(new FileInputStream("test/data/image.ppm")));
      assertEquals(image1,
          new PngImportExporter().parseImage(new FileInputStream("test/data/image.png")));
    } catch (IOException e) {
      fail("Failed to parse saved images. " + e.getMessage());
    }
    assertEquals(expected, output);
  }

  @Test
  public void testRunLoad() {
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

    String output = runCommands(
        "current layer1",
        "load test/data/image.ppm ppm",
        "current layer2",
        "load test/data/image.png png",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 (V) (current)",
        "2. layer2 (V)",
        "Layers:",
        "1. layer1 (V) (current)",
        "2. layer2 (V)",
        "Layers:",
        "1. layer1 (V)",
        "2. layer2 (V) (current)",
        "Layers:",
        "1. layer1 (V)",
        "2. layer2 (V) (current)",
        "Quitting."
    );

    assertEquals(image1, model.getImageIn("layer1"));
    assertEquals(image1, model.getImageIn("layer2"));
    assertEquals(expected, output);
  }

  @Test
  public void testRunShow() {
    model.addLayer("layer1");
    model.showLayer("layer1", false);

    assertFalse(model.isVisible("layer1"));

    String output = runCommands(
        "show",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 (V) (current)",
        "Quitting."
    );

    assertTrue(model.isVisible("layer1"));
    assertEquals(expected, output);
  }

  @Test
  public void testRunHide() {
    model.addLayer("layer1");

    assertTrue(model.isVisible("layer1"));

    String output = runCommands(
        "hide",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 ( ) (current)",
        "Quitting."
    );

    assertFalse(model.isVisible("layer1"));
    assertEquals(expected, output);
  }

  @Test
  public void testRunSaveall() {
    model.addLayer("layer1");
    model.setLayerImage("layer1", image1);
    model.addLayer("layer2");
    model.showLayer("layer2", false);
    model.setLayerImage("layer2", image2);
    model.addLayer("layer3");

    String output = runCommands(
        "saveall test/data layers",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 (V)",
        "2. layer2 ( )",
        "3. layer3 (V) (current)",
        "Quitting."
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
    assertEquals(expected, output);

    // Clean up
    File directory = new File("test/data/layers");
    for (File file : directory.listFiles()) {
      file.delete();
    }
    directory.delete();
  }

  @Test
  public void testRunLoadall() {
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

    String output = runCommands(
        "loadall test/data/layers",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 (V)",
        "2. layer2 ( )",
        "3. layer3 (V) (current)",
        "Quitting."
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

    assertEquals(expected, output);

    // Clean up
    File directory = new File("test/data/layers");
    for (File file : directory.listFiles()) {
      file.delete();
    }
    directory.delete();
  }

  @Test
  public void testRunAdd() {
    assertEquals(0, model.numLayers());

    String output = runCommands(
        "add layer1",
        "add layer2",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 (V) (current)",
        "Layers:",
        "1. layer1 (V)",
        "2. layer2 (V) (current)",
        "Quitting."
    );

    assertEquals(2, model.numLayers());
    assertEquals(expected, output);
  }

  @Test
  public void testRunRemove() {
    model.addLayer("layer1");
    model.addLayer("layer2");

    assertEquals(2, model.numLayers());

    String output = runCommands(
        "remove",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 (V)",
        "Quitting."
    );

    assertEquals(1, model.numLayers());
    assertEquals(expected, output);
  }

  @Test
  public void testRunSharpen() {
    model.addLayer("layer1");
    model.setLayerImage("layer1", image1);

    assertEquals(image1, model.getImageIn("layer1"));

    String output = runCommands(
        "sharpen",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 (V) (current)",
        "Quitting."
    );

    Image image1Edit = ImageOperationCreator.create(OperationType.SHARPEN).apply(image1);
    assertEquals(image1Edit, model.getImageIn("layer1"));
    assertEquals(expected, output);
  }

  @Test
  public void testRunBlur() {
    model.addLayer("layer1");
    model.setLayerImage("layer1", image1);

    assertEquals(image1, model.getImageIn("layer1"));

    String output = runCommands(
        "blur",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 (V) (current)",
        "Quitting."
    );

    Image image1Edit = ImageOperationCreator.create(OperationType.BLUR).apply(image1);
    assertEquals(image1Edit, model.getImageIn("layer1"));
    assertEquals(expected, output);
  }

  @Test
  public void testRunGreyscale() {
    model.addLayer("layer1");
    model.setLayerImage("layer1", image1);

    assertEquals(image1, model.getImageIn("layer1"));

    String output = runCommands(
        "greyscale",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 (V) (current)",
        "Quitting."
    );

    Image image1Sharpen = ImageOperationCreator.create(OperationType.GREYSCALE).apply(image1);
    assertEquals(image1Sharpen, model.getImageIn("layer1"));
    assertEquals(expected, output);
  }

  @Test
  public void testRunSepia() {
    model.addLayer("layer1");
    model.setLayerImage("layer1", image1);

    assertEquals(image1, model.getImageIn("layer1"));

    String output = runCommands(
        "sepia",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 (V) (current)",
        "Quitting."
    );

    Image image1Sharpen = ImageOperationCreator.create(OperationType.SEPIA).apply(image1);
    assertEquals(image1Sharpen, model.getImageIn("layer1"));
    assertEquals(expected, output);
  }

  @Test
  public void testRunScript() {
    assertEquals(0, model.numLayers());

    String output = runCommands(
        "script test/data/script1.txt",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 (V) (current)",
        "Layers:",
        "1. layer1 (V)",
        "2. layer2 (V) (current)",
        "Layers:",
        "1. layer1 (V)",
        "Quitting."
    );

    assertEquals(1, model.numLayers());
    assertEquals("layer1", model.getLayerNameAt(0));
    assertEquals(expected, output);
  }

  @Test
  public void testRunCurrent() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    assertEquals("layer2", model.getCurrentName());

    String output = runCommands(
        "current layer1",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer1 (V) (current)",
        "2. layer2 (V)",
        "Quitting."
    );

    assertEquals("layer1", model.getCurrentName());
    assertEquals(expected, output);
  }

  @Test
  public void testRunMove() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    assertEquals("layer1", model.getLayerNameAt(0));
    assertEquals("layer2", model.getLayerNameAt(1));

    String output = runCommands(
        "move 1",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Layers:",
        "1. layer2 (V) (current)",
        "2. layer1 (V)",
        "Quitting."
    );

    assertEquals("layer2", model.getLayerNameAt(0));
    assertEquals("layer1", model.getLayerNameAt(1));
    assertEquals(expected, output);
  }

  // Tests inputting commands that are not valid into the controller.
  @Test
  public void testInvalidCommands() {
    String output = runCommands(
        "notarealcommand",
        "anotherfakecommand",
        "yay",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "Unknown Command",
        "Unknown Command",
        "Unknown Command",
        "Quitting."
    );

    assertEquals(expected, output);
  }

  /*
  Tests inputting invalid arguments for a valid command into the controller.
  Just test the controller's ability to render error messages produced by
  a ControllerCommand implementation. The actual error behavior of specific
  invalid arguments should be tested for each ControllerCommand implementation
  individually.
   */
  @Test
  public void testInvalidCommandArguments() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    model.setLayerImage("layer1", image1);

    String output = runCommands(
        "current name",
        "sharpen",
        "save notarealpath/fake/crashandburn ppm",
        "move -3",
        "q"
    );
    String expected = concatenateLines(
        "Enter a command",
        "No layer named name.",
        "The image right now is null!",
        "Failed to save file. notarealpath/fake/crashandburn (No such file or directory)",
        "Invalid index.",
        "Quitting."
    );

    assertEquals(expected, output);
  }


  /**
   * Runs a series of commands in a new SimpleImageProcessingController.
   *
   * @param commands the commands that should be run in the controller. Each command is separated by
   *                 a new line character.
   * @return the output of the controller as a String.
   */
  private String runCommands(String... commands) {

    Readable input = new StringReader(concatenateLines(commands));
    Appendable output = new StringBuilder();
    ImageProcessingController controller =
        new SimpleImageProcessingController(model, input, output);

    controller.run();
    return output.toString();
  }

  /**
   * Concatenates a series of lines into one String with each line followed
   * by a new line character.
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
}
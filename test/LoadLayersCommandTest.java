import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.PngImportExporter;
import controller.commands.LoadLayersCommand;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import model.Image;
import model.ImageExamples;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.RgbPixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the SaveLayersCommand class.
 */
public class LoadLayersCommandTest {

  private ImageProcessingModel model;
  private final Image image1;
  private final Image image2;

  /**
   * Construct a new LoadLayersCommandTest initializing all example test data.
   */
  public LoadLayersCommandTest() {
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
  public void testConstructorNullPath() {
    new LoadLayersCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalid() {
    new LoadLayersCommand("notarealpath/not/real");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullModel() {
    new LoadLayersCommand(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testGoInvalidTextFile() {
    clean();

    String textFile = "layer1 true test/data/layers/layer1.png\n"
        + "layer2\n"
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

    new LoadLayersCommand("test/data/layers").runCommand(model);

    clean();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoMissingImageFile() {
    clean();

    String textFile = "layer1 true test/data/layers/layer1.png\n"
        + "layer2 false test/data/layers/layer2.png\n"
        + "layer3 true\n";

    model.addLayer("other");
    try {
      new File("test/data/layers").mkdir();
      new PngImportExporter().saveImage(
          new FileOutputStream("test/data/layers/layer2.png"), image2);
      new FileOutputStream("test/data/layers/info.txt").write(textFile.getBytes());
    } catch (IOException e) {
      fail("Failed to save test images. " + e.getMessage());
    }

    new LoadLayersCommand("test/data/layers").runCommand(model);

    clean();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoInvalidImageFile() {
    clean();

    String textFile = "layer1 true test/data/layers/layer1.png\n"
        + "layer2 false test/data/layers/layer2.png\n"
        + "layer3 true\n";

    model.addLayer("other");
    try {
      new File("test/data/layers").mkdir();
      new FileOutputStream("test/data/layers/layer1.png").write("hello".getBytes());
      new PngImportExporter().saveImage(
          new FileOutputStream("test/data/layers/layer2.png"), image2);
      new FileOutputStream("test/data/layers/info.txt").write(textFile.getBytes());
    } catch (IOException e) {
      fail("Failed to save test images. " + e.getMessage());
    }

    new LoadLayersCommand("test/data/layers").runCommand(model);

    clean();
  }

  @Test
  public void testGoValid() {
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

    new LoadLayersCommand("test/data/layers").runCommand(model);

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

    clean();
  }

  /**
   * Deletes files from test/data/layers and layers directory to make sure tests
   * run independently.
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
}
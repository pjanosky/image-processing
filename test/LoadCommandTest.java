import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import controller.PngImportExporter;
import controller.commands.LoadCommand;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import model.Image;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the LoadCommand class.
 */
public class LoadCommandTest {

  private ImageProcessingModel model;

  /**
   * Construct a new ImageProcessCommandTest initializing all example test data.
   */
  public LoadCommandTest() {
    model = new ImageProcessingModelImpl();
  }

  @Before
  public void setup() {
    model = new ImageProcessingModelImpl();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullFilePath() {
    new LoadCommand(null, "ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullFormat() {
    new LoadCommand("test/data", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidFormat() {
    new LoadCommand("test/data", "not a supported format");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidFilePath() {
    new LoadCommand("notarealpath/not/real", "ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullModel() {
    new LoadCommand("test/data", "ppm").go(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNoCurrentLayerSet() {
    new LoadCommand("test/data", "ppm").go(model);
  }

  @Test
  public void testGoValid() {
    clean();

    Image image = ImageExamples.rainbow(1, 2);
    String path = "test/data/image.png";
    try {
      new PngImportExporter().saveImage(new FileOutputStream(path), image);
    } catch (IOException e) {
      fail("Failed to save text image. " + e.getMessage());
    }
    model.addLayer("layer1");
    assertNull(model.getImageIn("layer1"));

    new LoadCommand(path, "png").go(model);
    assertEquals(image, model.getImageIn("layer1"));
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import controller.PngImportExporter;
import controller.commands.SaveLayersCommand;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import model.Image;
import model.ImageExamples;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.RgbPixel;
import org.junit.Before;
import org.junit.Test;
import view.ImageProcessingTextView;
import view.ImageProcessingView;


/**
 * Test the SaveLayersCommand class.
 */
public class SaveLayersCommandTest {

  private ImageProcessingModel model;
  private Appendable output;
  private ImageProcessingView view;

  /**
   * Construct a new SaveLayersCommandTest initializing all example test data.
   */
  public SaveLayersCommandTest() {
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
  public void testConstructorNullPath() {
    new SaveLayersCommand(null, "layers");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullName() {
    new SaveLayersCommand("test/data", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidPath() {
    new SaveLayersCommand("notarealpath/not/real", "layers");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullModel() {
    new SaveLayersCommand("test/data", "layers").runCommand(null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullView() {
    new SaveLayersCommand("test/data", "layers").runCommand(model, null);
  }

  @Test(expected = IllegalStateException.class)
  public void testGoInvalidName() {
    new SaveLayersCommand("test/data", "invalid/name#$\\%^&*").runCommand(model, view);
  }

  @Test
  public void testGoValid() {
    clean();
    Image image1 = ImageExamples.rainbow(10, 12);
    Image image2 = ImageExamples.checkerboard(12, 10, 1, 1,
        new RgbPixel(0, 0, 0),
        new RgbPixel(255, 255, 255));
    model.addLayer("layer1");
    model.setLayerImage("layer1", image1);
    model.addLayer("layer2");
    model.showLayer("layer2", false);
    model.setLayerImage("layer2", image2);
    model.addLayer("layer3");

    new SaveLayersCommand("test/data", "layers").runCommand(model, view);

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

    String expected = "Saved all layers to test/data/layers." + System.lineSeparator();
    assertEquals(expected, output.toString());

    clean();
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
}
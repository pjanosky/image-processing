import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import controller.PngImportExporter;
import controller.commands.SaveCommand;
import java.io.FileInputStream;
import java.io.IOException;
import model.Image;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.RgbPixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the SaveCommand class.
 */
public class SaveCommandTest {

  private ImageProcessingModel model;

  /**
   * Construct a new SaveCommandTest initializing all example test data.
   */
  public SaveCommandTest() {
    model = new ImageProcessingModelImpl();
  }

  @Before
  public void setup() {
    model = new ImageProcessingModelImpl();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullFilePath() {
    new SaveCommand(null, "png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullFormat() {
    new SaveCommand("test/data", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidFilePath() {
    new SaveCommand("notarealpath/not/real", "png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidFormat() {
    new SaveCommand("test/data", "not a real format");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoNullModel() {
    new SaveCommand("test/data", "png").go(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoLayers() {
    new SaveCommand("test/data", "png").go(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoVisibleLayers() {
    model.addLayer("layer1");
    model.addLayer("layer2");
    model.setLayerImage("layer2", ImageExamples.rainbow(2, 10));
    model.showLayer("layer2", false);
    new SaveCommand("test/data", "png").go(model);
  }

  @Test
  public void testGoValid() {
    Image image1 = ImageExamples.rainbow(10, 2);
    Image image2 = ImageExamples.checkerboard(12, 10,1, 1,
        new RgbPixel(0, 0, 0),
        new RgbPixel(255, 255, 255));
    String path = "test/data/image.png";

    model.addLayer("layer1");
    model.addLayer("layer2");
    model.addLayer("layer3");
    model.addLayer("layer4");

    model.setLayerImage("layer2", image2);
    model.showLayer("layer2", false);
    model.setLayerImage("layer3", image1);
    model.setLayerImage("layer4", image2);

    new SaveCommand(path, "png").go(model);

    try {
      assertEquals(image1, new PngImportExporter().parseImage(new FileInputStream(path)));
    } catch (IOException e) {
      fail("Failed to parse saved images. " + e.getMessage());
    }
  }
}
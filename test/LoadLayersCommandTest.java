import static org.junit.Assert.*;

import controller.ImageImportExporter;
import controller.PngImportExporter;
import controller.PpmImportExporter;
import controller.commands.LoadLayersCommand;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import org.junit.Test;

public class LoadLayersCommandTest {

  @Test
  public void santiyCheck() {
    ImageProcessingModel model = new ImageProcessingModelImpl();
    new LoadLayersCommand("layers_test", "ppm").go(model);
    assertEquals(3, model.numLayers());
    assertEquals("rainbow", model.getLayerNameAt(0));
    assertEquals("checkerboard", model.getLayerNameAt(1));
    assertEquals("no_image", model.getLayerNameAt(2));

    assertEquals(true, model.isVisible("rainbow"));
    assertEquals(false, model.isVisible("checkerboard"));
    assertEquals(true, model.isVisible("no_image"));

    try {
      new PpmImportExporter().saveImage(
          new FileOutputStream("layers_test/rainbow_saved.ppm"),
          model.getImageIn("rainbow"));
      new PpmImportExporter().saveImage(
          new FileOutputStream("layers_test/checkerboard_saved.ppm"),
          model.getImageIn("checkerboard"));
    } catch (IOException e) {
      fail("Failed to save image. " + e.getMessage());
    }
    // need to check images as well
  }
}
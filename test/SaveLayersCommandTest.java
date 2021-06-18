import static org.junit.Assert.*;

import controller.commands.SaveLayersCommand;
import model.ImageProcessingModel;
import model.ImageProcessingModelImpl;
import model.RgbPixel;
import org.junit.Test;

public class SaveLayersCommandTest {

  @Test
  public void sanityCheck() {
    ImageProcessingModel model = new ImageProcessingModelImpl();
    model.addLayer("rainbow");
    model.setLayerImage("rainbow", ImageExamples.rainbow(30, 2));
    model.addLayer("checkerboard");
    model.show("checkerboard", false);
    model.setLayerImage("checkerboard",
        ImageExamples.checkerboard(5, 5, 5, 5,
            new RgbPixel(255, 0, 0),
            new RgbPixel(0, 0, 255)));
    model.addLayer("no_image");

    new SaveLayersCommand("", "layers_test", "png").go(model);

  }
}